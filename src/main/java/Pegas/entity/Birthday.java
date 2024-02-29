package Pegas.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Birthday(LocalDate birthday) implements Serializable {
    public long getAge(){
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
