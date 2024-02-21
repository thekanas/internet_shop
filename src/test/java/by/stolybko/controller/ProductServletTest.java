package by.stolybko.controller;

import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.impl.ProductServiceImpl;
import by.stolybko.util.ProductTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServletTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productServlet;

    @Test
    void doGetWithIdShouldReturnProduct_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        UUID uuid = ProductTestData.getProductId();
        ProductResponseDto productResponseDto = ProductTestData.getProductResponseDto();

        when(productService.getById(uuid)).thenReturn(productResponseDto);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        productServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(productService).getById(uuid);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doGetWithoutIdShouldReturnAllProduct_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        ProductResponseDto productResponseDto1 = ProductTestData.getProductResponseDto();
        ProductResponseDto productResponseDto2 = ProductTestData.getProductResponseDto();
        List<ProductResponseDto> productResponseDtoList = List.of(productResponseDto1, productResponseDto2);

        when(productService.getAll()).thenReturn(productResponseDtoList);
        when(request.getParameter("id")).thenReturn(null);

        //when
        productServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(productService).getAll();

        String responseContent = stringWriter.toString();
        String expectedContent = "[{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99},{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99}]";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPostTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();
        ProductResponseDto productResponseDto = ProductTestData.getProductResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(productService.save(productRequestDto)).thenReturn(productResponseDto);

        //when
        productServlet.doPost(request, response);

        //then
        verify(response).getWriter();
        verify(productService).save(productRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPutTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = ProductTestData.getProductId();
        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();
        ProductResponseDto productResponseDto = ProductTestData.getProductResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);
        when(request.getParameter("id")).thenReturn("" + uuid);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(productService.update(uuid, productRequestDto)).thenReturn(productResponseDto);

        //when
        productServlet.doPut(request, response);

        //then
        verify(response).getWriter();
        verify(productService).update(uuid, productRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99}";
        Assertions.assertEquals(expectedContent, responseContent);
    }


    @Test
    void doDeleteTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = ProductTestData.getProductId();

        when(productService.deleteById(uuid)).thenReturn(true);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        productServlet.doDelete(request, response);

        //then
        verify(productService).deleteById(uuid);
        verify(response).setStatus(200);
    }

    @Test
    void doDeleteShouldReturnStatusNotFound_whenIdIsNotFound() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = ProductTestData.getProductId();

        when(productService.deleteById(uuid)).thenReturn(false);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        productServlet.doDelete(request, response);

        //then
        verify(productService).deleteById(uuid);
        verify(response).setStatus(404);
    }

}
