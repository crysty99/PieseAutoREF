package com.thecon.pieseauto.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    public Long countByIdUser(Integer id);
    public User getUserByNameAndEmail(String name, String email);
    public Long deleteUserByIdUser(Integer id);
    @Query("SELECT u FROM User u WHERE u.name = :username")
    public User getUserByUsername(@Param("username") String username);

}
