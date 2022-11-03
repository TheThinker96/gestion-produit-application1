package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.ProductSale;
import com.chad.produitmanager.repository.ProductSaleRepository;
import com.chad.produitmanager.service.ProductSaleService;
import com.chad.produitmanager.service.dto.ProductSaleDTO;
import com.chad.produitmanager.service.mapper.ProductSaleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductSale}.
 */
@Service
@Transactional
public class ProductSaleServiceImpl implements ProductSaleService {

    private final Logger log = LoggerFactory.getLogger(ProductSaleServiceImpl.class);

    private final ProductSaleRepository productSaleRepository;

    private final ProductSaleMapper productSaleMapper;

    public ProductSaleServiceImpl(ProductSaleRepository productSaleRepository, ProductSaleMapper productSaleMapper) {
        this.productSaleRepository = productSaleRepository;
        this.productSaleMapper = productSaleMapper;
    }

    @Override
    public ProductSaleDTO save(ProductSaleDTO productSaleDTO) {
        log.debug("Request to save ProductSale : {}", productSaleDTO);
        ProductSale productSale = productSaleMapper.toEntity(productSaleDTO);
        productSale = productSaleRepository.save(productSale);
        return productSaleMapper.toDto(productSale);
    }

    @Override
    public ProductSaleDTO update(ProductSaleDTO productSaleDTO) {
        log.debug("Request to update ProductSale : {}", productSaleDTO);
        ProductSale productSale = productSaleMapper.toEntity(productSaleDTO);
        productSale = productSaleRepository.save(productSale);
        return productSaleMapper.toDto(productSale);
    }

    @Override
    public Optional<ProductSaleDTO> partialUpdate(ProductSaleDTO productSaleDTO) {
        log.debug("Request to partially update ProductSale : {}", productSaleDTO);

        return productSaleRepository
            .findById(productSaleDTO.getId())
            .map(existingProductSale -> {
                productSaleMapper.partialUpdate(existingProductSale, productSaleDTO);

                return existingProductSale;
            })
            .map(productSaleRepository::save)
            .map(productSaleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSales");
        return productSaleRepository.findAll(pageable).map(productSaleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSaleDTO> findOne(Long id) {
        log.debug("Request to get ProductSale : {}", id);
        return productSaleRepository.findById(id).map(productSaleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSale : {}", id);
        productSaleRepository.deleteById(id);
    }
}
