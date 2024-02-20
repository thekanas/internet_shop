package by.stolybko.servlet;

import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.impl.OrderServiceImpl;
import by.stolybko.util.CustomerTestData;
import by.stolybko.util.OrderTestData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrdersServletTest {

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private CustomerOrdersServlet customerOrdersServlet;


    @Test
    void doGet() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        UUID uuid = CustomerTestData.getCustomerId();
        OrderResponseDto orderResponseDto1 = OrderTestData.getOrderResponseDto();
        OrderResponseDto orderResponseDto2 = OrderTestData.getOrderResponseDto();
        List<OrderResponseDto> orderResponseDtoList = List.of(orderResponseDto1, orderResponseDto2);

        when(orderService.getAllByCustomerId(uuid)).thenReturn(orderResponseDtoList);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        customerOrdersServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(orderService).getAllByCustomerId(uuid);

        String responseContent = stringWriter.toString();
        String expectedContent = "[{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"},{\"id\":\"5f0cba7d-643b-48c2-8713-5efc2983acfe\",\"customerId\":\"58cf8a5b-3f98-4180-86d2-c22e033642f5\",\"createDate\":\"01-01-2024 01:01:01\"}]";
        Assertions.assertEquals(expectedContent, responseContent);

    }

    @Test
    void doGetShouldReturnStatus400_whenIdIsNull() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(null);

        //when
        customerOrdersServlet.doGet(request, response);

        //then
        verify(response).setStatus(400);

    }

}