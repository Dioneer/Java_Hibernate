package Pegas.entity;

import Pegas.converter.BirthdayConvert;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.List;

//@NamedQuery(name="findByUserNameandCompany", query = "select u from User u\n" +
//        "            join u.company c\n" +
//        "            where u.personalInfo.firstname = :firstname\n" +
//        "            and c.nameCompany = :namecompany")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile"})
@Builder
@Entity
@EqualsAndHashCode(of = "username") //получается круг из-за set
@Table(name = "users", schema = "public")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="type")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @AttributeOverride(name = "birthday", column = @Column(name ="birthday"))
    @Embedded
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
//    optional меняет left join to inner join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

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
