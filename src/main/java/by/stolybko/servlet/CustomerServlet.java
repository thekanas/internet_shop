package by.stolybko.servlet;

import by.stolybko.config.AppConfig;
import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.stolybko.util.Constants.CONTENT_TYPE;

@WebServlet(name = "CustomerServlet", value = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init(ServletConfig config) {
        this.customerService = AppConfig.getCustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            ObjectMapper objectMapper = new ObjectMapper();

            if (id == null) {

                List<CustomerResponseDto> customerResponseDtoList = customerService.getAll();

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), customerResponseDtoList);

            } else {

                CustomerResponseDto customerResponseDto = customerService.getById(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), customerResponseDto);
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            BufferedReader reader = req.getReader();
            Stream<String> lines = reader.lines();
            String collect = lines.collect(Collectors.joining());

            CustomerRequestDto customerRequestDto = objectMapper.readValue(collect, CustomerRequestDto.class);


            //CustomerRequestDto customerRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), CustomerRequestDto.class);
            CustomerResponseDto customerResponseDto = customerService.save(customerRequestDto);

            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(200);

            objectMapper.writeValue(resp.getWriter(), customerResponseDto);

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");

            if (id == null) {
                resp.setStatus(400);
            } else {

                ObjectMapper objectMapper = new ObjectMapper();
                CustomerRequestDto customerRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), CustomerRequestDto.class);
                CustomerResponseDto customerResponseDto = customerService.update(UUID.fromString(id), customerRequestDto);

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), customerResponseDto);
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");

            if (id == null) {
                resp.setStatus(400);
            } else {

                boolean delete = customerService.deleteById(UUID.fromString(id));

                if (delete) {
                    resp.setContentType(CONTENT_TYPE);
                    resp.setStatus(200);
                } else {
                    resp.setStatus(404);
                }
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

}
