package il.testtask.clearsolutions.data.jpa;

import il.testtask.clearsolutions.data.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.birthDate BETWEEN :from AND :to")
    List<User> findByBirthDateBetweenDates(Date from, Date to);
}
