package Pegas.entity;

import Pegas.converter.BirthdayConvert;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile"})
@Builder
@Entity
@EqualsAndHashCode(of = "username")
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @AttributeOverride(name = "birthday", column = @Column(name ="birthday"))
    @Embedded
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_chat", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats = new ArrayList<>();

    public void addChat(Chat chat){
        chats.add(chat);
        chat.getUsers().add(this);
    }
}
