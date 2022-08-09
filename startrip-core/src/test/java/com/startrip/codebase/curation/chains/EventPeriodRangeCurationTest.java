package com.startrip.codebase.curation.chains;

import com.startrip.core.curation.CurationInputObject;
import com.startrip.core.curation.CurationManager;
import com.startrip.core.curation.CurationPipeline;
import com.startrip.core.curation.chains.ChainType;
import com.startrip.core.curation.chains.EventPeriodRangeCuration;
import com.startrip.core.curation.userinput.EventPeriodDateTimeObject;
import com.startrip.core.entity.curation.repository.CurationObjectRepository;
import com.startrip.core.entity.user.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventPeriodRangeCurationTest {

    @Autowired
    private CurationObjectRepository curationObjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurationManager curationManager;

    @DisplayName("사용자가 오늘 기준 일주일 기간범위를 지정, 필터로 해당 쿼리가 생성되는지 테스트")
    @Test
    public void test0() {
        CurationInputObject curationObject = new CurationInputObject();
        EventPeriodDateTimeObject userInput = new EventPeriodDateTimeObject();
        userInput.setStartDate(LocalDateTime.now());
        userInput.setEndDate(LocalDateTime.now().plusDays(7));

        curationObject.addInput(ChainType.EVENT_PERIOD, userInput); // 사용자 입력

        var pipeline = new CurationPipeline<CurationInputObject, CurationInputObject>(new EventPeriodRangeCuration());
        pipeline.execute(curationObject);

        System.out.println(curationObject.toString());
    }
}