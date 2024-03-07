package by.stolybko.controller;

import by.stolybko.service.ProductService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
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

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto product = productService.save(productRequestDto);
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID uuid, @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto product = productService.update(uuid, productRequestDto);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteProductByUuid(@PathVariable UUID uuid) {
        productService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

}
