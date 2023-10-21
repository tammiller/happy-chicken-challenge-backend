package happy.chicken.backend.data;


import happy.chicken.backend.data.model.ChallengeDB;

import java.util.List;

public interface ChallengesRepository {

    void createChallenge(ChallengeDB challenge);

    ChallengeDB getChallengeById(String id, String userId);

    void updateChallenge(ChallengeDB challenge);

    void deleteChallenge(final String userId, final String id) ;

    List<ChallengeDB> getChallengesByUserId(String userId);
}
