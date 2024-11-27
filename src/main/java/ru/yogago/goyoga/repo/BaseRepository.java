package ru.yogago.goyoga.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BaseRepository extends JpaRepository<BaseEntity, Long> {
}
