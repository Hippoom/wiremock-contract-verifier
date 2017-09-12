package com.github.hippoom.wiremock.contract.verifier

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.http.RequestMethod.GET

class ContractMappingTest extends Specification {

    def "The mapping should support request url and method"() {

        when:

        def actual = StubMapping.buildFrom("""
            {
                "request": {
                    "method": "GET",
                    "url": "/some/thing"
                }
            }
        """)

        then:

        assert actual.request.method == GET
    }

    def "The mapping should support request equalTo headers"() {

        when:

        def actual = StubMapping.buildFrom("""
            {
                "request": {
                    "headers": {
                        "Content-Type": {
                            "equalTo": "application/json"
                        }
                    }
                }
            }
        """)

        then:

        assert actual.request.headers.get("Content-Type").expected == "application/json"
    }

    def "The mapping should support request contains headers"() {

        when:

        def actual = StubMapping.buildFrom("""
            {
                "request": {
                    "headers": {
                        "Content-Type": {
                            "contains": "application/json"
                        }
                    }
                }
            }
        """)

        then:

        assert actual.request.headers.get("Content-Type").expected == "application/json"
    }

    def "The mapping should support request json body"() {

        when:

        def actual = StubMapping.buildFrom("""
            {
                "request": {
                    "bodyPatterns" : [ {
                        "equalToJson" : "{ \\"total_results\\": 4 }"
                    } ]
                }
            }
        """)

        then:

        assert actual.request.bodyPatterns.get(0).match("""
            { 
                "total_results": 4 
            }
        """)
    }
}
