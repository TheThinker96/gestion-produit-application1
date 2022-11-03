package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.ProductTransaction;
import com.chad.produitmanager.repository.ProductTransactionRepository;
import com.chad.produitmanager.service.ProductTransactionService;
import com.chad.produitmanager.service.dto.ProductTransactionDTO;
import com.chad.produitmanager.service.mapper.ProductTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductTransaction}.
 */
@Service
@Transactional
public class ProductTransactionServiceImpl implements ProductTransactionService {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionServiceImpl.class);

    private final ProductTransactionRepository productTransactionRepository;

    private final ProductTransactionMapper productTransactionMapper;

    public ProductTransactionServiceImpl(
        ProductTransactionRepository productTransactionRepository,
        ProductTransactionMapper productTransactionMapper
    ) {
        this.productTransactionRepository = productTransactionRepository;
        this.productTransactionMapper = productTransactionMapper;
    }

    @Override
    public ProductTransactionDTO save(ProductTransactionDTO productTransactionDTO) {
        log.debug("Request to save ProductTransaction : {}", productTransactionDTO);
        ProductTransaction productTransaction = productTransactionMapper.toEntity(productTransactionDTO);
        productTransaction = productTransactionRepository.save(productTransaction);
        return productTransactionMapper.toDto(productTransaction);
    }

    @Override
    public ProductTransactionDTO update(ProductTransactionDTO productTransactionDTO) {
        log.debug("Request to update ProductTransaction : {}", productTransactionDTO);
        ProductTransaction productTransaction = productTransactionMapper.toEntity(productTransactionDTO);
        productTransaction = productTransactionRepository.save(productTransaction);
        return productTransactionMapper.toDto(productTransaction);
    }

    @Override
    public Optional<ProductTransactionDTO> partialUpdate(ProductTransactionDTO productTransactionDTO) {
        log.debug("Request to partially update ProductTransaction : {}", productTransactionDTO);

        return productTransactionRepository
            .findById(productTransactionDTO.getId())
            .map(existingProductTransaction -> {
                productTransactionMapper.partialUpdate(existingProductTransaction, productTransactionDTO);

                return existingProductTransaction;
            })
            .map(productTransactionRepository::save)
            .map(productTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTransactions");
        return productTransactionRepository.findAll(pageable).map(productTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTransactionDTO> findOne(Long id) {
        log.debug("Request to get ProductTransaction : {}", id);
        return productTransactionRepository.findById(id).map(productTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductTransaction : {}", id);
        productTransactionRepository.deleteById(id);
    }
}
