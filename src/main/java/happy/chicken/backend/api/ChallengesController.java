package happy.chicken.backend.api;

import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.DailyEntry;
import happy.chicken.backend.service.ChallengesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ChallengesController implements ChallengesApi {
    ChallengesService service;

    @Override
    public ResponseEntity<DailyEntry> addDailyEntryForChallenge(String challengeId, DailyEntry dailyEntry) {
        service.addDailyEntry(challengeId, dailyEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Challenge> createChallenge(final Challenge challenge) {
        var result = service.createChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Void> deleteChallenge(final String userId, final String challengeId) {
        service.deleteChallenge(challengeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteDailyEntry(final String challengeId, final String entryId) {
        service.deleteDailyEntry(challengeId, entryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Challenge> getChallenge(final String userId, final String challengeId) {
        var challenge = service.getChallenge(challengeId);
        return ResponseEntity.ok(challenge);
    }

    @Override
    public ResponseEntity<List<DailyEntry>> getChallengeEntriesForUser(String challengeId) {
        List<DailyEntry> entries = service.getChallengeEntriesForUser(challengeId);
        return ResponseEntity.ok(entries);
    }

    @Override
    public ResponseEntity<List<Challenge>> getChallengesForUser(final String userId) {
        List<Challenge> challenges = service.getChallenges(userId);
        return ResponseEntity.ok(challenges);
    }

    @Override
    public ResponseEntity<Challenge> updateChallenge(final String userId, final String challengeId, final Challenge challenge) {
        var result = service.updateChallenge(challenge);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<DailyEntry> updateDailyEntry(final String challengeId, final String entryId, final DailyEntry dailyEntry) {
        service.updateDailyEntry(challengeId, entryId, dailyEntry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
