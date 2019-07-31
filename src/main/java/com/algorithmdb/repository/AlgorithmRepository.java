package com.algorithmdb.repository;

import com.algorithmdb.domain.Algorithm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Algorithm entity.
 */
@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    @Query(value = "select distinct algorithm from Algorithm algorithm left join fetch algorithm.authors left join fetch algorithm.tags left join fetch algorithm.problems",
        countQuery = "select count(distinct algorithm) from Algorithm algorithm")
    Page<Algorithm> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct algorithm from Algorithm algorithm left join fetch algorithm.authors left join fetch algorithm.tags left join fetch algorithm.problems")
    List<Algorithm> findAllWithEagerRelationships();

    @Query("select algorithm from Algorithm algorithm left join fetch algorithm.authors left join fetch algorithm.tags left join fetch algorithm.problems where algorithm.id =:id")
    Optional<Algorithm> findOneWithEagerRelationships(@Param("id") Long id);

}
