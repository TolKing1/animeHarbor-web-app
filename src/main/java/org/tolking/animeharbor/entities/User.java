package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tolking.animeharbor.entities.enums.Provider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, updatable = false)
    @NotBlank(message = "Username: Can't be blank")
    @Size(min = 3, max = 30, message = "Username: Length should be between 3 and 30")
    private String username;

    @Column(unique = true,updatable = false)
    @Email(message = "Email: Enter valid email", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider = Provider.LOCAL;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'default.png'")
    private String picture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_m2m_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Anime> watchList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rating> rating = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    public void addRole(Roles role) {
        roles.add(role);
    }
}
