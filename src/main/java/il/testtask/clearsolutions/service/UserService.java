package il.testtask.clearsolutions.service;

import il.testtask.clearsolutions.data.dto.User;
import il.testtask.clearsolutions.data.jpa.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private Storage storage;

    public void save(User user) {
        storage.save(user);
    }
    public List<User> findByBirthDateBetween(Date fromDate, Date toDate) {
        return storage.findByBirthDateBetween(fromDate, toDate);
    }
    public Optional<User> findById(Long userId) {
        return storage.findById(userId);
    }
    public void deleteById(Long userId) {
        storage.deleteById(userId);
    }
}
