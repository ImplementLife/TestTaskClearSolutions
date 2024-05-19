package il.testtask.clearsolutions.controller;

import il.testtask.clearsolutions.data.dto.User;
import il.testtask.clearsolutions.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    @Autowired
    private UserService userService;

    @Value("${user.min.age}")
    private int minAge;

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody User user) {
        log.info("create");
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
        log.info("update");
        User existingUser = userService.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setId(existingUser.getId());
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long userId) {
        log.info("delete");
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> get(
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate
    ) {
        log.info("get(from: {} to: {})", fromDate, toDate);
        if (fromDate == null || toDate == null) {
            return ResponseEntity.ok(userService.findAll());
        }
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("'From' date must be before 'To' date");
        }
        return ResponseEntity.ok(userService.findByBirthDateBetweenDates(fromDate, toDate));
    }
}
