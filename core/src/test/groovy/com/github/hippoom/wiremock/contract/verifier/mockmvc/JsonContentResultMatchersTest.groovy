package com.github.hippoom.wiremock.contract.verifier.mockmvc

import com.github.hippoom.wiremock.contract.verifier.anntation.Contract
import org.skyscreamer.jsonassert.JSONCompareMode
import spock.lang.Specification

import java.lang.annotation.Annotation

import static com.github.hippoom.wiremock.contract.verifier.mockmvc.JsonContentResultMatchers.toJsonCompareMode
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT_ORDER

class JsonContentResultMatchersTest extends Specification {

    def "given strict order is false and extensible is true"() {
        when:
        def mode = toJsonCompareMode(aContractWith(false, true))

        then:
        assert mode == LENIENT
    }

    def "given strict order is true and extensible is true"() {
        when:
        def mode = toJsonCompareMode(aContractWith(true, true))

        then:
        assert mode == STRICT_ORDER
    }

    def "given strict order is true and extensible is false"() {
        when:
        def mode = toJsonCompareMode(aContractWith(true, false))

        then:
        assert mode == STRICT
    }

    def "given strict order is false and extensible is false"() {
        when:
        def mode = toJsonCompareMode(aContractWith(false, false))

        then:
        assert mode == NON_EXTENSIBLE
    }

    private static Contract aContractWith(strictOrder, extensible) {
        new Contract() {

            @Override
            String value() {
                return null
            }

            @Override
            boolean strictOrder() {
                return strictOrder
            }

            @Override
            boolean extensible() {
                return extensible
            }

            @Override
            Class<? extends Annotation> annotationType() {
                return null
            }
        }
    }
}
