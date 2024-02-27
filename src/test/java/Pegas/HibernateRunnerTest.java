package Pegas;

import Pegas.entity.*;
import Pegas.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class HibernateRunnerTest {
//    this test doesn't work because of change
    @Test
    public void testUnderIngine() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("ivanov125@gk.ru")
//                .personalInfo(PersonalInfo.builder().firstname("Ivan").lastname("Ivanov")
//                        .birthday(new Birthday(LocalDate.of(2011,1,21))).build())
//                .role(Role.User)
                .build();
        var sql = """
                insert into %s (%s) values(%s)
                """;
        String userClass = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(i->i.schema()+"."+i.name()).orElse(user.getClass().getName());
        Field[] fields = user.getClass().getDeclaredFields();
        String userAttr = Arrays.stream(fields).map(i->Optional.ofNullable(i.getAnnotation(Column.class))
                .map(Column::name)
                .orElse(i.getName())).collect(Collectors.joining(","));
        String question = Arrays.stream(fields).map(i->"?").collect(Collectors.joining(","));
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(userClass, userAttr, question));
        for(int i = 0; i< fields.length; i++){
            fields[i].setAccessible(true);
            preparedStatement.setObject(i+1,fields[i].get(user));
        }
        System.out.println(preparedStatement);
        preparedStatement.execute();
        connection.close();
    }
    @Test
    public void checkHQL(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            var users = session.createQuery("select u from User u").list();
            System.out.println(users);
            session.getTransaction().commit();
        }
    }
    @Test
    public void createNewUser(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
           User user = User.builder()
                   .username("ivanov128@gk.ru")
                   .personalInfo(PersonalInfo.builder().firstname("Ivan").lastname("Ivanov")
                           .birthday(new Birthday(LocalDate.of(2011,1,21))).build())
                   .role(Role.User)
                   .build();
           session.persist(user);
           session.getTransaction().commit();
        }
    }
    @Test
    public void updateUser(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, 19);
            PersonalInfo personalInfo = user.getPersonalInfo();
            personalInfo.setFirstname("Kirra");
//            session.update(user);
            User u = session.merge(user);
            session.getTransaction().commit();
        }
    }
    @Test
    public void deleteUser(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, 13);
            session.remove(user);
            session.getTransaction().commit();
        }
    }
    @Test
    public void getOneToMany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = session.get(Company.class, 9);
            System.out.println(company.getUsers());
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkManyToOne(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = session.get(Company.class, 9);
            User user = User.builder()
                    .username("tech03@mail.ru")
                    .company(company)
                    .build();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkOneToMany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Company company = Company.builder()
                    .nameCompany("VTB")
                    .build();
            User user = User.builder()
                    .username("tech04@mail.ru")
                    .company(company)
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
            Company company = session.get(Company.class, 9);
            company.getUsers().removeIf(i->i.getId().equals(Long.parseLong(String.valueOf(28))));
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkOneToOne(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = User.builder()
                    .username("ivan1028@mail.ru")
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
    public void checkRoundManyToMany(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, 26);
            Chat chat = session.get(Chat.class, 1);
            UserChat userChat = UserChat.builder()
                    .chat(chat)
                    .user(user)
                    .build();
            userChat.setCreteAt(Instant.now());
            userChat.setCreateBy("Maxim");
            userChat.setChat(chat);
            userChat.setUser(user);
            session.persist(userChat);
            session.getTransaction().commit();
        }
    }
    @Test
    public void checkH2(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, 3);
            System.out.println(user);
            session.getTransaction().commit();
        }
    }
//    @Test
//    public void checkInheritance(){
//        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//            Session session = sessionFactory.openSession()){
//            session.beginTransaction();
//            Company company = Company.builder()
//                    .nameCompany("Yandex")
//                    .build();
//            Programmer programmer =Programmer.builder()
//                    .username("jj@asd.ru")
//                    .language(Language.JAVA)
//                    .company(company)
//                    .build();
//            Manager manager = Manager.builder()
//                    .username("jjj@asd.ru")
//                    .project("BigBoss")
//                    .company(company)
//                    .build();
//            session.save(company);
//            session.save(programmer);
//            session.save(manager);
//            session.flush();
//            session.clear();
//
//            Programmer programmer1 = session.get(Programmer.class, 1L);
//            System.out.println(programmer1);
//            Manager manager1 = session.get(Manager.class, 2L);
//            session.getTransaction().commit();
//        }
//    }
//    this test commented because functions commented
//    @Test
//    public void checkManyToMany(){
//        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//            Session session = sessionFactory.openSession()){
//            session.beginTransaction();
//            Chat chat = Chat.builder()
//                    .name("pains")
//                    .build();
//            User user = session.get(User.class, 30);
//            user.addChat(chat);
//            session.save(chat);
//            session.getTransaction().commit();
//        }catch (Exception e){
//            log.error("Exception occurred: ", e);
//            throw e;
//        }
//    }
}
