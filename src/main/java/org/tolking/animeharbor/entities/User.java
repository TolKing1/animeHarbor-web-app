package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.tolking.animeharbor.entities.enums.Provider;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users", schema = "public")
public class User extends TransactionEntity{

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
    private Image image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_m2m_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "anime_id"}
            )
    )
    private List<Anime> watchList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Rating> rating = new ArrayList<>();

    @Transient
    public void addRole(Roles role) {
        roles.add(role);
    }
}
