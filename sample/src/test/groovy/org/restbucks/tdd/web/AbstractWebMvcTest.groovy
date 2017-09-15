package org.restbucks.tdd.web

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner)
@Import(MockMvcBuilderCustomizers)
abstract class AbstractWebMvcTest {

    @Autowired
    protected MockMvc mockMvc

}
