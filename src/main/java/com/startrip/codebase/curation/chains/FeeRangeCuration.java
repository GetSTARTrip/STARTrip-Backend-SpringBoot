package com.startrip.codebase.curation.chains;

import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationInputObject;
import com.startrip.codebase.curation.userinput.FeeRangeObject;
import com.startrip.codebase.domain.place_info.QPlaceInfo;

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
