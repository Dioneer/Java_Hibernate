package Pegas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "users")
@Table(name = "payment", schema = "public")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Payment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Version
    private Long version;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "payment", orphanRemoval = true)
    List<User> users = new ArrayList<>();

    public void addUser(User user){
        users.add(user);
        user.setPayment(this);
    }
}
