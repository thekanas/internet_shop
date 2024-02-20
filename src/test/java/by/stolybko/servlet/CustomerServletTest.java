package by.stolybko.servlet;

import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.service.impl.CustomerServiceImpl;
import by.stolybko.util.CustomerTestData;
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
class CustomerServletTest {

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerServlet customerServlet;

    @Test
    void doGetWithIdShouldReturnCustomer_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        UUID uuid = CustomerTestData.getCustomerId();
        CustomerResponseDto customerResponseDto = CustomerTestData.getCustomerResponseDto();

        when(customerService.getById(uuid)).thenReturn(customerResponseDto);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        customerServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(customerService).getById(uuid);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"fullName\":\"Test Name Surname\"}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doGetWithoutIdShouldReturnAllCustomer_WhenInvoke() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerResponseDto customerResponseDto1 = CustomerTestData.getCustomerResponseDto();
        CustomerResponseDto customerResponseDto2 = CustomerTestData.getCustomerResponseDto();
        List<CustomerResponseDto> customerResponseDtoList = List.of(customerResponseDto1, customerResponseDto2);

        when(customerService.getAll()).thenReturn(customerResponseDtoList);
        when(request.getParameter("id")).thenReturn(null);

        //when
        customerServlet.doGet(request, response);

        //then
        verify(response).getWriter();
        verify(customerService).getAll();

        String responseContent = stringWriter.toString();
        String expectedContent = "[{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"fullName\":\"Test Name Surname\"},{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"fullName\":\"Test Name Surname\"}]";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPostTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();
        CustomerResponseDto customerResponseDto = CustomerTestData.getCustomerResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customerRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(customerService.save(customerRequestDto)).thenReturn(customerResponseDto);

        //when
        customerServlet.doPost(request, response);

        //then
        verify(response).getWriter();
        verify(customerService).save(customerRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"fullName\":\"Test Name Surname\"}";
        Assertions.assertEquals(expectedContent, responseContent);
    }

    @Test
    void doPutTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = CustomerTestData.getCustomerId();
        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();
        CustomerResponseDto customerResponseDto = CustomerTestData.getCustomerResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customerRequestDto);

        StringReader stringReader = new StringReader(json);
        BufferedReader reader = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(reader);
        when(request.getParameter("id")).thenReturn("" + uuid);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(customerService.update(uuid, customerRequestDto)).thenReturn(customerResponseDto);

        //when
        customerServlet.doPut(request, response);

        //then
        verify(response).getWriter();
        verify(customerService).update(uuid, customerRequestDto);

        String responseContent = stringWriter.toString();
        String expectedContent = "{\"id\":\"25486810-43dd-41e8-ab60-98aa2d200acb\",\"fullName\":\"Test Name Surname\"}";
        Assertions.assertEquals(expectedContent, responseContent);
    }


    @Test
    void doDeleteTest() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = CustomerTestData.getCustomerId();

        when(customerService.deleteById(uuid)).thenReturn(true);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        customerServlet.doDelete(request, response);

        //then
        verify(customerService).deleteById(uuid);
    }

    @Test
    void doDeleteShouldReturnStatusNotFound_whenIdIsNotFound() throws IOException, ServletException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UUID uuid = CustomerTestData.getCustomerId();

        when(customerService.deleteById(uuid)).thenReturn(false);
        when(request.getParameter("id")).thenReturn("" + uuid);

        //when
        customerServlet.doDelete(request, response);

        //then
        verify(customerService).deleteById(uuid);
        verify(response).setStatus(404);
    }

}