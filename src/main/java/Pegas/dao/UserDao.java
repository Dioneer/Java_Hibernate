package Pegas.dao;

import Pegas.entity.Birthday;
import Pegas.entity.QCompany;
import Pegas.entity.QUser;
import Pegas.entity.User;
import Pegas.util.HibernateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static Pegas.entity.QUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {
//    public List<User> findAll(Session session){
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//        Root<User> user = criteria.from(User.class);
//        criteria.select(user);
//        return  session.createQuery(criteria).list();
//    }
        public List<User> findAll(Session session){
            return new JPAQuery<User>(session).select(user).from(user).fetch();
    }
//    public List<User> findAllByFirstName(Session session, String firstName){
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//        Root<User> user = criteria.from(User.class);
//        criteria.select(user).where(cb.equal(user.get("personalInfo").get("firstName"), firstName));
//        return Collections.emptyList();
//    }
        public List<User> findAllByFirstName(Session session, String firstName){
            return new JPAQuery<User>(session).select(user).from(user)
                   .where(user.personalInfo.firstname.eq(firstName)).fetch();
        }
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit){
        return new JPAQuery<User>(session).select(user).from(user)
                .orderBy(new OrderSpecifier(Order.ASC, user.personalInfo.birthday))
                .limit(limit).fetch();
    }
    public List<User> findAllByCompanyName(Session session, String company){
        return new JPAQuery<User>(session).select(user).from(QCompany.company)
                .join(QCompany.company.users, user)
                .where(QCompany.company.nameCompany.eq(company))
                .fetch();
    }
    public List<User> findAllPaymentsByCompanyName(Session session, String company){
        return new JPAQuery<User>(session).select(user).from(QCompany.company)
                .join(QCompany.company.users, user)
                .where(QCompany.company.nameCompany.eq(company))
                .fetch();
    }
//    public List<User> findAveragePaymentAmountByFirstAndLastName(Session session, String firstname, String lastname){
//        return new JPAQuery<User>(session)
//                .select(payment.amount.avg())
//                .from(payment)
//                .join(payment.receiver(), user)
//                .where(user.personalInfo.firstname.eq(firstname) .and(user.personalInfo.lastname.eq(lastname)))
//                .fetchOne();
//    }
//    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrdersByCompanyName(Session session, String company){
//        return new JPAQuery<Tuple>(session)
//                .select(QCompany.company.nameCompany, payment.amount.avg())
//                .from(QCompany.company)
//                .join(QCompany.company.users, user)
//                .join(user.payment, payment)
//                .groupBy(QCompany.company.nameCompany)
//                .orderBy(QCompany.company.nameCompany.asc())
//                .fetch();
//        }
//    public List<Tuple> isItPossible(Session session){
//            return new JPAQuery<>(session).select(user, payment.amount.avg())
//                    .from(user)
//                    .join(user.payments, payment)
//                    .fetch();
//}

    private volatile static UserDao INSTANCE;

    public static UserDao getINSTANCE() {
        if(INSTANCE==null){
            synchronized (UserDao.class){
                if(INSTANCE==null){
                    INSTANCE=new UserDao();
                }
            }
        }
        return INSTANCE;
    }
}
