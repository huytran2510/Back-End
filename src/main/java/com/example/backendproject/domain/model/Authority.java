package com.example.backendproject.domain.model;

import com.example.backendproject.domain.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = -6520888182797362903L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ERole authority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Authority(ERole authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
