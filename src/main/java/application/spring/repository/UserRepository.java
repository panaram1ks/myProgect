package application.spring.repository;

import application.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional // for to obtain error org.postgresql.util.PSQLException:
// Большие объекты не могут использоваться в режиме авто-подтверждения (auto-commit)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    User getOne(Integer id);
}