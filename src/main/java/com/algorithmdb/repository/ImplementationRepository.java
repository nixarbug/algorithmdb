package com.algorithmdb.repository;

import com.algorithmdb.domain.Implementation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Implementation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImplementationRepository extends JpaRepository<Implementation, Long> {

}
