package com.thecon.pieseauto.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    public User getUserByNameAndEmail(String name, String email);
    public List<User> getUsersByRoles(String role);

    //TODO change id to roles
    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.enabled =:status WHERE u.id_user != 1", nativeQuery = true)
    public void updateStatusForAllUsers(@Param("status") boolean status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_roles WHERE id_user =:id", nativeQuery = true)
    public void deleteUserRoleByIdUser(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE id_user =:id", nativeQuery = true)
    public void deleteUserByIdUser(@Param("id") int id);

}
