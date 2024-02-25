package Pegas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of ="nameCompany")
@ToString(exclude = "users")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, name = "nameCompany")
    private String nameCompany;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.setCompany(this);
    }
}
