package happy.chicken.backend.data.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import happy.chicken.backend.config.LocalDateConverter;
import happy.chicken.backend.model.Challenge;
import happy.chicken.backend.model.DailyEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamoDBTable(tableName = "user_challenge")
public class ChallengeDB {

  @DynamoDBHashKey(attributeName = "challenge_id")
  private String challengeId;

  @DynamoDBRangeKey(attributeName = "user_id")
  private String userId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @DynamoDBTypeConverted(converter = LocalDateConverter.class)
  private LocalDate start;

  @DynamoDBAttribute(attributeName = "number_of_days")
  private Integer numberOfDays;

  @DynamoDBAttribute
  private String status;

  @DynamoDBAttribute(attributeName = "daily_entries")
  private List<DailyEntryDB> dailyEntries;

  public static ChallengeDB fromChallenge(final Challenge challenge) {
    var dailyEntries = challenge.getDailyEntries()
            .stream()
            .map(dailyEntry -> DailyEntryDB.builder()
                    .date(dailyEntry.getDate())
                    .status(dailyEntry.getStatus().getValue())
                    .id(dailyEntry.getId())
                    .build())
            .toList();
    return ChallengeDB.builder()
            .challengeId(challenge.getChallengeId().toString())
            .start(challenge.getStart())
            .userId(challenge.getUserId().toString())
            .numberOfDays(challenge.getNumberOfDays())
            .status(challenge.getStatus().getValue())
            .dailyEntries(dailyEntries)
            .build();
  }

  public static Challenge toChallenge(final ChallengeDB challengeDB) {
    var dailyEntries = challengeDB.getDailyEntries()
            .stream()
            .map(dailyEntryDB -> DailyEntry.builder()
                    .status(DailyEntry.StatusEnum.fromValue(dailyEntryDB.getStatus()))
                    .id(dailyEntryDB.getId())
                    .date(dailyEntryDB.getDate())
                    .build())
            .toList();
    return Challenge.builder()
            .challengeId(UUID.fromString(challengeDB.getChallengeId()))
            .userId(UUID.fromString(challengeDB.getUserId()))
            .start(challengeDB.getStart())
            .numberOfDays(challengeDB.getNumberOfDays())
            .status(Challenge.StatusEnum.fromValue(challengeDB.getStatus()))
            .dailyEntries(dailyEntries)
            .build();
  }

  public ChallengeDB addDailyEntriesItem(final DailyEntry dailyEntriesItem) {
    if (this.dailyEntries == null) {
      this.dailyEntries = new ArrayList<>();
    }
    var entry = DailyEntryDB.builder()
            .date(dailyEntriesItem.getDate())
            .status(dailyEntriesItem.getStatus().getValue())
            .id(dailyEntriesItem.getId())
            .build();
    this.dailyEntries.add(entry);
    return this;
  }

}

