package com.thecon.pieseauto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository repo;

    public String saveImage(int id, MultipartFile imageFile) throws IOException {
        String folder = "src/main/resources/poze/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + id + "-" + new Date().getTime() + "-" + imageFile.getOriginalFilename());
        Files.write(path,bytes);

        return path.toAbsolutePath().toString();
    }

    public String updateImage(int id, MultipartFile imageFile){
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            try {
                result.get().setProfileImageLocation(saveImage(id,imageFile));
                repo.save(result.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result.get().getProfileImageLocation();
        }
        throw new UserNotFoundException("No user with id " + id);
    }
    public String getImageLocation(int id){
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get().getProfileImageLocation();
        }
        throw new UserNotFoundException("No user with id " + id);
    }

    public List<User> listAll() {
        List<User> result = repo.findAll();
            return result;
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
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            repo.deleteUserRoleByIdUser(id);
            repo.deleteUserByIdUser(id);
        }
        else throw new UserNotFoundException("No user with id " + id);
    }

    public User getUserByNameAndEmail(String name, String email) {
        Optional<User> result = Optional.ofNullable(repo.getUserByNameAndEmail(name, email));
        if(result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("No user with name and email:" + name +" "+email);
    }

    public void updateStatusForAllUsers(boolean status) {
        repo.updateStatusForAllUsers(status);
    }
}
