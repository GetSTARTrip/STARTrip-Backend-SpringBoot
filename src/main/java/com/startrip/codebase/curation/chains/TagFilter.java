package com.startrip.codebase.curation.chains;

import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationInputObject;
import com.startrip.codebase.domain.place.QPlace;

import java.util.List;

<<<<<<< HEAD:src/main/java/com/startrip/codebase/curation/chains/TagFilter.java
public class TagFilter implements CurationChain<CurationObject, CurationObject> {
=======
public class TagCuration implements CurationChain<CurationInputObject, CurationInputObject> {
>>>>>>> develop:src/main/java/com/startrip/codebase/curation/chains/TagCuration.java

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
