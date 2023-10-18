package happy.chicken.backend.data;


import happy.chicken.backend.data.model.ChallengeDB;

public interface ChallengesRepository {

    void createChallenge(ChallengeDB challenge);

    ChallengeDB getChallengeById(String id);

    void updateChallenge(ChallengeDB challenge);

    void deleteChallenge(String id);
}
