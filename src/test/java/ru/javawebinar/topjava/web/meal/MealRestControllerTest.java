package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(MEALS, mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void TestDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void createWithLocation() throws Exception {
        //User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        Meal expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void getBetween() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 8, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 31, 11, 0);
        mockMvc.perform(get(REST_URL + "/filter")
                .param("startDate", startDateTime.toLocalDate().toString())
                .param("startTime", startDateTime.toLocalTime().toString())
                .param("endDate", endDateTime.toLocalDate().toString())
                .param("endTime", endDateTime.toLocalTime().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                        MealsUtil.createWithExceed(MEAL4, true),
                        MealsUtil.createWithExceed(MEAL1, false)));


    }

    @Test
    public void getBetweenEmptyParam() throws Exception {
        mockMvc.perform(get(REST_URL + "/filter")
                .param("startDate", "")
                .param("endTime", ""))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), USER.getCaloriesPerDay())));


    }

}