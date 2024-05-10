package il.testtask.clearsolutions.data.jpa;

import il.testtask.clearsolutions.data.dto.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Storage {
    Optional<User> save(User user);
    List<User> findByBirthDateBetween(Date fromDate, Date toDate);
    Optional<User> findById(Long userId);
    boolean deleteById(Long userId);
}
