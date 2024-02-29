package Pegas.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {
    private final Collection<Predicate> predicateList = new ArrayList<>();
    public static QPredicate builder(){
        return new QPredicate();
    }
    public <T> QPredicate add(T object, Function<T, Predicate> function){
        if(object != null){
            predicateList.add(function.apply(object));
        }
        return this;
    }
    public Predicate buildAnd(){
        return ExpressionUtils.allOf(predicateList);
    }
    public Predicate buildOr(){
        return ExpressionUtils.anyOf(predicateList);
    }
}
