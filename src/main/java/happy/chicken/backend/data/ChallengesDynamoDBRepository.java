package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ChallengesDynamoDBRepository {
    private DynamoDBMapper dbMapper;


}
