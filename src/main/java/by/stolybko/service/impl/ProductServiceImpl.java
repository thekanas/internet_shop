package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Product;
import by.stolybko.repository.ProductRepository;
import by.stolybko.service.ProductService;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.mapper.ProductDtoMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productDtoMapper = Mappers.getMapper(ProductDtoMapper.class);
    }


    @Override
    public ProductResponseDto getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));
        return productDtoMapper.toDto(product);
    }

    @Override
    public boolean deleteById(UUID id) {
        return productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(productDtoMapper.toEntity(productRequestDto)).orElseThrow();
        return productDtoMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDto update(UUID id, ProductRequestDto productRequestDto) {
        Product updatedProduct = productRepository.update(id, productDtoMapper.toEntity(productRequestDto))
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));
        return productDtoMapper.toDto(updatedProduct);
    }
}
