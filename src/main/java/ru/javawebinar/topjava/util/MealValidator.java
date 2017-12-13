package ru.javawebinar.topjava.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;

@Component
public class MealValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Meal.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Meal meal=(Meal)o;
        if("".equals(String.valueOf(meal.getCalories())) || meal.getCalories()==0){
            errors.rejectValue("calories","calories.empty","Calories empty");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "calories", "Calories no be empty");
    }
}
