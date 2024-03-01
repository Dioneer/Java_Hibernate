package Pegas.dao;

import Pegas.entity.Company;
import jakarta.persistence.EntityManager;

public class CompanyRepository extends BaseRepository<Long, Company>{
    public CompanyRepository(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}
