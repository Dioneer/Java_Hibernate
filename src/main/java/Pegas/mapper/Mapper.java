package Pegas.mapper;

public interface Mapper <from, to>{
    to mapFrom(from object);
}
