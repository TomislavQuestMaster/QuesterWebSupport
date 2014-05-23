package net.thequester.websupport.database.repositories;

import net.thequester.websupport.IFileManager;
import net.thequester.websupport.model.QUser;
import net.thequester.websupport.model.User;

import java.util.List;

import static net.thequester.websupport.model.QUser.*;

/**
 * Created by Tomo.
 */
public class UserProvider {

    private UserRepository userRepository;
    private IFileManager fileManager;

    public UserProvider(UserRepository userRepository, IFileManager fileManager) {
        this.userRepository = userRepository;
        this.fileManager = fileManager;
    }

    public User getUser(String username) {
        return userRepository.findOne(user.username.like(username));
    }

    public boolean verifyUser(User user) {

        User fromDb = getUser(user.getUsername());

        if (fromDb == null) {
            return false;
        }

        return fromDb.getPassword().equals(user.getPassword());
    }

    public void registerUser(User user){

        if (getUser(user.getUsername()) != null) {
            return;
        }

        userRepository.save(user);
        fileManager.createUserDirectory(user);
    }
}
