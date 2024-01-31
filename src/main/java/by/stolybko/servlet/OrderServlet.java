package by.stolybko.servlet;

import by.stolybko.config.AppConfig;
import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static by.stolybko.util.Constants.CONTENT_TYPE;

@WebServlet(name = "OrderServlet", value = "/orders")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) {
        this.orderService = AppConfig.getOrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            if (id == null) {

                List<OrderResponseDto> orderResponseDtoList = orderService.getAll();

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), orderResponseDtoList);

            } else {

                OrderResponseDtoWithProduct orderResponseDto = orderService.getByIdWithProduct(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), orderResponseDto);
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            OrderRequestDto orderRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), OrderRequestDto.class);
            OrderResponseDto orderResponseDto = orderService.save(orderRequestDto);

            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(200);

            objectMapper.writeValue(resp.getWriter(), orderResponseDto);

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

                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

                OrderRequestDto orderRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), OrderRequestDto.class);
                OrderResponseDto orderResponseDto = orderService.update(UUID.fromString(id), orderRequestDto);

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), orderResponseDto);
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

                boolean delete = orderService.deleteById(UUID.fromString(id));

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
