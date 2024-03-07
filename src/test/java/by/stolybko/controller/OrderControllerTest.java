package by.stolybko.controller;

import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.util.CustomerTestData;
import by.stolybko.util.OrderTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
    }


    @Test
    void getAllOrder() throws Exception {
        // given
        List<OrderResponseDto> orders = List.of(OrderTestData.getOrderResponseDto());

        Mockito.when(orderService.getAll())
                .thenReturn(orders);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void getOrderByUuid() throws Exception {
        // given
        OrderResponseDtoWithProduct orderResponseDtoWithProduct = OrderTestData.getOrderResponseDtoWithProduct();
        Mockito.when(orderService.getByIdWithProduct(orderResponseDtoWithProduct.id()))
                .thenReturn(orderResponseDtoWithProduct);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + orderResponseDtoWithProduct.id()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderResponseDtoWithProduct.id().toString()));

    }

    @Test
    void getAllOrderByCustomerUuid() throws Exception {
        // given
        UUID customerId = CustomerTestData.getCustomerId();
        OrderResponseDto orderResponseDto = OrderTestData.getOrderResponseDto();
        Mockito.when(orderService.getAllByCustomerId(customerId))
                .thenReturn(List.of(orderResponseDto));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/customer/" + customerId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void createOrder() throws Exception {
        // given
        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderRequestDto);

        // when, then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateOrder() throws Exception {
        // given
        UUID orderId = OrderTestData.getOrderId();
        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderRequestDto);

        // when, then
        mockMvc.perform(put("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrderByUuid() throws Exception {
        // given
        UUID orderId = OrderTestData.getOrderId();

        // when, then
        mockMvc.perform(delete("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}