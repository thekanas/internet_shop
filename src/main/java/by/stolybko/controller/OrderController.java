package by.stolybko.controller;

import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseDto>> getAllOrder() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getAll();
        return ResponseEntity.ok().body(orderResponseDtoList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponseDtoWithProduct> getOrderByUuid(@PathVariable UUID uuid) {
        OrderResponseDtoWithProduct orderResponseDto = orderService.getByIdWithProduct(uuid);
        return ResponseEntity.ok().body(orderResponseDto);
    }

    @GetMapping("/customer/{uuid}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrderByCustomerUuid(@PathVariable UUID uuid) {
        List<OrderResponseDto> allByCustomerId = orderService.getAllByCustomerId(uuid);
        return ResponseEntity.ok().body(allByCustomerId);
    }

    /*

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

                orderService.deleteById(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }
*/
}
