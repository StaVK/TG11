package ru.javawebinar.topjava.service.datajpa;

import ru.javawebinar.topjava.service.MealServiceTest;
import org.springframework.test.context.ActiveProfiles;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
}
