package com.startrip.core.curation.chains;

import com.startrip.core.curation.CurationChain;
import com.startrip.core.curation.CurationInputObject;
import com.startrip.core.entity.place.QPlace;
import java.util.List;


public class TagFilter implements CurationChain<CurationInputObject, CurationInputObject> {

    @Override
    public CurationInputObject process(CurationInputObject input) {
        Object object = input.getData(ChainType.TAG);
        if (object instanceof List){
            List<String> userTags = (List<String>) object;
            for (String tag : userTags) {
                input.getBooleanBuilder().or(QPlace.place.placeName.contains(tag).or(QPlace.place.address.contains(tag)));
            }

        }
        return input;
    }
}
