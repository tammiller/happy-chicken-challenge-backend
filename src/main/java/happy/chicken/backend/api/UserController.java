package happy.chicken.backend.api;

import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserLoginRequest;
import happy.chicken.backend.model.UserSignUpRequest;
import happy.chicken.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController implements UsersApi{

    private final UserService userService;

    @Override
    public ResponseEntity<User> getUser(String userId) {
        User user = userService.getUserInfo(userId);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Challenge> loginUser(UserLoginRequest userLoginRequest) {
        Challenge activeChallenge = userService.loginUser(userLoginRequest);
        return ResponseEntity.ok(activeChallenge);
    }

    @Override
    public ResponseEntity<User> signUpUser(UserSignUpRequest userSignUpRequest) {
        User user = userService.createUserAccount(userSignUpRequest);

        if (userSignUpRequest.getPassword().isEmpty()) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(201).body(user);
    }
}
