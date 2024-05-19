package il.testtask.clearsolutions.service;

import il.testtask.clearsolutions.data.dto.User;
import il.testtask.clearsolutions.data.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public List<User> findByBirthDateBetweenDates(Date fromDate, Date toDate) {
        return userRepository.findByBirthDateBetweenDates(fromDate, toDate);
    }
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
