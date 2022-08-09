package com.startrip.core.curation;


public interface CurationChain<I, O> {
    O process(I input);
}
