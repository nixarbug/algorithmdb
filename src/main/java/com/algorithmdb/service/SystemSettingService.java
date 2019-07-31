package com.algorithmdb.service;

import com.algorithmdb.domain.SystemSetting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SystemSetting}.
 */
public interface SystemSettingService {

    /**
     * Save a systemSetting.
     *
     * @param systemSetting the entity to save.
     * @return the persisted entity.
     */
    SystemSetting save(SystemSetting systemSetting);

    /**
     * Get all the systemSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemSetting> findAll(Pageable pageable);


    /**
     * Get the "id" systemSetting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemSetting> findOne(Long id);

    /**
     * Delete the "id" systemSetting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
