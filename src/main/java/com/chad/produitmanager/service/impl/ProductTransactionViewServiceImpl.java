package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.ProductTransactionView;
import com.chad.produitmanager.repository.ProductTransactionViewRepository;
import com.chad.produitmanager.service.ProductTransactionViewService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductTransactionView}.
 */
@Service
@Transactional
public class ProductTransactionViewServiceImpl implements ProductTransactionViewService {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionViewServiceImpl.class);

    private final ProductTransactionViewRepository productTransactionViewRepository;

    public ProductTransactionViewServiceImpl(ProductTransactionViewRepository productTransactionViewRepository) {
        this.productTransactionViewRepository = productTransactionViewRepository;
    }

    @Override
    public ProductTransactionView save(ProductTransactionView productTransactionView) {
        log.debug("Request to save ProductTransactionView : {}", productTransactionView);
        return productTransactionViewRepository.save(productTransactionView);
    }

    @Override
    public ProductTransactionView update(ProductTransactionView productTransactionView) {
        log.debug("Request to update ProductTransactionView : {}", productTransactionView);
        return productTransactionViewRepository.save(productTransactionView);
    }

    @Override
    public Optional<ProductTransactionView> partialUpdate(ProductTransactionView productTransactionView) {
        log.debug("Request to partially update ProductTransactionView : {}", productTransactionView);

        return productTransactionViewRepository
            .findById(productTransactionView.getId())
            .map(existingProductTransactionView -> {
                if (productTransactionView.getProductName() != null) {
                    existingProductTransactionView.setProductName(productTransactionView.getProductName());
                }
                if (productTransactionView.getStockName() != null) {
                    existingProductTransactionView.setStockName(productTransactionView.getStockName());
                }
                if (productTransactionView.getQuantite() != null) {
                    existingProductTransactionView.setQuantite(productTransactionView.getQuantite());
                }
                if (productTransactionView.getTransactionType() != null) {
                    existingProductTransactionView.setTransactionType(productTransactionView.getTransactionType());
                }
                if (productTransactionView.getDescription() != null) {
                    existingProductTransactionView.setDescription(productTransactionView.getDescription());
                }
                if (productTransactionView.getCreatedBy() != null) {
                    existingProductTransactionView.setCreatedBy(productTransactionView.getCreatedBy());
                }
                if (productTransactionView.getCreatedDate() != null) {
                    existingProductTransactionView.setCreatedDate(productTransactionView.getCreatedDate());
                }
                if (productTransactionView.getLastModifiedBy() != null) {
                    existingProductTransactionView.setLastModifiedBy(productTransactionView.getLastModifiedBy());
                }
                if (productTransactionView.getLastModifiedDate() != null) {
                    existingProductTransactionView.setLastModifiedDate(productTransactionView.getLastModifiedDate());
                }

                return existingProductTransactionView;
            })
            .map(productTransactionViewRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductTransactionView> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTransactionViews");
        return productTransactionViewRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTransactionView> findOne(Long id) {
        log.debug("Request to get ProductTransactionView : {}", id);
        return productTransactionViewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductTransactionView : {}", id);
        productTransactionViewRepository.deleteById(id);
    }
}
