package com.startrip.codebase.curation.chains;

import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationInputObject;
import com.startrip.codebase.curation.classs.EventPeriodDateTimeObject;
import com.startrip.codebase.domain.event.QEvent;
import java.time.LocalDateTime;
import java.util.List;

public class EventPeriodRangeCuration implements
    CurationChain<CurationInputObject, CurationInputObject> {

    @Override
    public CurationInputObject process(CurationInputObject input) {
        Object object = input.getData(ChainType.EVENT_PERIOD);
        if (object != null && object instanceof EventPeriodDateTimeObject) {
            EventPeriodDateTimeObject userInput = (EventPeriodDateTimeObject) object;

            input.getBooleanBuilder().or(QEvent.event.startDate.after(userInput.getStartDate())
                .and(QEvent.event.endDate.before(userInput.getEndDate())));
        }
        return null;
    }
}
