package com.startrip.core.curation.chains;

import com.startrip.core.curation.CurationChain;
import com.startrip.core.curation.CurationInputObject;
import com.startrip.core.curation.userinput.UseTimeObject;
import com.startrip.core.entity.operating_time.QOperatingTime;

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
