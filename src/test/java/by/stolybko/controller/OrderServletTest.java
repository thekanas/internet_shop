package by.stolybko.controller;


import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.impl.OrderServiceImpl;
import by.stolybko.util.OrderTestData;
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
class OrderServletTest {

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderServlet;

    @Test
    void doGetWithIdShouldReturnOrder_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        UUID uuid = OrderTestData.getOrderId();
        OrderResponseDtoWithProduct orderResponseDto = OrderTestData.getOrderResponseDtoWithProduct();

        when(orderService.getByIdWithProduct(uuid)).thenReturn(orderResponseDto);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        orderServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(orderService).getByIdWithProduct(uuid);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\",\"products\":[{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99},{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"name\":\"SomeProduct\",\"price\":9.99}]}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doGetWithoutIdShouldReturnAllOrder_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        OrderResponseDto orderResponseDto1 = OrderTestData.getOrderResponseDto();
        OrderResponseDto orderResponseDto2 = OrderTestData.getOrderResponseDto();
        List<OrderResponseDto> orderResponseDtoList = List.of(orderResponseDto1, orderResponseDto2);

        when(orderService.getAll()).thenReturn(orderResponseDtoList);
        when(request.getParameter("id")).thenReturn(null);

        //when
        orderServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(orderService).getAll();

        String responseContent = stringWriter.toString();
        String expectedContent = "[{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"},{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"}]";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPostTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        OrderResponseDto orderResponseDto = OrderTestData.getOrderResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(orderService.save(orderRequestDto)).thenReturn(orderResponseDto);

        //when
        orderServlet.doPost(request, response);

        //then
        verify(response).getWriter();
        verify(orderService).save(orderRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPutTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = OrderTestData.getOrderId();
        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        OrderResponseDto orderResponseDto = OrderTestData.getOrderResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);
        when(request.getParameter("id")).thenReturn("" + uuid);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(orderService.update(uuid, orderRequestDto)).thenReturn(orderResponseDto);

        //when
        orderServlet.doPut(request, response);

        //then
        verify(response).getWriter();
        verify(orderService).update(uuid, orderRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"}";
        Assertions.assertEquals(expectedContent, responseContent);
    }


    @Test
    void doDeleteTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = OrderTestData.getOrderId();

        when(orderService.deleteById(uuid)).thenReturn(true);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        orderServlet.doDelete(request, response);

        //then
        verify(orderService).deleteById(uuid);
        verify(response).setStatus(200);
    }

    @Test
    void doDeleteShouldReturnStatusNotFound_whenIdIsNotFound() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = OrderTestData.getOrderId();

        when(orderService.deleteById(uuid)).thenReturn(false);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        orderServlet.doDelete(request, response);

        //then
        verify(orderService).deleteById(uuid);
        verify(response).setStatus(404);
    }

}
