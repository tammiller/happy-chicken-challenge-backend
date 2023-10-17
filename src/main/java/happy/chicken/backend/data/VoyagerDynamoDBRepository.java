package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import happy.chicken.backend.model.User;
import happy.chicken.backend.model.Voyager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoyagerDynamoDBRepository implements VoyagerRepository {

    @Autowired
    private DynamoDBMapper dbMapper;

    @Override
    public User saveCustomer(User user) {
        Voyager voyager = new Voyager();
        voyager.setId(user.getId().toString());
        voyager.setName(user.getName());
        dbMapper.save(voyager);
        return user;
    }
}
