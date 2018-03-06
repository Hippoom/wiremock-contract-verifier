package com.github.hippoom.wiremock.contract.verifier.mockmvc

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.DefaultMvcResult
import spock.lang.Specification

import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT_ORDER
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ResponseBuilderTest extends Specification {

    def subject = new ResultMatcherMapper(LENIENT)
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
        actual.match(new DefaultMvcResult(this.request, this.response))

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

        response.setStatus(OK.value())
        response.getOutputStream().print("""
            {
                "arbitrary_json": [2, 1,  3]
            }
        """)

        when:

        def actual = subject.from(stubMapping)
        actual.match(new DefaultMvcResult(this.request, this.response))


        then:
        noExceptionThrown()
    }

    def "The builder should support strict response json given ordering is wrong"() {

        given:
        subject = new ResultMatcherMapper(STRICT_ORDER)
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
                "arbitrary_json": [2, 1,  3]
            }
        """)

        when:

        def actual = subject.from(stubMapping)
        actual.match(new DefaultMvcResult(this.request, this.response))


        then:
        thrown(AssertionError)
    }

    def "The builder should support strict response json given extra fields"() {

        given:
        subject = new ResultMatcherMapper(NON_EXTENSIBLE)
        def stubMapping = StubMapping.buildFrom("""
            {
                "response": {
                    "status": 200,
                    "jsonBody": {
                        "a": "b"
                    }
                }
            }
        """)

        response.setStatus(OK.value())
        response.getOutputStream().print("""
            {
                "a": "b",
                "c": "d"
            }
        """)

        when:

        def actual = subject.from(stubMapping)
        actual.match(new DefaultMvcResult(this.request, this.response))


        then:
        thrown(AssertionError)
    }

    def "The builder should support strict response json given ordering is correct"() {

        given:
        subject = new ResultMatcherMapper(STRICT_ORDER)
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
                "arbitrary_json": [1, 2,  3]
            }
        """)

        when:

        def actual = subject.from(stubMapping)
        actual.match(new DefaultMvcResult(this.request, this.response))


        then:
        noExceptionThrown()
    }

}
