package by.stolybko.servlet;

import by.stolybko.config.AppConfig;
import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderResponseDto;
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

import static by.stolybko.util.Constants.CONTENT_TYPE;

@WebServlet(name = "CustomerOrdersServlet", value = "/orders/customer")
public class CustomerOrdersServlet extends HttpServlet {

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

                resp.setStatus(400);

            } else {

                List<OrderResponseDto> orderResponseDtoList = orderService.getAllByCustomerId(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), orderResponseDtoList);
            }

        } catch (Exception e) {
            resp.setStatus(404);
        }
    }

}