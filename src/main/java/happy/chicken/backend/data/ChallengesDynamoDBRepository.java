package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import happy.chicken.backend.data.model.ChallengeDB;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;

@Repository
public class ChallengesDynamoDBRepository extends DynamoDBRepository implements ChallengesRepository {

    private final DynamoDBMapper mapper;

    public ChallengesDynamoDBRepository(final AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
    }

    @Override
    public void createChallenge(final ChallengeDB challenge) {
        try {
            mapper.save(challenge);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public ChallengeDB getChallengeById(final String id) {
        ChallengeDB challenge = mapper.load(ChallengeDB.class, id);
        if (challenge == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        return challenge;
    }

    @Override
    public void updateChallenge(final ChallengeDB challenge) {
        mapper.save(challenge);
    }

    @Override
    public void deleteChallenge(final String id) {
        var challenge = getChallengeById(id);
        if (challenge == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        mapper.delete(challenge);
    }
}
