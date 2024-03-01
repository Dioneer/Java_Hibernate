package Pegas.dao;

import Pegas.entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends BaseRepository<Long, User>{
    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
