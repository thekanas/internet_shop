package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Product;
import by.stolybko.repository.ProductRepository;
import by.stolybko.service.ProductService;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.mapper.ProductDtoMapper;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }


    @Override
    public ProductResponseDto getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));
        return productDtoMapper.toDto(product);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(productDtoMapper.toEntity(productRequestDto));
        return productDtoMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDto update(UUID id, ProductRequestDto productRequestDto) {
        Product updateProduct = productDtoMapper.toEntity(productRequestDto);
        updateProduct.setId(id);
        Product updatedProduct = productRepository.save(updateProduct);
        return productDtoMapper.toDto(updatedProduct);
    }
}
