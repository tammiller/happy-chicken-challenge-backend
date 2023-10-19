package happy.chicken.backend.service;

import happy.chicken.backend.model.User;
import happy.chicken.backend.model.UserSignInRequest;

public interface UserService {
    User createUserAccount(UserSignInRequest userSignIn);

    User signInUser(UserSignInRequest userSignIn);

    /**
     * Fetch user info based on internal user id
     * @param userId - UUID
     * @return - user info
     */
    User getUserInfo(String userId);
}
