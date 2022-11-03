package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.ProductSaleView;
import com.chad.produitmanager.repository.ProductSaleViewRepository;
import com.chad.produitmanager.service.ProductSaleViewService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductSaleView}.
 */
@Service
@Transactional
public class ProductSaleViewServiceImpl implements ProductSaleViewService {

    private final Logger log = LoggerFactory.getLogger(ProductSaleViewServiceImpl.class);

    private final ProductSaleViewRepository productSaleViewRepository;

    public ProductSaleViewServiceImpl(ProductSaleViewRepository productSaleViewRepository) {
        this.productSaleViewRepository = productSaleViewRepository;
    }

    @Override
    public ProductSaleView save(ProductSaleView productSaleView) {
        log.debug("Request to save ProductSaleView : {}", productSaleView);
        return productSaleViewRepository.save(productSaleView);
    }

    @Override
    public ProductSaleView update(ProductSaleView productSaleView) {
        log.debug("Request to update ProductSaleView : {}", productSaleView);
        return productSaleViewRepository.save(productSaleView);
    }

    @Override
    public Optional<ProductSaleView> partialUpdate(ProductSaleView productSaleView) {
        log.debug("Request to partially update ProductSaleView : {}", productSaleView);

        return productSaleViewRepository
            .findById(productSaleView.getId())
            .map(existingProductSaleView -> {
                if (productSaleView.getProductName() != null) {
                    existingProductSaleView.setProductName(productSaleView.getProductName());
                }
                if (productSaleView.getStockName() != null) {
                    existingProductSaleView.setStockName(productSaleView.getStockName());
                }
                if (productSaleView.getQuantite() != null) {
                    existingProductSaleView.setQuantite(productSaleView.getQuantite());
                }
                if (productSaleView.getProductPrice() != null) {
                    existingProductSaleView.setProductPrice(productSaleView.getProductPrice());
                }
                if (productSaleView.getTotal() != null) {
                    existingProductSaleView.setTotal(productSaleView.getTotal());
                }
                if (productSaleView.getCreatedBy() != null) {
                    existingProductSaleView.setCreatedBy(productSaleView.getCreatedBy());
                }
                if (productSaleView.getCreatedDate() != null) {
                    existingProductSaleView.setCreatedDate(productSaleView.getCreatedDate());
                }
                if (productSaleView.getLastModifiedBy() != null) {
                    existingProductSaleView.setLastModifiedBy(productSaleView.getLastModifiedBy());
                }
                if (productSaleView.getLastModifiedDate() != null) {
                    existingProductSaleView.setLastModifiedDate(productSaleView.getLastModifiedDate());
                }

                return existingProductSaleView;
            })
            .map(productSaleViewRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSaleView> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSaleViews");
        return productSaleViewRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSaleView> findOne(Long id) {
        log.debug("Request to get ProductSaleView : {}", id);
        return productSaleViewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSaleView : {}", id);
        productSaleViewRepository.deleteById(id);
    }
}
