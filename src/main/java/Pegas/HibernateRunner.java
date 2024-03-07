package Pegas;

import Pegas.converter.BirthdayConvert;
import Pegas.dao.CompanyRepository;
import Pegas.dao.PaymentRepository;
import Pegas.dao.UserDao;
import Pegas.dao.UserRepository;
import Pegas.dto.UserCreateDTO;
import Pegas.entity.*;
import Pegas.mapper.CompanyReadMapper;
import Pegas.mapper.UserCreateMapper;
import Pegas.mapper.UserReadMapper;
import Pegas.service.UserService;
import Pegas.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        final UserDao userDao = UserDao.getINSTANCE();
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(),args1)))) {
                session.beginTransaction();
//            PaymentRepository paymentRepository = new PaymentRepository(session);
//            Payment payment = Payment.builder()
//                    .amount(new BigDecimal("105000"))
//                    .build();
//            Payment payment1 = paymentRepository.save(payment);
//            System.out.println(payment1);
//            paymentRepository.findBuId(2L).ifPresent(System.out::println);
                CompanyRepository companyRepository = new CompanyRepository(session);
                Company company = Company.builder()
                        .nameCompany("NewDay")
                        .build();
                companyRepository.save(company);
                var companyReadMapper = new CompanyReadMapper();
                var userReadMapper = new UserReadMapper(companyReadMapper);
                var userRepository = new UserRepository(session);
                UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);
                UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);
//            userService.findUserId(1L).ifPresent(System.out::println);
                UserCreateDTO userCreateDTO = new UserCreateDTO(
                        PersonalInfo.builder()
                                .firstname("Trust")
                                .lastname("Popkov")
                                .birthday(new Birthday(LocalDate.now()))
                                .build(),
                        "ass12362n@ads.ru",
                        Role.Admin, 1L
                );
                userService.create(userCreateDTO);
                session.getTransaction().commit();
            }

            try (Session session1 = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(),args1)))) {
                session1.beginTransaction();
                List<User> results= userDao.findAll(session1);
                var user = session1.find(User.class, 1L);
                var company = user.getCompany();
                System.out.println(user +" "+company);
//                var results2 = session1.find(User.class, 1L);
//                var userGraph = session1.createEntityGraph(User.class);
//                userGraph.addAttributeNodes("company", "userChats");
//                var userChatSubgraph = userGraph.addSubgraph("userChats", UserChat.class);
//                userChatSubgraph.addAttributeNodes("chat");
//                Map<String,Object> prop = Map.of(GraphSemantic.LOAD.getJakartaHintName(),userGraph);
//                var user = session1.find(User.class, 1L, prop);
//                System.out.println(results2);
                session1.getTransaction().commit();

            }

        }
    }
}
