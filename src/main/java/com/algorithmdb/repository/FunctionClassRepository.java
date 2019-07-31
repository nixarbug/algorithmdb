package com.algorithmdb.repository;

import com.algorithmdb.domain.FunctionClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FunctionClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionClassRepository extends JpaRepository<FunctionClass, Long> {

}
