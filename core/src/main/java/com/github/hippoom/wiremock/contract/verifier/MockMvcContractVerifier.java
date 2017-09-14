package com.github.hippoom.wiremock.contract.verifier;

import static com.github.tomakehurst.wiremock.stubbing.StubMapping.buildFrom;
import static java.nio.file.Files.readAllBytes;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class MockMvcContractVerifier extends TestWatcher {

    private final MockHttpServletRequestBuilderMapper requestBuilderMapper =
        new MockHttpServletRequestBuilderMapper();

    private final ResultMatcherMapper resultMatcherMapper =
        new ResultMatcherMapper();

    private MockHttpServletRequestBuilder requestBuilder;
    private ResultMatcher resultMatcher;

    @Override
    protected void starting(Description description) {
        Contract contract = description.getAnnotation(Contract.class);
        StubMapping stubMapping = buildFrom(extractJsonFrom(contract));
        requestBuilder = this.requestBuilderMapper.from(stubMapping);
        resultMatcher = this.resultMatcherMapper.from(stubMapping);
    }

    private String extractJsonFrom(Contract contract) {
        ClassPathResource pathResource = new ClassPathResource(
            "contracts/" + contract.value());
        try {
            byte[] bytes = readAllBytes(Paths.get(pathResource.getURI()));
            return new String(bytes, Charset.forName("utf-8"));
        } catch (IOException err) {
            throw new NoSuchContractException(err.getMessage(), err);
        }
    }

    public MockHttpServletRequestBuilder requestPattern() {
        return requestBuilder;
    }

    public ResultMatcher responseDefinition() {
        return resultMatcher;
    }
}
