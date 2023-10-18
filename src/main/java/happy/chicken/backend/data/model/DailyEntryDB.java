package happy.chicken.backend.data.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import happy.chicken.backend.config.LocalDateConverter;
import happy.chicken.backend.model.DailyEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@DynamoDBDocument
public class DailyEntryDB {
    private UUID id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    private LocalDate date;

    private String status;
}
