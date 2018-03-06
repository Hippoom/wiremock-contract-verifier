package com.github.hippoom.wiremock.contract.verifier.mockmvc;

import com.github.hippoom.wiremock.contract.verifier.anntation.Contract;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.test.web.servlet.ResultMatcher;

public class JsonContentResultMatchers {

    public static JSONCompareMode toJsonCompareMode(Contract contract) {
        return JSONCompareMode.LENIENT.withExtensible(contract.extensible()).withStrictOrdering(contract.strictOrder());
    }

    public ResultMatcher json(final String jsonContent, final JSONCompareMode jsonCompareMode) {
        return result -> {
            String content = result.getResponse().getContentAsString();
            JSONAssert.assertEquals(jsonContent, content, jsonCompareMode);
        };
    }
}
