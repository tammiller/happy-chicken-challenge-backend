package happy.chicken.backend.service;

import happy.chicken.backend.data.ChallengesDynamoDBRepository;
import happy.chicken.backend.data.model.ChallengeDB;
import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.DailyEntry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ChallengesService {
    ChallengesDynamoDBRepository repository;

    public Challenge createChallenge(final Challenge challenge) {
        challenge.setChallengeId(UUID.randomUUID());
        repository.createChallenge(ChallengeDB.fromChallenge(challenge));
        return challenge;
    }

    public Challenge getChallenge(final String userId, final String challengeId) {
        return ChallengeDB.toChallenge(repository.getChallengeById(challengeId, userId));
    }

    public List<DailyEntry> getChallengeEntriesForUser(final String userId, final String challengeId) {
        return getChallenge(userId, challengeId).getDailyEntries();
    }

    public void addDailyEntry(final String userId, final String challengeId, final DailyEntry dailyEntry) {
        ChallengeDB challenge = repository.getChallengeById(challengeId, userId);
        dailyEntry.setId(UUID.randomUUID());
        challenge.addDailyEntriesItem(dailyEntry);
        repository.updateChallenge(challenge);
    }

    public void updateDailyEntry(final String userId, final String challengeId, final String entryId, final DailyEntry dailyEntry) {
        ChallengeDB challenge = repository.getChallengeById(challengeId, userId);
        challenge.getDailyEntries().removeIf(savedEntry -> entryId.equals(savedEntry.getId()));
        challenge.addDailyEntriesItem(dailyEntry);
        repository.updateChallenge(challenge);
    }

    public void deleteChallenge(final String userId, final String challengeId) {
        repository.deleteChallenge(userId, challengeId);
    }

    public void deleteDailyEntry(final String userId, final String challengeId, final String entryId) {
        ChallengeDB challenge = repository.getChallengeById(challengeId, userId);
        challenge.getDailyEntries().removeIf(savedEntry -> entryId.equals(savedEntry.getId()));
        repository.updateChallenge(challenge);
    }

    public Challenge updateChallenge(final String userId, final Challenge challenge) {
        repository.updateChallenge(ChallengeDB.fromChallenge(challenge));
        return challenge;
    }

    public List<Challenge> getChallenges(final String userId) {
        List<ChallengeDB> challenges = repository.getChallengesByUserId(userId);
        return challenges.stream().map(ChallengeDB::toChallenge).collect(Collectors.toList());
    }
}
