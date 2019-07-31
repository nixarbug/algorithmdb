package com.algorithmdb.repository;

import com.algorithmdb.domain.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Problem entity.
 */
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = "select distinct problem from Problem problem left join fetch problem.problemGroups",
        countQuery = "select count(distinct problem) from Problem problem")
    Page<Problem> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct problem from Problem problem left join fetch problem.problemGroups")
    List<Problem> findAllWithEagerRelationships();

    @Query("select problem from Problem problem left join fetch problem.problemGroups where problem.id =:id")
    Optional<Problem> findOneWithEagerRelationships(@Param("id") Long id);

}
