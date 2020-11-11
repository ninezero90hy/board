package com.lightning.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.deleted = false and b.id = :id")
    Optional<Board> findByIdAndDeletedFalse(@Param("id") Long id);

    @Query("select b from Board b where b.deleted = false")
    Optional<Page<Board>> findAllByDeletedFalse(Pageable pageable);
}
