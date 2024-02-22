package Pegas;

import Pegas.converter.BirthdayConvert;
import Pegas.entity.Birthday;
import Pegas.entity.Role;
import Pegas.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConvert(), true);
//        configuration.addAnnotatedClass(User.class);
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = (User.builder()
                    .username("ivan1@mail.ru")
                    .firstname("Ivan")
                    .lastname("Boykoo")
                    .birthday(new Birthday(LocalDate.of(2000, 1, 1)))
                    .role(Role.Admin)
                    .build());
            session.save(user);
            session.update(user);
//            session.saveOrUpdate(user);
//            session.delete(user);
            User user1 = session.get(User.class, "ivan1@mail.ru");
            System.out.println(user1);
            session.getTransaction().commit();
        }
    }
}
