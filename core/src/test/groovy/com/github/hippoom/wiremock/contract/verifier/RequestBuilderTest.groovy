package com.github.hippoom.wiremock.contract.verifier

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockServletContext
import spock.lang.Specification

class RequestBuilderTest extends Specification {

    def subject = new MockHttpServletRequestBuilderMapper()

    def "The builder should support request url and method"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "request": {
                    "method": "GET",
                    "url": "/some/thing"
                }
            }
        """)

        when:

        MockHttpServletRequest actual = subject.from(stubMapping)
            .buildRequest(new MockServletContext())

        then:

        assert actual.method == "GET"
        assert actual.requestURI == "/some/thing"
    }

    def "The builder should support headers"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "request": {
                    "method": "GET",
                    "url": "/some/thing",
                    "headers": {
                        "Content-Type": {
                            "equalTo": "application/json"
                        },
                        "Accept": {
                            "contains": "application/xml"
                        }
                    }
                }
            }
        """)

        when:

        MockHttpServletRequest actual = subject.from(stubMapping)
            .buildRequest(new MockServletContext())

        then:

        assert actual.method == "GET"
        assert actual.requestURI == "/some/thing"
        assert actual.getHeader("Content-Type") == "application/json"
        assert actual.getHeader("Accept") == "application/xml"
    }

    def "The builder should support json body"() {

        given:

        def stubMapping = StubMapping.buildFrom("""
            {
                "request": {
                    "method": "GET",
                    "url": "/some/thing",
                    "bodyPatterns" : [ {
                        "equalToJson" : "{}"
                    } ]
                }
            }
        """)

        when:

        MockHttpServletRequest actual = subject.from(stubMapping)
            .buildRequest(new MockServletContext())

        then:

        assert actual.method == "GET"
        assert actual.requestURI == "/some/thing"
        assert new String(actual.content) == "{ }"
    }
}
