package by.stolybko.controller;

import by.stolybko.service.ProductService;
import by.stolybko.service.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getAll();
        return ResponseEntity.ok().body(productResponseDtoList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> getProductByUuid(@PathVariable UUID uuid) {
        ProductResponseDto productResponseDto = productService.getById(uuid);
        return ResponseEntity.ok().body(productResponseDto);
    }



    /*

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequestDto productRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), ProductRequestDto.class);
            ProductResponseDto productResponseDto = productService.save(productRequestDto);

            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(200);

            objectMapper.writeValue(resp.getWriter(), productResponseDto);

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
                ProductRequestDto productRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), ProductRequestDto.class);
                ProductResponseDto productResponseDto = productService.update(UUID.fromString(id), productRequestDto);

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), productResponseDto);
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

                productService.deleteById(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }
*/
}
