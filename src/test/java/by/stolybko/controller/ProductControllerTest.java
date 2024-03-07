package by.stolybko.controller;

import by.stolybko.service.ProductService;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.util.ProductTestData;
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
class ProductControllerTest {

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
    }


    @Test
    void getAllProduct() throws Exception {
        // given
        List<ProductResponseDto> products = List.of(ProductTestData.getProductResponseDto());

        Mockito.when(productService.getAll())
                .thenReturn(products);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void getProductByUuid() throws Exception {
        // given
        ProductResponseDto productResponseDto = ProductTestData.getProductResponseDto();
        Mockito.when(productService.getById(productResponseDto.id()))
                .thenReturn(productResponseDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productResponseDto);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productResponseDto.id()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(productResponseDto.id().toString()));

    }

    @Test
    void createProduct() throws Exception {
        // given
        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productRequestDto);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProduct() throws Exception {
        // given
        UUID productId = ProductTestData.getProductId();
        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productRequestDto);

        // when, then
        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductByUuid() throws Exception {
        // given
        UUID productId = ProductTestData.getProductId();

        // when, then
        mockMvc.perform(delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
