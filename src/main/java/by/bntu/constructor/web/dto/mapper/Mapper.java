package by.bntu.constructor.web.dto.mapper;

public interface Mapper<E, D> {

    E fromDTO(D d);

    D toDTO(E e);
}
