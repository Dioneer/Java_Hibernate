package Pegas.util;

import Pegas.converter.BirthdayConvert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConvert(), true);
//        configuration.addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }
}
