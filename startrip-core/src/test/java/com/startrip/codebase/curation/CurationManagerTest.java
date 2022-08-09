package com.startrip.codebase.curation;

import com.startrip.core.curation.CurationInputObject;
import com.startrip.core.curation.CurationManager;
import com.startrip.core.curation.CurationPipeline;
import com.startrip.core.curation.chains.ChainType;
import com.startrip.core.curation.chains.TagFilter;
import com.startrip.core.entity.auth.WithMockCustomUser;
import com.startrip.core.entity.curation.entity.CurationObject;
import com.startrip.core.entity.curation.repository.CurationObjectRepository;
import com.startrip.core.entity.user.User;
import com.startrip.core.entity.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CurationManagerTest {

    @Autowired
    private CurationObjectRepository curationObjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurationManager curationManager;

    @DisplayName("사용자가 공원,박물관 태그를 입력할시 테스트")
    @Test
    public void test0() {
        CurationInputObject curationObject = new CurationInputObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("공원");
        userInputTags.add("박물관");

        curationObject.addInput(ChainType.TAG, userInputTags); // 사용자 입력

        var filters = new CurationPipeline<>(new TagFilter());
        CurationInputObject result = filters.execute(curationObject);

        System.out.println(curationObject.toString());
    }

    private void createUser() {
        User user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("test")
                .nickname("닉네임")
                .build();
        userRepository.save(user);
    }

    @DisplayName("큐레이션 후 CurationObject DB에 저장되는지 테스트")
    @WithMockCustomUser(username = "test@test.com", role = "USER")
    @Test
    public void test1() {
        createUser();
        CurationInputObject inputObject = new CurationInputObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("공원");
        userInputTags.add("박물관");

        inputObject.addInput(ChainType.TAG, userInputTags); // 사용자 입력

        CurationObject curationObject = curationManager.start(inputObject);

        CurationObject savedObject = curationObjectRepository.findById(curationObject.getCurationObjectId()).orElseThrow(() -> {
            throw new RuntimeException("해당 CurationObject를 찾을 수 없습니다.");
        });
        assertThat(curationObject.getEncodeObject()).isEqualTo(savedObject.getEncodeObject());
    }

    @DisplayName("큐레이션 후 DB에 저장된 CurationObject 바이너리를 역직렬화 테스트")
    @WithMockCustomUser(username = "test@test.com", role = "USER")
    @Test
    public void test2() throws IOException, ClassNotFoundException {
        CurationInputObject inputObject = new CurationInputObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("공원");
        userInputTags.add("박물관");

        inputObject.addInput(ChainType.TAG, userInputTags); // 사용자 입력

        CurationObject curationObject = curationManager.start(inputObject);

        CurationObject savedObject = curationObjectRepository.findById(curationObject.getCurationObjectId()).orElseThrow(() -> {
            throw new RuntimeException("해당 CurationObject를 찾을 수 없습니다.");
        });
        CurationInputObject deSerializedObject = savedObject.deSerialize();

        assertThat(inputObject.toString()).isEqualTo(deSerializedObject.toString());
    }


}