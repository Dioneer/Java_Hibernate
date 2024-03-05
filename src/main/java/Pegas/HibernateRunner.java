package Pegas;

import Pegas.converter.BirthdayConvert;
import Pegas.dao.CompanyRepository;
import Pegas.dao.PaymentRepository;
import Pegas.dao.UserDao;
import Pegas.dao.UserRepository;
import Pegas.dto.UserCreateDTO;
import Pegas.entity.*;
import Pegas.mapper.UserCreateMapper;
import Pegas.mapper.UserReadMapper;
import Pegas.service.UserService;
import Pegas.util.HibernateUtil;
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

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConvert(), true);
        final UserDao userDao = UserDao.getINSTANCE();
        User results =null;
        try ( SessionFactory sessionFactory = configuration.buildSessionFactory();) {
            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(),args1)));
            session.beginTransaction();
//            PaymentRepository paymentRepository = new PaymentRepository(session);
//            Payment payment = Payment.builder()
//                    .amount(new BigDecimal("105000"))
//                    .build();
//            Payment payment1 = paymentRepository.save(payment);
//            System.out.println(payment1);
//            paymentRepository.findBuId(2L).ifPresent(System.out::println);

            var userReadMapper = new UserReadMapper();
            var userRepository = new UserRepository(session);
            CompanyRepository companyRepository = new CompanyRepository(session);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);
            UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);
//            userService.findUserId(1L).ifPresent(System.out::println);
            UserCreateDTO userCreateDTO = new UserCreateDTO(
                    PersonalInfo.builder()
                            .firstname("Trust")
                            .lastname("Popkov")
                            .birthday(new Birthday(LocalDate.now()))
                            .build(),
                    "ass12347n@ads.ru",
                    Role.Admin,1L
            );
            userService.create(userCreateDTO);


            session.getTransaction().commit();

        }
//        try (Session session = sessionFactory.openSession()) {
//            assert session != null;
//            session.beginTransaction();
//            var results2 = session.find(User.class, 1L);
//            var userGraph = session.createEntityGraph(User.class);
//            userGraph.addAttributeNodes("company", "userChats");
//            var userChatSubgraph = userGraph.addSubgraph("userChats", UserChat.class);
//            userChatSubgraph.addAttributeNodes("chat");
//            Map<String,Object> prop = Map.of(GraphSemantic.LOAD.getJakartaHintName(),userGraph);
//            User user = session.find(User.class, prop);
//            System.out.println(results2);
//            session.getTransaction().commit();

//        }
    }
}
