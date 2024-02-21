package by.stolybko.controller;

import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
        List<CustomerResponseDto> customers = customerService.getAll();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerResponseDto> getCustomerByUuid(@PathVariable UUID uuid) {
        CustomerResponseDto customer = customerService.getById(uuid);
        return ResponseEntity.ok().body(customer);
    }


/*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            BufferedReader reader = req.getReader();
            Stream<String> lines = reader.lines();
            String collect = lines.collect(Collectors.joining());

            CustomerRequestDto customerRequestDto = objectMapper.readValue(collect, CustomerRequestDto.class);

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

                customerService.deleteById(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }*/

}
