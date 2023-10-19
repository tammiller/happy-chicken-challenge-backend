package happy.chicken.backend.data;

import happy.chicken.backend.data.model.UserDB;
import happy.chicken.backend.model.User;

public interface UserRepository {

    UserDB saveUser(User user);

    UserDB getUserById(String userId);

    UserDB getUserByEmailId(String emailId);
}
