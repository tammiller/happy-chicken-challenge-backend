package happy.chicken.backend.data;

import happy.chicken.backend.model.User;
import happy.chicken.backend.data.model.Voyager;
import org.springframework.stereotype.Repository;

@Repository
public class VoyagerDynamoDBRepository extends DynamoDBRepository implements VoyagerRepository {

    @Override
    public User saveCustomer(User user) {
        Voyager voyager = new Voyager();
        voyager.setId(user.getId().toString());
        voyager.setName(user.getName());
        dbMapper.save(voyager);
        return user;
    }
}
