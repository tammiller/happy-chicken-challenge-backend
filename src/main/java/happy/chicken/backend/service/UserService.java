package happy.chicken.backend.service;

import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserLoginRequest;
import happy.chicken.backend.model.UserSignUpRequest;

public interface UserService {
    User createUserAccount(UserSignUpRequest signUpRequest);

    Challenge loginUser(UserLoginRequest loginRequest);

    /**
     * Fetch user info based on internal user id
     * @param userId - UUID
     * @return - user info
     */
    User getUserInfo(String userId);
}
