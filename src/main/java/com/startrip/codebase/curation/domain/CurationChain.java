package com.startrip.codebase.curation.domain;


public interface CurationChain<I, O> {
    O process(I input);
}
