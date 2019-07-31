package com.algorithmdb.repository;

import com.algorithmdb.domain.ProblemGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProblemGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemGroupRepository extends JpaRepository<ProblemGroup, Long> {

}
