package com.thecon.pieseauto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository repo;

    public ArrayList<User> listAll() {
        return (ArrayList<User>) repo.findAll();
    }
    public void save(User user) {
        repo.save(user);
    }

    public User get(int id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("No user with id " + id);
    }

    public void delete(int id) throws UserNotFoundException {
        Long count = repo.countByIdUser(id);
        if(count == null || count == 0){
            throw new UserNotFoundException("No user with id " + id);
        }
        repo.deleteById(id);
    }

}
