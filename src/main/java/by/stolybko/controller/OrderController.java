package by.stolybko.controller;

import by.stolybko.service.OrderService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto order = orderService.save(orderRequestDto);
        return ResponseEntity.status(201).body(order);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable UUID uuid, @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto order = orderService.update(uuid, orderRequestDto);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteOrderByUuid(@PathVariable UUID uuid) {
        orderService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

}
