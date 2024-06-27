package uz.pdp.animalshop.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BaseService<T,I> {
    List<T> findAll();
    Optional<T> findById(I id);
    T save(T t);
    void delete(I i);
}