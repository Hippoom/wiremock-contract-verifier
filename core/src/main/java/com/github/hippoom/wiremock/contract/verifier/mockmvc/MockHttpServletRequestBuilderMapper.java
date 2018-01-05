package com.github.hippoom.wiremock.contract.verifier.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

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
            ContentPattern<?> firstPattern = requestPattern.getBodyPatterns().get(0);
            if (firstPattern instanceof EqualToJsonPattern) {
                requestBuilder.content(stubMapping.getRequest().getBodyPatterns().get(0).getExpected());
            } else if (firstPattern instanceof EqualToPattern) {
                String expected = firstPattern.getExpected();
                String[] paramKeyValuePairs = expected.split("&");
                Arrays.stream(paramKeyValuePairs)
                    .map(keyValue -> keyValue.split("="))
                    .forEach(keyValueArr -> requestBuilder.param(keyValueArr[0], keyValueArr[1]));
            }
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
