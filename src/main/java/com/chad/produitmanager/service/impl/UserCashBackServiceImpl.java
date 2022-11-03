package com.chad.produitmanager.service.impl;

import com.chad.produitmanager.domain.UserCashBack;
import com.chad.produitmanager.repository.UserCashBackRepository;
import com.chad.produitmanager.service.UserCashBackService;
import com.chad.produitmanager.service.dto.UserCashBackDTO;
import com.chad.produitmanager.service.mapper.UserCashBackMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserCashBack}.
 */
@Service
@Transactional
public class UserCashBackServiceImpl implements UserCashBackService {

    private final Logger log = LoggerFactory.getLogger(UserCashBackServiceImpl.class);

    private final UserCashBackRepository userCashBackRepository;

    private final UserCashBackMapper userCashBackMapper;

    public UserCashBackServiceImpl(UserCashBackRepository userCashBackRepository, UserCashBackMapper userCashBackMapper) {
        this.userCashBackRepository = userCashBackRepository;
        this.userCashBackMapper = userCashBackMapper;
    }

    @Override
    public UserCashBackDTO save(UserCashBackDTO userCashBackDTO) {
        log.debug("Request to save UserCashBack : {}", userCashBackDTO);
        UserCashBack userCashBack = userCashBackMapper.toEntity(userCashBackDTO);
        userCashBack = userCashBackRepository.save(userCashBack);
        return userCashBackMapper.toDto(userCashBack);
    }

    @Override
    public UserCashBackDTO update(UserCashBackDTO userCashBackDTO) {
        log.debug("Request to update UserCashBack : {}", userCashBackDTO);
        UserCashBack userCashBack = userCashBackMapper.toEntity(userCashBackDTO);
        userCashBack = userCashBackRepository.save(userCashBack);
        return userCashBackMapper.toDto(userCashBack);
    }

    @Override
    public Optional<UserCashBackDTO> partialUpdate(UserCashBackDTO userCashBackDTO) {
        log.debug("Request to partially update UserCashBack : {}", userCashBackDTO);

        return userCashBackRepository
            .findById(userCashBackDTO.getId())
            .map(existingUserCashBack -> {
                userCashBackMapper.partialUpdate(existingUserCashBack, userCashBackDTO);

                return existingUserCashBack;
            })
            .map(userCashBackRepository::save)
            .map(userCashBackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserCashBackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCashBacks");
        return userCashBackRepository.findAll(pageable).map(userCashBackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserCashBackDTO> findOne(Long id) {
        log.debug("Request to get UserCashBack : {}", id);
        return userCashBackRepository.findById(id).map(userCashBackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCashBack : {}", id);
        userCashBackRepository.deleteById(id);
    }
}
