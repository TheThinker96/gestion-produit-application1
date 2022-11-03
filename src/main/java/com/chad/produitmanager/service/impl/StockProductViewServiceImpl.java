package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.StockProductView;
import com.chad.produitmanager.repository.StockProductViewRepository;
import com.chad.produitmanager.service.StockProductViewService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StockProductView}.
 */
@Service
@Transactional
public class StockProductViewServiceImpl implements StockProductViewService {

    private final Logger log = LoggerFactory.getLogger(StockProductViewServiceImpl.class);

    private final StockProductViewRepository stockProductViewRepository;

    public StockProductViewServiceImpl(StockProductViewRepository stockProductViewRepository) {
        this.stockProductViewRepository = stockProductViewRepository;
    }

    @Override
    public StockProductView save(StockProductView stockProductView) {
        log.debug("Request to save StockProductView : {}", stockProductView);
        return stockProductViewRepository.save(stockProductView);
    }

    @Override
    public StockProductView update(StockProductView stockProductView) {
        log.debug("Request to update StockProductView : {}", stockProductView);
        return stockProductViewRepository.save(stockProductView);
    }

    @Override
    public Optional<StockProductView> partialUpdate(StockProductView stockProductView) {
        log.debug("Request to partially update StockProductView : {}", stockProductView);

        return stockProductViewRepository
            .findById(stockProductView.getId())
            .map(existingStockProductView -> {
                if (stockProductView.getQuantite() != null) {
                    existingStockProductView.setQuantite(stockProductView.getQuantite());
                }
                if (stockProductView.getStockName() != null) {
                    existingStockProductView.setStockName(stockProductView.getStockName());
                }
                if (stockProductView.getProductName() != null) {
                    existingStockProductView.setProductName(stockProductView.getProductName());
                }
                if (stockProductView.getDeliveryDate() != null) {
                    existingStockProductView.setDeliveryDate(stockProductView.getDeliveryDate());
                }
                if (stockProductView.getExpirationDate() != null) {
                    existingStockProductView.setExpirationDate(stockProductView.getExpirationDate());
                }
                if (stockProductView.getCreatedBy() != null) {
                    existingStockProductView.setCreatedBy(stockProductView.getCreatedBy());
                }
                if (stockProductView.getCreatedDate() != null) {
                    existingStockProductView.setCreatedDate(stockProductView.getCreatedDate());
                }
                if (stockProductView.getLastModifiedBy() != null) {
                    existingStockProductView.setLastModifiedBy(stockProductView.getLastModifiedBy());
                }
                if (stockProductView.getLastModifiedDate() != null) {
                    existingStockProductView.setLastModifiedDate(stockProductView.getLastModifiedDate());
                }

                return existingStockProductView;
            })
            .map(stockProductViewRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockProductView> findAll(Pageable pageable) {
        log.debug("Request to get all StockProductViews");
        return stockProductViewRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockProductView> findOne(Long id) {
        log.debug("Request to get StockProductView : {}", id);
        return stockProductViewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockProductView : {}", id);
        stockProductViewRepository.deleteById(id);
    }
}
