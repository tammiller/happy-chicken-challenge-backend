package happy.chicken.backend.data;

import happy.chicken.backend.model.User;
import happy.chicken.backend.data.model.UserDB;
import org.springframework.stereotype.Repository;

@Repository
public class UserDynamoDBRepository extends DynamoDBRepository implements UserRepository {

    @Override
    public User saveCustomer(User user) {
        UserDB voyager = new UserDB();
        voyager.setId(user.getId().toString());
        voyager.setName(user.getName());
        dbMapper.save(voyager);
        return user;
    }
}
