package by.stolybko.controller;

import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.util.CustomerTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }


    @Test
    void getAllCustomer() throws Exception {
        // given
        List<CustomerResponseDto> customers = List.of(CustomerTestData.getCustomerResponseDto());

        Mockito.when(customerService.getAll())
                .thenReturn(customers);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void getCustomerByUuid() throws Exception {
        // given
        CustomerResponseDto customerResponseDto = CustomerTestData.getCustomerResponseDto();
        Mockito.when(customerService.getById(customerResponseDto.id()))
                .thenReturn(customerResponseDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customerResponseDto);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customerResponseDto.id()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerResponseDto.id().toString()));

    }

    @Test
    void createCustomer() throws Exception {
        // given
        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customerRequestDto);

        // when, then
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCustomer() throws Exception {
        // given
        UUID customerId = CustomerTestData.getCustomerId();
        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customerRequestDto);

        // when, then
        mockMvc.perform(put("/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerByUuid() throws Exception {
        // given
        UUID customerId = CustomerTestData.getCustomerId();

        // when, then
        mockMvc.perform(delete("/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}