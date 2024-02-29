package Pegas.dao;

import Pegas.dto.PaymentFilter;
import Pegas.entity.*;
import Pegas.util.HibernateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static Pegas.entity.QCompany.*;
import static Pegas.entity.QPayment.payment;
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
    public List<User> findAllByCompanyName(Session session, String companyName){
        return new JPAQuery<User>(session).select(user).from(company)
                .join(company.users, user)
                .where(company.nameCompany.eq(companyName))
                .fetch();
    }
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName){
        return new JPAQuery<Payment>(session).select(payment).from(payment)
                .join(payment.users, user)
                .join(user.company, company)
                .where(company.nameCompany.eq(companyName))
                .orderBy(user.personalInfo.firstname.asc(), payment.amount.asc())
                .fetch();
    }
    public Double findAveragePaymentAmountByFirstAndLastName(Session session, PaymentFilter filter){
            List<BooleanExpression> predicates = new ArrayList<>();
            if(filter.getFirstName() != null) predicates.add((user.personalInfo.firstname.eq(filter.getFirstName())));
        if(filter.getLastName() != null) predicates.add(user.personalInfo.lastname.eq(filter.getLastName()));
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.personalInfo.firstname::eq)
                .add(filter.getLastName(), user.personalInfo.lastname::eq)
                .buildAnd();
        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.users, user)
                .where(predicate)
                .fetchOne();
    }
    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrdersByCompanyName(Session session, String companyName){
        return new JPAQuery<Tuple>(session)
                .select(company.nameCompany, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payment, payment)
                .groupBy(company.nameCompany)
                .orderBy(company.nameCompany.asc())
                .fetch();
        }
    public List<Tuple> isItPossible(Session session){
            return new JPAQuery<Tuple>(session).select(user, payment.amount.avg())
                    .from(user)
                    .join(user.payment, payment)
                    .groupBy(user.id)
                    .having(payment.amount.avg().gt(new JPAQuery<Double>(session).select(payment.amount.avg()).from(payment)))
                    .orderBy(user.personalInfo.firstname.asc())
                    .fetch();
}

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
