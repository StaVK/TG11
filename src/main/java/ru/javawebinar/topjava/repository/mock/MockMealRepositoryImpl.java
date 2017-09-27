package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MockMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MockMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal,1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(userId);
        return meal != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.values().stream()
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }
//
//    private Stream<Meal> getAllAsStream(int userId) {
//        Map<Integer, Meal> meals = repository.get(userId);
//        return meals == null ?
//                Stream.empty() : meals.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed());
//    }
}
