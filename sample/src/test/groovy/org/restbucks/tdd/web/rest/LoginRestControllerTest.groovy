package org.restbucks.tdd.web.rest

import com.github.hippoom.wiremock.contract.verifier.anntation.Contract
import org.junit.Test
import org.restbucks.tdd.domain.ordering.OrderRepository
import org.restbucks.tdd.web.AbstractWebMvcTest
import org.restbucks.tdd.web.rest.assembler.OrderResourceAssembler
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import static org.hamcrest.Matchers.is
import static org.mockito.BDDMockito.given
import static org.restbucks.tdd.domain.ordering.Location.TAKE_AWAY
import static org.restbucks.tdd.domain.ordering.Order.newOrder
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [
    LoginRestController
])
class LoginRestControllerTest extends AbstractWebMvcTest {

    @Contract("login.json")
    @Test
    void "it should handle form post"() {

        // @formatter:off
        this.mockMvc.perform(contractVerifier.requestPattern())
                .andExpect(contractVerifier.responseDefinition())
        // @formatter:on
    }
}
