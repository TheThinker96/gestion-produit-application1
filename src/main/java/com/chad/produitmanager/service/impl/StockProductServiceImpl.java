package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.StockProduct;
import com.chad.produitmanager.repository.StockProductRepository;
import com.chad.produitmanager.service.StockProductService;
import com.chad.produitmanager.service.dto.StockProductDTO;
import com.chad.produitmanager.service.mapper.StockProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StockProduct}.
 */
@Service
@Transactional
public class StockProductServiceImpl implements StockProductService {

    private final Logger log = LoggerFactory.getLogger(StockProductServiceImpl.class);

    private final StockProductRepository stockProductRepository;

    private final StockProductMapper stockProductMapper;

    public StockProductServiceImpl(StockProductRepository stockProductRepository, StockProductMapper stockProductMapper) {
        this.stockProductRepository = stockProductRepository;
        this.stockProductMapper = stockProductMapper;
    }

    @Override
    public StockProductDTO save(StockProductDTO stockProductDTO) {
        log.debug("Request to save StockProduct : {}", stockProductDTO);
        StockProduct stockProduct = stockProductMapper.toEntity(stockProductDTO);
        stockProduct = stockProductRepository.save(stockProduct);
        return stockProductMapper.toDto(stockProduct);
    }

    @Override
    public StockProductDTO update(StockProductDTO stockProductDTO) {
        log.debug("Request to update StockProduct : {}", stockProductDTO);
        StockProduct stockProduct = stockProductMapper.toEntity(stockProductDTO);
        stockProduct = stockProductRepository.save(stockProduct);
        return stockProductMapper.toDto(stockProduct);
    }

    @Override
    public Optional<StockProductDTO> partialUpdate(StockProductDTO stockProductDTO) {
        log.debug("Request to partially update StockProduct : {}", stockProductDTO);

        return stockProductRepository
            .findById(stockProductDTO.getId())
            .map(existingStockProduct -> {
                stockProductMapper.partialUpdate(existingStockProduct, stockProductDTO);

                return existingStockProduct;
            })
            .map(stockProductRepository::save)
            .map(stockProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockProducts");
        return stockProductRepository.findAll(pageable).map(stockProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockProductDTO> findOne(Long id) {
        log.debug("Request to get StockProduct : {}", id);
        return stockProductRepository.findById(id).map(stockProductMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockProduct : {}", id);
        stockProductRepository.deleteById(id);
    }
}
