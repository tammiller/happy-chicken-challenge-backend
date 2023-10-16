package happy.chicken.backend.api;

import happy.chicken.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController implements UsersApi{

    @Override
    public ResponseEntity<Void> createUser(User user){
        log.info("saving user {}", user);
        return null;
    }
}
