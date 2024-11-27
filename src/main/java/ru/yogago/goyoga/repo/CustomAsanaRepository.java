package ru.yogago.goyoga.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomAsanaRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomAsanaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<AsanaEntity> findEntitiesByCustomSql(String customSql) {
        Query query = entityManager.createNativeQuery(customSql, AsanaEntity.class);
        return query.getResultList();
    }
}
