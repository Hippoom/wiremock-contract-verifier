package com.github.hippoom.wiremock.contract.verifier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class MockHttpServletRequestBuilderMapper {

    public MockHttpServletRequestBuilder from(StubMapping stubMapping) {

        RequestPattern requestPattern = stubMapping.getRequest();

        MockHttpServletRequestBuilder requestBuilder = request(
            requestPattern.getMethod().getExpected(),
            getUri(requestPattern));

        if (requestPattern.getHeaders() != null) {
            requestPattern.getHeaders().forEach((header, valuePattern) ->
                requestBuilder.header(header, valuePattern.getExpected()));
        }

        if (requestPattern.getBodyPatterns() != null) {
            requestBuilder.content(stubMapping.getRequest().getBodyPatterns().get(0).getExpected());
        }

        return requestBuilder;

    }

    private URI getUri(RequestPattern requestPattern) {
        try {
            return new URI(requestPattern.getUrl());
        } catch (URISyntaxException err) {
            throw new IllegalArgumentException(err.getMessage(), err);
        }
    }
}
