package ru.yogago.goyoga.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yogago.goyoga.data.ClientEntity;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByUsername(String name);

    @Modifying
    @Transactional
    @Query("UPDATE ClientEntity c SET c.displayName = :displayName WHERE c.username = :username")
    int updateClientData(@Param("username") String username, @Param("displayName") String displayName);
}
