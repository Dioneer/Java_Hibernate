package Pegas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of ="nameCompany") //получается круг из-за set
@ToString(exclude = "users")
@Entity
@Audited
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "company", schema = "public")
public class Company implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, name = "nameCompany")
    private String nameCompany;
    @Version
    private Long version;
    @Builder.Default
    // mappedBy - поле в классе user, а joincolumn - столбец в таблице на который мапимся
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "company", orphanRemoval = true)
    @NotAudited
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.setCompany(this);
    }
}
