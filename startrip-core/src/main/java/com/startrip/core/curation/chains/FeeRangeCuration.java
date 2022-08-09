package com.startrip.core.curation.chains;

import com.startrip.core.curation.CurationChain;
import com.startrip.core.curation.CurationInputObject;
import com.startrip.core.curation.userinput.FeeRangeObject;

public class FeeRangeCuration implements CurationChain<CurationInputObject, CurationInputObject> {

    @Override
    public CurationInputObject process(CurationInputObject input) {
        Object object = input.getData(ChainType.FEE);
        if (object != null && object instanceof FeeRangeObject) {
            FeeRangeObject uesrInput = (FeeRangeObject) object;
            // 가격 테이블이 없음
        }
        return input;
    }
}
