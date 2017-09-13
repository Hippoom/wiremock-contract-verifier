package com.github.hippoom.wiremock.contract.verifier

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.DefaultMvcResult
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ResponseBuilderTest extends Specification {

    def subject = new ResultMatcherMapper()

    def "The builder should support response status"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "response": {
                    "status": 201
                }
            }
        """)

        def response = new MockHttpServletResponse()
        response.setStatus(CREATED.value())

        when:

        subject.match(stubMapping,
            new DefaultMvcResult(new MockHttpServletRequest(), response))


        then:
        noExceptionThrown()
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

        MockHttpServletResponse response = new MockHttpServletResponse()
        response.setStatus(OK.value())
        response.getOutputStream().print("""
            {
                "arbitrary_json": [1, 2, 3]
            }
        """)

        when:

        subject.match(stubMapping,
            new DefaultMvcResult(new MockHttpServletRequest(), response))


        then:
        noExceptionThrown()
    }
}
