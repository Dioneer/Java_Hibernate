package Pegas.dao;

import Pegas.dto.PaymentFilter;
import Pegas.entity.Payment;
import Pegas.entity.User;
import Pegas.util.HibernateUtil;
import com.querydsl.core.Tuple;
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
            assertThat(results).hasSize(6);
            List<String>fullName = results.stream().map(User::getUsername).toList();
            assertThat(fullName).containsExactlyInAnyOrder("ivanov131@gk.ru", "ivanov132@gk.ru","ivanov133@gk.ru",
                    "ivanov134@gk.ru", "ivanov135@gk.ru","ivanov136@gk.ru");
            session.getTransaction().commit();
        }
    }
    @Test
    public void findAllByFirstName() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> results= userDao.findAllByFirstName(session, "Petr");
            assertThat(results).hasSize(1);
            List<String>fullName = results.stream().map(User::getUsername).toList();
            assertThat(fullName).containsExactlyInAnyOrder("ivanov137@gk.ru");
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
    @Test
    public void findLimitedUsersOrderedByBirthday(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> results= userDao.findLimitedUsersOrderedByBirthday(session, 2);
            assertThat(results).hasSize(2);
            session.getTransaction().commit();
        }
    }
    @Test
    public void findAllByCompanyName(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<User> results = userDao.findAllByCompanyName(session, "Google");
            assertThat(results).hasSize(1);
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
    @Test
    public void findAllPaymentsByCompanyName(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Payment> results = userDao.findAllPaymentsByCompanyName(session, "Sber");
            assertThat(results).hasSize(1);
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
    @Test
    public void findAveragePaymentAmountByFirstAndLastName(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Double results = userDao.findAveragePaymentAmountByFirstAndLastName(session, PaymentFilter.builder().firstName("Pavel").build());
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
    @Test
    public void findCompanyNamesWithAvgUserPaymentsOrdersByCompanyName(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Tuple> results = userDao.findCompanyNamesWithAvgUserPaymentsOrdersByCompanyName(session, "Yandex");
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
    @Test
    public void isItPossible(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Tuple> results = userDao.isItPossible(session);
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
}
