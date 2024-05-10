package il.testtask.clearsolutions.controller;

import il.testtask.clearsolutions.data.dto.User;
import il.testtask.clearsolutions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserService userService;

    @Value("${user.min.age}")
    private int minAge;

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -minAge);
        Date minRequireBirthday = calendar.getTime();

        if (!user.getBirthDate().before(minRequireBirthday)) {
            throw new IllegalArgumentException("User must be at least " + minAge + " years old");
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@Validated @RequestBody User user) {
        User existingUser = userService.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setId(existingUser.getId());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> get(
        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate
    ) {
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("'From' date must be before 'To' date");
        }
        System.out.println("searchUsersByBirthDateRange");
        return ResponseEntity.ok(userService.findByBirthDateBetween(fromDate, toDate));
    }
}
