package com.acme.banking.dbo.spring.it;

import com.acme.banking.dbo.spring.dao.AccountRepository;
import com.acme.banking.dbo.spring.dao.CustomerRepository;
import com.acme.banking.dbo.spring.domain.Customer;
import com.acme.banking.dbo.spring.domain.SavingAccount;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Any;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//TODO @SpringBootTest VS @WebMvcTest(MyController.class): focus only on the web layer and not start a complete ApplicationContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //TODO Semantics of MOCK: No servlet container started
@AutoConfigureMockMvc
public class CustomerControllerIT {
    @Autowired
    private MockMvc mockMvc; //TODO Exception handling issue: https://github.com/spring-projects/spring-boot/issues/7321#issuecomment-261343803
//    @Autowired private Logger logger;
//    @MockBean private AccountRepository accounts; //TODO MockBean semantics
    @MockBean private CustomerRepository customers; //TODO MockBean semantics
    //TODO If not @SpringBootTest use @TestExecutionListeners(MockitoTestExecutionListener.class)
    //TODO @SpyBean semantics

    @Test
    public void shouldGetNoCustomersWhenCustomersRepoIsEmpty() throws Exception {
        when(customers.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/customer-api/customers").header("X-API-VERSION", "2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value("0"));
    }

    @Test
    public void shouldAddCustomer() throws Exception {
        when(customers.save(any(Customer.class))).thenReturn(null);

        mockMvc.perform(post("/customer-api/addCustomer")
                    .header("X-API-VERSION", "2")
                    .contentType("application/json")
                    .content("{\"id\":\"1\",\"name\":\"customer name\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("customer name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        Customer cust = new Customer(5L, "Certain customer");
        when(customers.findById(5L)).thenReturn(Optional.of(cust));

        mockMvc.perform(get("/customer-api/customer/5")
                .header("X-API-VERSION", "2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(jsonPath("$.name").value("Certain customer"))
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    public void shouldDeleteCustomerById() throws Exception {
        doNothing().when(customers).deleteById(any());

        mockMvc.perform(delete("/customer-api/deleteCustomer/8")
                .header("X-API-VERSION", "2")
                )
                .andExpect(status().is2xxSuccessful());
        verify(customers, times(1)).deleteById(8L);
    }

    @Test
    public void shouldNotDeleteCustomerWithWrongId() throws Exception {
        doThrow(new EmptyResultDataAccessException(4)).when(customers).deleteById(any());

        mockMvc.perform(delete("/customer-api/deleteCustomer/8")
                .header("X-API-VERSION", "2")
                )
                .andExpect(status().is4xxClientError());
    }

}
