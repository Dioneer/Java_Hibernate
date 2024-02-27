package Pegas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of ="nameCompany") //получается круг из-за set
@ToString(exclude = "users")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, name = "nameCompany")
    private String nameCompany;
    @Builder.Default
    // mappedBy - поле в классе user, а joincolumn - столбец в таблице на который мапимся
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.setCompany(this);
    }
}
