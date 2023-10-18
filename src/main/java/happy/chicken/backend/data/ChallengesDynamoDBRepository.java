package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import happy.chicken.backend.model.Challenge;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class ChallengesDynamoDBRepository extends DynamoDBRepository implements ChallengesRepository{


    @Override
    public Challenge saveChallenge(Challenge challenge) {
        //We may need to update our model for challenge
        return null;
    }
}
