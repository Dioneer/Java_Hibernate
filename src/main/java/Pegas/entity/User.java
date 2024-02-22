package Pegas.entity;

import Pegas.converter.BirthdayConvert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    @Convert(converter = BirthdayConvert.class)
    @Column(name = "birthday")
    private Birthday birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
}
