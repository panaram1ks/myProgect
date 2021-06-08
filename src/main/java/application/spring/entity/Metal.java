package application.spring.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "metal")
public class Metal extends PersistentEntity implements Serializable {
    private static final long serialVersionUID = 4L;
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @Column(nullable = false)
    private LocalDate arrivalTime;
    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String color;

//    @OneToMany
//    private Collection<Product> products = new ArrayList<>();
}