package happy.chicken.backend.data;

import happy.chicken.backend.data.model.ChallengeDB;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;

@Repository
public class ChallengesDynamoDBRepository extends DynamoDBRepository implements ChallengesRepository {

    @Override
    public void createChallenge(final ChallengeDB challenge) {
        try {
            dbMapper.save(challenge);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public ChallengeDB getChallengeById(final String id) {
        ChallengeDB challenge = dbMapper.load(ChallengeDB.class, id);
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
    public void deleteChallenge(final String id) {
        var challenge = getChallengeById(id);
        if (challenge == null) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        dbMapper.delete(challenge);
    }
}
