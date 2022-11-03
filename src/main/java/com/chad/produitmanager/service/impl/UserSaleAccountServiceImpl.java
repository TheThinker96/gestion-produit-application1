package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.UserSaleAccount;
import com.chad.produitmanager.repository.UserSaleAccountRepository;
import com.chad.produitmanager.service.UserSaleAccountService;
import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import com.chad.produitmanager.service.mapper.UserSaleAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserSaleAccount}.
 */
@Service
@Transactional
public class UserSaleAccountServiceImpl implements UserSaleAccountService {

    private final Logger log = LoggerFactory.getLogger(UserSaleAccountServiceImpl.class);

    private final UserSaleAccountRepository userSaleAccountRepository;

    private final UserSaleAccountMapper userSaleAccountMapper;

    public UserSaleAccountServiceImpl(UserSaleAccountRepository userSaleAccountRepository, UserSaleAccountMapper userSaleAccountMapper) {
        this.userSaleAccountRepository = userSaleAccountRepository;
        this.userSaleAccountMapper = userSaleAccountMapper;
    }

    @Override
    public UserSaleAccountDTO save(UserSaleAccountDTO userSaleAccountDTO) {
        log.debug("Request to save UserSaleAccount : {}", userSaleAccountDTO);
        UserSaleAccount userSaleAccount = userSaleAccountMapper.toEntity(userSaleAccountDTO);
        userSaleAccount = userSaleAccountRepository.save(userSaleAccount);
        return userSaleAccountMapper.toDto(userSaleAccount);
    }

    @Override
    public UserSaleAccountDTO update(UserSaleAccountDTO userSaleAccountDTO) {
        log.debug("Request to update UserSaleAccount : {}", userSaleAccountDTO);
        UserSaleAccount userSaleAccount = userSaleAccountMapper.toEntity(userSaleAccountDTO);
        userSaleAccount = userSaleAccountRepository.save(userSaleAccount);
        return userSaleAccountMapper.toDto(userSaleAccount);
    }

    @Override
    public Optional<UserSaleAccountDTO> partialUpdate(UserSaleAccountDTO userSaleAccountDTO) {
        log.debug("Request to partially update UserSaleAccount : {}", userSaleAccountDTO);

        return userSaleAccountRepository
            .findById(userSaleAccountDTO.getId())
            .map(existingUserSaleAccount -> {
                userSaleAccountMapper.partialUpdate(existingUserSaleAccount, userSaleAccountDTO);

                return existingUserSaleAccount;
            })
            .map(userSaleAccountRepository::save)
            .map(userSaleAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSaleAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSaleAccounts");
        return userSaleAccountRepository.findAll(pageable).map(userSaleAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSaleAccountDTO> findOne(Long id) {
        log.debug("Request to get UserSaleAccount : {}", id);
        return userSaleAccountRepository.findById(id).map(userSaleAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSaleAccount : {}", id);
        userSaleAccountRepository.deleteById(id);
    }
}
