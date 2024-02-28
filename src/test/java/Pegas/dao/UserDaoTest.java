package Pegas.dao;

import Pegas.entity.User;
import Pegas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class UserDaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
   private final UserDao userDao = UserDao.getINSTANCE();
//   @BeforeAll
//   public void initDb(){
//       TestDataImporter.importData(sessionFactory);
//   }
//    @AfterAll
//    public void finish(){
//        sessionFactory.close();
//    }
    @Test
        public void findAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> results= userDao.findAll(session);
            assertThat(results).hasSize(5);
            List<String>fullName = results.stream().map(User::getUsername).toList();
            assertThat(fullName).containsExactlyInAnyOrder("ivanov127@gk.ru", "ivanov128@gk.ru","ivanov129@gk.ru", "ivanov130@gk.ru", "ivanov131@gk.ru");
            session.getTransaction().commit();
        }
    }
    @Test
    public void findAllByFirstName() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> results= userDao.findAllByFirstName(session, "Pavel");
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
}
