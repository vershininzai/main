package ru.mku;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MkuApplicationTests {

    @Autowired
    WebApplicationContext context;

    @Autowired
    FilterChainProxy filterChain;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SecurityProperties security;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.context).addFilters(this.filterChain).build();
        SecurityContextHolder.clearContext();
    }

//    @Test
//    public void homePageAvailable() throws Exception {
//        this.mvc.perform(get("/").accept(MediaTypes.HAL_JSON)).andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void usersSecuredByDefault() throws Exception {
//        this.mvc.perform(get("/users").accept(MediaTypes.HAL_JSON))
//                .andExpect(status().isUnauthorized()).andDo(print());
//        this.mvc.perform(get("/users/1").accept(MediaTypes.HAL_JSON))
//                .andExpect(status().isUnauthorized()).andDo(print());
//    }
//
//    @Test
//    public void profileAvailable() throws Exception {
//        this.mvc.perform(get("/profile").accept(MediaTypes.HAL_JSON))
//                .andExpect(status().isOk()).andDo(print());
//    }

    @Test
    public void canAuch() throws Exception {
        ResponseEntity<String> entity =
                this.restTemplate.withBasicAuth("admin", "admin")
                        .getForEntity("/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
