package Pegas;

import Pegas.entity.*;
import Pegas.util.HibernateUtil;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class HibernateRunnerTest {
    @Test
    public void checkOneToMany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = session.get(Company.class, 18);
            System.out.println(company.getUsers());
            session.getTransaction().commit();
        }
    }
    @Test
    public void addNewUserAndCompany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = Company.builder()
                    .nameCompany("Whooshes")
                    .build();
            User user = User.builder()
                    .username("ivan1010@mail.ru")
                    .build();
            company.addUser(user);
            session.persist(company);
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkOrphanRemoval(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = session.get(Company.class, 18);
            company.getUsers().removeIf(i->i.getId().equals(10));
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkOneToOne(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = User.builder()
                    .username("ivan1013@mail.ru")
                    .build();

            Profile profile = Profile.builder()
                    .street("RU")
                    .street("Lenina")
                    .build();
            session.save(user);
            profile.setUser(user);
            session.save(profile);
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkManyToMany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Chat chat = Chat.builder()
                    .name("pain")
                    .build();
            User user = session.get(User.class, 7);
            user.addChat(chat);
            session.save(chat);
            session.getTransaction().commit();
        }
    }
}
