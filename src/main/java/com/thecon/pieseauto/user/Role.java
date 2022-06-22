package com.thecon.pieseauto.user;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;

    @Column()
    private String roleName;

    public Integer getId() {
        return idRole;
    }

    public void setId(Integer id) {
        this.idRole = id;
    }

    public String getName() {
        return roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }
}
