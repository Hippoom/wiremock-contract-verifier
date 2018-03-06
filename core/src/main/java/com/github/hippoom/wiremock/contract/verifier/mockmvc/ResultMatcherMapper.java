package com.github.hippoom.wiremock.contract.verifier.mockmvc;

import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.StringUtils;

public class ResultMatcherMapper {

    private final JSONCompareMode strictJson;

    public ResultMatcherMapper(JSONCompareMode jsonCompareMode) {
        this.strictJson = jsonCompareMode;
    }

    public ResultMatcherMapper() {
        this(LENIENT);
    }

    public ResultMatcher from(StubMapping stubMapping) {
        List<ResultMatcher> delegates = new ArrayList<>();

        ResponseDefinition responseDefinition = stubMapping.getResponse();

        delegates.add(status().is(responseDefinition.getStatus()));

        if (!StringUtils.isEmpty(responseDefinition.getBody())) {
            delegates.add(new JsonContentResultMatchers().json(responseDefinition.getBody(), strictJson));
        }
        return new MultiResultMatcher(delegates);
    }

    public static class MultiResultMatcher implements ResultMatcher {

        private List<ResultMatcher> delegates;

        public MultiResultMatcher(
            List<ResultMatcher> delegates) {
            this.delegates = delegates;
        }

        @Override
        public void match(MvcResult result) throws Exception {
            for (ResultMatcher current : this.delegates) {
                current.match(result);
            }
        }
    }
}
