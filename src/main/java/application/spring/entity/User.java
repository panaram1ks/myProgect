package application.spring.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

import static com.google.common.collect.FluentIterable.from;

@Entity
@Table(name = "customer_users")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User extends PersistentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @NonNull
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @NonNull
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(name = "customer_users_roles", joinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Role> roles;

    public boolean hasRole(String role) {
        return from(roles).anyMatch(r -> role.equals(r.getName()));
    }
}
