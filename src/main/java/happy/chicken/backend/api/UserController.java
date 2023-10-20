package happy.chicken.backend.api;

import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserSignInRequest;
import happy.chicken.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController implements UsersApi{

    private final UserService userService;

    @Override
    public ResponseEntity<User> getUser(String userId) {
//        User user = userService.getUserInfo(userId);
//        return ResponseEntity.ok(user);
        return ResponseEntity.ok(User.builder()
                        .name("TestUser")
                        .emailId("email@test.com")
                        .id(UUID.randomUUID())
                .build());
    }

    @Override
    public ResponseEntity<User> signInUser(UserSignInRequest userSignInRequest) {
//        User user = userService.signInUser(userSignInRequest);
//        return ResponseEntity.status(201).body(user);
        return ResponseEntity.ok(User.builder()
                .name("TestUser")
                .emailId("email@test.com")
                .id(UUID.randomUUID())
                .build());
    }
}
