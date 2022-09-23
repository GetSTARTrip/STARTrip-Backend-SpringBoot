package com.startrip.codebase.curation.domain;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.*;
import com.startrip.codebase.curation.domain.chains.TagFilter;
import com.startrip.codebase.curation.repository.CurationObjectRepository;
import com.startrip.codebase.member.domain.user.User;
import com.startrip.codebase.member.domain.user.UserRepository;
import com.startrip.codebase.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class CurationManager {
    private final JPAQueryFactory jpaQueryFactory;
    private final CurationPipeline pipeline;
    private final CurationObjectRepository curationObjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public CurationManager(JPAQueryFactory jpaQueryFactory, CurationObjectRepository curationObjectRepository, UserRepository userRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.curationObjectRepository = curationObjectRepository;
        this.userRepository = userRepository;

        pipeline = new CurationPipeline(new TagFilter());

    }

    public CurationObject start(CurationInputObject userSource) {
        try {
            pipeline.execute(userSource);
            return curationObjectSerializeAndPersist(userSource);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null; // null?
    }

    private CurationObject curationObjectSerializeAndPersist(CurationInputObject object) throws IOException {
        String userEmail = getUserEmail();
        log.debug(userEmail + " SecurityContext");

        User user = findUser(userEmail);
        String encode = object.serialize();

        CurationObject resultObject = CurationObject.builder()
                .curationObjectId(UUID.randomUUID())
                .createdTime(LocalDateTime.now())
                .user(user)
                .encodeObject(encode)
                .build();

        return curationObjectRepository.save(resultObject);
    }

    private User findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new RuntimeException("없는 유저");
        });
        return user;
    }

    private String getUserEmail() {
        return SecurityUtil.getCurrentUsername().orElseThrow(() -> {
            throw new RuntimeException("SecurityContextHolder에 유저 정보가 없습니다.");
        });
    }
}