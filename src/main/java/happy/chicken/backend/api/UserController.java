package happy.chicken.backend.api;

import happy.chicken.backend.model.User;
import happy.chicken.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController implements UsersApi{

    private final UserService userService;

    @Override
    public ResponseEntity<Void> createUser(User user){
        userService.createUserAccount(user);
        return ResponseEntity.ok().build();
    }
}
