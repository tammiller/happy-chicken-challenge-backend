package happy.chicken.backend.service;

import happy.chicken.backend.data.UserRepository;
import happy.chicken.backend.data.model.UserDB;
import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserSignInRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User createUserAccount(UserSignInRequest userSignIn) {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, userSignIn.getName(), userSignIn.getEmailId());
        userRepo.saveUser(user);
        return user;
    }

    @Override
    public User signInUser(UserSignInRequest userSignIn) {
        UserDB userDB = userRepo.getUserByEmailId(userSignIn.getEmailId());

        if (userDB == null) {
            return createUserAccount(userSignIn);
        }

        return toUser(userDB);
    }

    @Override
    public User getUserInfo(String userId) {
        UserDB userDB = userRepo.getUserById(userId);

        if (userDB == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

        return toUser(userDB);
    }

    private User toUser(UserDB userDB){
        return new User(UUID.fromString(userDB.getId()), userDB.getName(), userDB.getEmail());
    }
}
