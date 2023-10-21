package happy.chicken.backend.service;

import happy.chicken.backend.data.UserRepository;
import happy.chicken.backend.data.model.UserDB;
import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserLoginRequest;
import happy.chicken.backend.model.UserSignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ChallengesService challengesService;

    @Override
    public User createUserAccount(UserSignUpRequest signUpRequest) {
        UserDB userCheck = userRepo.getUserByEmailId(signUpRequest.getEmailId());

        if (userCheck == null) {
            UUID userId = UUID.randomUUID();
            User user = new User(userId, signUpRequest.getName(), signUpRequest.getEmailId());
            userRepo.saveUser(user, signUpRequest.getPassword());
            log.info("Successfully created account for {}: {}", signUpRequest.getName(), signUpRequest.getEmailId());
            return user;
        } else {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Challenge loginUser(UserLoginRequest loginRequest) {
        UserDB userDB = userRepo.getUserByEmailId(loginRequest.getEmailId());

        if (userDB == null || userDB.getPassword().equals(loginRequest.getPassword())) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }

        List<Challenge> challenges = challengesService.getChallenges(userDB.getId());
        log.info("Successfully logged in user {} with {} challenges", loginRequest.getEmailId(), challenges.size());

        //only one challenge is active at a time
        Optional<Challenge> activeChallenge = challenges.stream().filter(challenge -> challenge.getStatus().equals(Challenge.StatusEnum.ACTIVE)).findFirst();

        return activeChallenge.orElse(null);
    }

    @Override
    public User getUserInfo(String userId) {
        try {
            UserDB userDB = userRepo.getUserById(userId);

            if (userDB == null) {
                throw new ErrorResponseException(HttpStatus.NOT_FOUND);
            }

            return toUser(userDB);
        }  catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getStackTrace().toString());
            return null;
        }
    }

    private User toUser(UserDB userDB){
        return new User(UUID.fromString(userDB.getId()), userDB.getName(), userDB.getEmail());
    }
}
