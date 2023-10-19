package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DynamoDBRepository {

    @Autowired
    protected DynamoDBMapper dbMapper;
}
