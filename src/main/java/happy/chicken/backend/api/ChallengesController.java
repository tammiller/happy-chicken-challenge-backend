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
    public ResponseEntity<Void> addDailyEntryForChallenge(final String challengeId, final DailyEntry dailyEntry) {
        service.addDailyEntry(challengeId, dailyEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> createChallenge(final Challenge challenge) {
        service.createChallenge(challenge);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteChallenge(String challengeId) {
        service.deleteChallenge(challengeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteDailyEntry(String challengeId, String entryId) {
        service.deleteDailyEntry(challengeId, entryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Challenge> getChallenge(final String challengeId) {
        var challenge = service.getChallenge(challengeId);
        return ResponseEntity.ok(challenge);
    }


    @Override
    public ResponseEntity<List<DailyEntry>> getChallengeEntriesForUser(final String challengeId) {
        List<DailyEntry> entries = service.getChallengeEntriesForUser(challengeId);
        return ResponseEntity.ok(entries);

    }

    @Override
    public ResponseEntity<Void> updateDailyEntry(final String challengeId, final String entryId, final DailyEntry dailyEntry) {
        service.updateDailyEntry(challengeId, entryId, dailyEntry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
