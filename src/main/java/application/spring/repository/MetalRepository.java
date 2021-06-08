package application.spring.repository;

import application.spring.entity.Metal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public interface MetalRepository extends JpaRepository<Metal, Integer> {

    //Metal findMetal(String name, LocalDate localDate);
}