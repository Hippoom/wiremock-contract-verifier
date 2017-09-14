package com.github.hippoom.wiremock.contract.verifier.mockmvc

import com.github.hippoom.wiremock.contract.verifier.mockmvc.ResultMatcherMapper
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.DefaultMvcResult
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ResponseBuilderTest extends Specification {

    def subject = new ResultMatcherMapper()
    private request
    private response

    def setup() {
        request = new MockHttpServletRequest()
        response = new MockHttpServletResponse()
    }

    def "The builder should support response status"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "response": {
                    "status": 201
                }
            }
        """)

        this.response.setStatus(CREATED.value())

        when:
        def actual = subject.from(stubMapping)


        then:
        actual.match(new DefaultMvcResult(this.request, this.response))
    }

    def "The builder should support response body"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "response": {
                    "status": 200,
                    "jsonBody": {
                        "arbitrary_json": [1, 2, 3]
                    }
                }
            }
        """)

        response.setStatus(OK.value())
        response.getOutputStream().print("""
            {
                "arbitrary_json": [1, 2, 3]
            }
        """)

        when:

        def actual = subject.from(stubMapping)


        then:
        actual.match(new DefaultMvcResult(this.request, this.response))
    }
}
