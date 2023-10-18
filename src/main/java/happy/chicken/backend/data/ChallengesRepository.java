package happy.chicken.backend.data;

import happy.chicken.backend.model.Challenge;

public interface ChallengesRepository {

    Challenge saveChallenge(Challenge challenge);

}
