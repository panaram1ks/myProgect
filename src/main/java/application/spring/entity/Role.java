package application.spring.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "customer_roles")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__( @NonNull))
public class Role extends PersistentEntity implements Serializable {
    private static final long serialVersionUID = 2L;
    @NonNull
    @Column(nullable = false, unique = true)
    private String name;
}