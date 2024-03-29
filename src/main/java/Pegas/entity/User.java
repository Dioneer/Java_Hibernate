package Pegas.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.List;

//@NamedQuery(name="findByUserNameandCompany", query = "select u from User u\n" +
//        "            join u.company c\n" +
//        "            where u.personalInfo.firstname = :firstname\n" +
//        "            and c.nameCompany = :namecompany")
@NamedEntityGraph(
        name="WithCompanyAndChat",
        attributeNodes = {
                @NamedAttributeNode("company"),
                @NamedAttributeNode(value="userChats", subgraph = "chats")
        },
        subgraphs = {
                @NamedSubgraph(name="chats", attributeNodes = @NamedAttributeNode("chat"))
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile","payment","userChats"})
@Builder
@Entity
@EqualsAndHashCode(of = "username") //получается круг из-за set
@Table(name = "users", schema = "public")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Users")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="type")
public class User implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Embedded
    @AttributeOverride(name = "birthday", column = @Column(name ="birthday"))
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
//    optional меняет left join to inner join
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;
//    при one to one все время будет идти запрос в профайл. Что бы прекратить, надо его тут закомментить и оставить только в профайле
//    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
//    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();
    //    @BatchSize(size=5) для уменьшения числа подзапросов плохая практика

    /**
     * for ManyToMany
     */
//    @Builder.Default
//    @ManyToMany
//    @JoinTable(name = "users_chat", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chat_id"))
//    private List<Chat> chats = new ArrayList<>();
//
//    public void addChat(Chat chat){
//        chats.add(chat);
//        chat.getUsers().add(this);
//    }
}
