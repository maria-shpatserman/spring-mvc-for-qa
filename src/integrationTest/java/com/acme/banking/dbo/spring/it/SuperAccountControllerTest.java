package com.acme.banking.dbo.spring.it;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //TODO Semantics of MOCK: No servlet container started
@AutoConfigureMockMvc
@Ignore
public class SuperAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testSuperAccount() throws Exception{
        mockMvc.perform(get("/super-api/accounts").header("X-API-VERSION", "1"))
                .andExpect(status().is2xxSuccessful());
                //.andExpect(jsonPath("$.length()").value("0"));
    }
}

