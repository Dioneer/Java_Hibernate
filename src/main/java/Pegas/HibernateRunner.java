package Pegas;

import Pegas.dao.UserDao;
import Pegas.entity.User;
import Pegas.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class HibernateRunner {
    public static void main(String[] args) {
        final UserDao userDao = UserDao.getINSTANCE();
        try (Session session = HibernateUtil.buildSession()) {
            assert session != null;
            session.beginTransaction();
            List<User> results= userDao.findAll(session);
            System.out.println(results);
            session.getTransaction().commit();
        }
    }
}
