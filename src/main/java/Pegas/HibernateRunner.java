package Pegas;

import Pegas.entity.*;
import Pegas.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
//        Company company = Company.builder()
//                .nameCompany("Alphabeting")
//                .build();
//        User user = (User.builder()
//                .username("ivan109@mail.ru")
//                .personalInfo(PersonalInfo.builder().firstname("Ivan").lastname("Boykoo")
//                        .birthday(new Birthday(LocalDate.of(2000, 1, 1)))
//                        .build())
//                .role(Role.Admin)
//                .company(company)
//                .build());
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
//                session.persist(company);
//                session.persist(user);
//                User user1 = session.get(User.class,10);
//                System.out.println(user1.getCompany().getId());
//                session.delete(user1);
//                session.remove(company);
//                session.clear();
//                session.delete(user);
//                session.persist(user);
//                Company company1 = session.get(Company.class,18);
//                user.setCompany(company1);
//                session.saveOrUpdate(user);
                /**
                 * test positions
                 */
//            session.save(user);
//            session.update(user);
//            session.saveOrUpdate(user);
//            session.delete(user);
//            User user1 = session.get(User.class, "ivan1@mail.ru");
//            user1.getPersonalInfo().setFirstname("Pavel");
//                session.flush();
//                User user2 = session.get(User.class, "Ivan");
//                User user3 = session.get(User.class, "ivan101@mail.ru");
//                System.out.println("User2"+user2);
//                System.out.println("User3"+user3);

                session.getTransaction().commit();
            }
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
//                session1.delete(user);
                session1.getTransaction().commit();
            }
        } catch (Exception e) {
            log.error("User exception occured: {}", e);
        }
    }
}
