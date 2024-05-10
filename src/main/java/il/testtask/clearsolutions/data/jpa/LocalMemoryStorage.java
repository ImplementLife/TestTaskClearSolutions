package il.testtask.clearsolutions.data.jpa;

import il.testtask.clearsolutions.data.dto.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LocalMemoryStorage implements Storage {
    private final HashMap<Long, User> mapById = new HashMap<>();
    private final TreeMap<Date, User> mapByBirthDate = new TreeMap<>();

    public Optional<User> save(User user) {
        Long id = user.getId();
        if (id == null || id <= 0) {
            id = (long) mapById.size() + 1;
            user.setId(id);
        }
        mapById.put(id, user);
        mapByBirthDate.put(user.getBirthDate(), user);
        return Optional.of(user);
    }

    public List<User> findByBirthDateBetween(Date fromDate, Date toDate) {
        List<User> users = new ArrayList<>();
        Map<Date, User> subMap = mapByBirthDate.subMap(fromDate, true, toDate, true);
        for (Map.Entry<Date, User> entry : subMap.entrySet()) {
            users.add(entry.getValue());
        }
        return users;
    }

    public Optional<User> findById(Long userId) {
        User user = mapById.get(userId);
        return Optional.ofNullable(user);
    }

    public boolean deleteById(Long userId) {
        User remove = mapById.remove(userId);
        mapByBirthDate.remove(remove.getBirthDate());
        return true;
    }
}
