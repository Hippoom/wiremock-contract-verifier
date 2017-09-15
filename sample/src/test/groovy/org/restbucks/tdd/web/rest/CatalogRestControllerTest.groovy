package org.restbucks.tdd.web.rest

import com.github.hippoom.wiremock.contract.verifier.anntation.Contract
import org.junit.Test
import org.restbucks.tdd.domain.catalog.CatalogRepository
import org.restbucks.tdd.web.AbstractWebMvcTest
import org.restbucks.tdd.web.rest.assembler.CatalogResourceAssembler
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

import static org.mockito.BDDMockito.given
import static org.restbucks.tdd.domain.catalog.CatalogFixture.aCatalog
import static org.restbucks.tdd.domain.catalog.Size.LARGE

@WebMvcTest(controllers = [
    CatalogRestController,
    CatalogResourceAssembler
])
class CatalogRestControllerTest extends AbstractWebMvcTest {

    @MockBean
    private CatalogRepository catalogRepository

    @Contract("catalogs.json")
    @Test
    void "it should return all catalogs"() {

        def first = aCatalog().withName("Espresso").withSize(LARGE).withPrice(32).build()
        def second = aCatalog().build()

        given(catalogRepository.findAll()).willReturn([
            first,
            second
        ])

        // @formatter:off
        this.mockMvc.perform(contractVerifier.requestPattern())
                .andExpect(contractVerifier.responseDefinition())
        // @formatter:on
    }

}
