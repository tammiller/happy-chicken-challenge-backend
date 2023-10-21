package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import happy.chicken.backend.data.model.ChallengeDB;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ChallengesDynamoDBRepository extends DynamoDBRepository implements ChallengesRepository {

    @Override
    public void createChallenge(final ChallengeDB challenge) {
        dbMapper.save(challenge);
    }

    @Override
    public ChallengeDB getChallengeById(final String id, String userId) {
        ChallengeDB challenge = dbMapper.load(ChallengeDB.class, id, userId);
        if (challenge == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        return challenge;
    }

    @Override
    public void updateChallenge(final ChallengeDB challenge) {
        dbMapper.save(challenge);
    }

    @Override
    public void deleteChallenge(final String userId, final String id) {
        var challenge = getChallengeById(id, userId);
        if (challenge == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        dbMapper.delete(challenge);
    }

    public List<ChallengeDB> getChallengesByUserId(String userId) {
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable("user_challenge");
        Index index = table.getIndex("user-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("user_id = :user_id")
                .withValueMap(new ValueMap()
                        .withString(":user_id",userId));
        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();

        List<ChallengeDB> userChallenges = new ArrayList<>();
        if (iter.hasNext()) {
            Item item = iter.next();
            String challengeId = item.get("challenge_id").toString();
            ChallengeDB challengeDB = getChallengeById(challengeId, userId);
            userChallenges.add(challengeDB);
        }

        return userChallenges;
    }
}
