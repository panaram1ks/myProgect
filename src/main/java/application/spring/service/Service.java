package application.spring.service;

import application.spring.entity.PersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Service<I, E extends PersistentEntity, R extends JpaRepository<E, I>> {

    R getRepository();

    default E getById(I id) {
        return getRepository().getOne(id);
    }

    default E add(E entity) {
        return getRepository().save(entity);
    }

    default List<E> getAll() {
        return getRepository().findAll();
    }

    default boolean delete(I id) {
        if (getRepository().existsById(id)) {
            getRepository().deleteById(id);
            return true;
        }
        return false;
    }

    default boolean delete(E entity) {
        if (getRepository().existsById((I) entity.getId())) {
            getRepository().delete(entity);
            return true;
        }
        return false;
    }
}