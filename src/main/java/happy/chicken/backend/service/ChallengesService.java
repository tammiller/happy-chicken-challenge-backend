package happy.chicken.backend.service;

import happy.chicken.backend.data.ChallengesDynamoDBRepository;
import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.DailyEntry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChallengesService {
    ChallengesDynamoDBRepository repository;

    public void createChallenge(final Challenge challenge) {

    }

    public Challenge getChallenge(final Integer challengeId) {
        return null;
    }

    public List<DailyEntry> getChallengeEntriesForUser(final Integer challengeId) {
        return null;
    }

    public void addDailyEntry(final Integer challengeId, final DailyEntry dailyEntry) {

    }

    public void updateDailyEntry(final Integer challengeId, final Integer entryId, final DailyEntry dailyEntry) {

    }
}
