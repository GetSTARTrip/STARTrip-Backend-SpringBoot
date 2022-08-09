package com.startrip.codebase.curation.chains;

import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationInputObject;
import com.startrip.codebase.curation.userinput.UseTimeObject;
import com.startrip.codebase.domain.operating_time.QOperatingTime;

public class UseTimeRangeCuration implements
    CurationChain<CurationInputObject, CurationInputObject> {


    @Override
    public CurationInputObject process(CurationInputObject input) {
        Object object = input.getData(ChainType.USE_TIME_RANGE);
        if (object != null && object instanceof UseTimeObject) {
            UseTimeObject userInput = (UseTimeObject) object;

            input.getBooleanBuilder().or(
                QOperatingTime.operatingTime.startTime.after(userInput.getStartTime())
                    .and(QOperatingTime.operatingTime.endTime.before(userInput.getEndTime()))
            );
        }
        return null;
    }
}
