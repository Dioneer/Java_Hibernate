package Pegas.dao;

import Pegas.entity.Payment;
import jakarta.persistence.EntityManager;

public class PaymentRepository extends BaseRepository<Long, Payment>{
    public PaymentRepository(EntityManager session) {
        super(session, Payment.class);
    }
}
