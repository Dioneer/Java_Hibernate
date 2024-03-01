package Pegas.entity;

import Pegas.converter.BirthdayConvert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;
    @Convert(converter = BirthdayConvert.class)
//    @Column(name = "birthday") in User mapping of this field
    @NotNull
    private Birthday birthday;

}
