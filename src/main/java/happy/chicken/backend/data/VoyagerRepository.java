package happy.chicken.backend.data;

import happy.chicken.backend.model.User;

public interface VoyagerRepository {

    User saveCustomer(User user);
}
