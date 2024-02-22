package Pegas;

import Pegas.entity.Birthday;
import Pegas.entity.Role;
import Pegas.entity.User;
import Pegas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


public class HibernateRunner {
    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) {
        User user = (User.builder()
                    .username("ivan100@mail.ru")
                    .firstname("Ivan")
                    .lastname("Boykoo")
                    .birthday(new Birthday(LocalDate.of(2000, 1, 1)))
                    .role(Role.Admin)
                    .build());
        log.info("User object in transient state: {}", user);
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()){
            try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
//            session.save(user);
//            session.update(user);
            session.saveOrUpdate(user);
//            session.delete(user);
            User user1 = session.get(User.class, "ivan1@mail.ru");
            user1.setFirstname("Pavel");
            session.flush();
            User user2 = session.get(User.class, "ivan10@mail.ru");
            User user3 = session.get(User.class, "ivan100@mail.ru");

            session.getTransaction().commit();
            }
            try(Session session1 = sessionFactory.openSession()){
                session1.beginTransaction();
//                session1.delete(user);
                session1.getTransaction().commit();
            }
        }
    }
}
