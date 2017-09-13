package org.restbucks.tdd.web.rest

import com.github.hippoom.wiremock.contract.verifier.MockHttpServletRequestBuilderMapper
import com.github.hippoom.wiremock.contract.verifier.ResultMatcherMapper
import org.junit.Test
import org.restbucks.tdd.domain.catalog.CatalogRepository
import org.restbucks.tdd.web.AbstractWebMvcTest
import org.restbucks.tdd.web.rest.assembler.CatalogResourceAssembler
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

import static com.github.tomakehurst.wiremock.stubbing.StubMapping.buildFrom
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

    @Test
    void "it should return all catalogs"() {

        def first = aCatalog().withName("Espresso").withSize(LARGE).withPrice(32).build()
        def second = aCatalog().build()

        given(catalogRepository.findAll()).willReturn([
            first,
            second
        ])

        // @formatter:off
        expectResponse("catalogs.json", this.mockMvc.perform(requestBy("catalogs.json"))
                .andReturn())
        // @formatter:on
    }

    private static MockHttpServletRequestBuilder requestBy(contractPath) {
        new MockHttpServletRequestBuilderMapper().from(buildFrom(fromJson(contractPath)))
    }

    private static MockHttpServletRequestBuilder expectResponse(contractPath, MvcResult mvcResult) {
        new ResultMatcherMapper().match(buildFrom(fromJson(contractPath)), mvcResult)
    }

    private static String fromJson(contractPath) {
        new ClassPathResource("contracts/${contractPath}").getFile().getText("utf-8")
    }

}
