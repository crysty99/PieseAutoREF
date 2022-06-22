package com.thecon.pieseauto;

import com.thecon.pieseauto.user.Role;
import com.thecon.pieseauto.user.User;
import com.thecon.pieseauto.user.UserRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNewUser(){
        User user = new User();
        Role role = new Role();
        user.setName("user");
        user.setPassword("user");
        user.setEmail("useraaa@gmail.com");
        user.setDateOfBirth(new Date(99,0,1));
        user.setPhoneNumber("0720000000");
        user.setAddress("address test");
        //role.setName("test");
        //user.setProfileImage("/poza.png");
        user.setEnabled(true);

        User savedUser= repo.save(user);

        Assertions.assertNotNull(savedUser);
    }
    @Test
    public void testAddImage() throws IOException {
        int idUser = 3;
        Optional<User> optionalUser = repo.findById(idUser);
        User user = optionalUser.get();

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("poze/poza2.png");

        if(inputStream == null) {
            fail("Unable to get resources");
        }

        user.setProfileImage(IOUtils.toByteArray(inputStream));
        repo.save(user);

        User updatedUser= repo.findById(idUser).get();
        Assertions.assertNotNull(updatedUser.getProfileImage());
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertNotNull(users);

        for (User user : users){
            System.out.println(users);
        }
    }

    @Test
    public void testUpdate(){
        int idUser = 2;
        Optional<User> optionalUser = repo.findById(idUser);
        User user = optionalUser.get();
        user.setPhoneNumber("000000");
        repo.save(user);

        User updatedUser= repo.findById(idUser).get();
        Assertions.assertEquals(updatedUser.getPhoneNumber(),"000000");
    }

    @Test
    public void testGet(){
        int idUser = 1;
        Optional<User> optionalUser = repo.findById(idUser);
        Assertions.assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    //@Test
    public void testDelete(){
        int idUser = 1;
        repo.deleteById(idUser);

        Optional<User> optionalUser = repo.findById(idUser);
        Assertions.assertFalse(optionalUser.isPresent());
    }
}
