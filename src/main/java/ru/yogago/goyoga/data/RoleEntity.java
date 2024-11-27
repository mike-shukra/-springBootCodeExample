package ru.yogago.goyoga.data;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_role")
public class RoleEntity implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Schema(readOnly = true)
    private Long id;
    @Schema(example = "USER")
    private String name;

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
