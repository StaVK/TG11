package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }


//    public void createOrUpdate(@RequestParam("id") Integer id,
//                               @RequestParam("name") String name,
//                               @RequestParam("email") String email,
//                               @RequestParam("enabled") boolean enabled,
//                               @RequestParam("password") String password) {
    @PostMapping("/{id}")
    public void createOrUpdate(@PathVariable("id") int id) {
        // Пока так :))
        User user = super.get(id);
        user.setEnabled(!user.isEnabled());
        if (user.isNew()) {
            super.create(user);
        }
        else {
            super.update(user,id);
        }
    }
}
