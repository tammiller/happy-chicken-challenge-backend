package happy.chicken.backend.service;

import happy.chicken.backend.data.ChallengesDynamoDBRepository;
import happy.chicken.backend.data.model.ChallengeDB;
import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.DailyEntry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ChallengesService {
    ChallengesDynamoDBRepository repository;

    public Challenge createChallenge(final Challenge challenge) {
        challenge.setChallengeId(UUID.randomUUID());
        repository.createChallenge(ChallengeDB.fromChallenge(challenge));
        return challenge;
    }

    public Challenge getChallenge(final String challengeId) {
        return ChallengeDB.toChallenge(repository.getChallengeById(challengeId));
    }

    public List<DailyEntry> getChallengeEntriesForUser(final String challengeId) {
        return getChallenge(challengeId).getDailyEntries();
    }

    public void addDailyEntry(final String challengeId, final DailyEntry dailyEntry) {
        ChallengeDB challenge = repository.getChallengeById(challengeId);
        dailyEntry.setId(UUID.randomUUID());
        challenge.addDailyEntriesItem(dailyEntry);
        repository.updateChallenge(challenge);
    }

    public void updateDailyEntry(final String challengeId, final String entryId, final DailyEntry dailyEntry) {
        ChallengeDB challenge = repository.getChallengeById(challengeId);
        challenge.getDailyEntries().removeIf(savedEntry -> entryId.equals(savedEntry.getId()));
        challenge.addDailyEntriesItem(dailyEntry);
        repository.updateChallenge(challenge);
    }

    public void deleteChallenge(final String challengeId) {
        repository.deleteChallenge(challengeId);
    }

    public void deleteDailyEntry(final String challengeId, final String entryId) {
        ChallengeDB challenge = repository.getChallengeById(challengeId);
        challenge.getDailyEntries().removeIf(savedEntry -> entryId.equals(savedEntry.getId()));
        repository.updateChallenge(challenge);
    }

    public Challenge updateChallenge(final Challenge challenge) {
        repository.updateChallenge(ChallengeDB.fromChallenge(challenge));
        return challenge;
    }

    public List<Challenge> getChallenges(final String userId) {
        var challenges = repository.getChallenges(userId);
        return ChallengeDB.toChallenge(repository.getChallengeById(challengeId));
    }
}
