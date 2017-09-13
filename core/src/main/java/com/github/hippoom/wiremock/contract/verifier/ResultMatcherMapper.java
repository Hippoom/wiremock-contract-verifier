package com.github.hippoom.wiremock.contract.verifier;

import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

public class ResultMatcherMapper {

    public void match(StubMapping stubMapping,
        MvcResult result) throws Exception {

        ResponseDefinition responseDefinition = stubMapping.getResponse();

        MockMvcResultMatchers.status().is(responseDefinition.getStatus()).match(result);

        if (!StringUtils.isEmpty(responseDefinition.getBody())) {
            MockMvcResultMatchers.content().json(responseDefinition.getBody()).match(result);
        }

    }
}
