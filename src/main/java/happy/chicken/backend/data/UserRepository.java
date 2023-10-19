package happy.chicken.backend.data;

import happy.chicken.backend.model.User;

public interface UserRepository {

    User saveCustomer(User user);
}
