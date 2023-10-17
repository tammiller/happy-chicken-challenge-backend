package happy.chicken.backend.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "voyager")
public class Voyager {

    @DynamoDBHashKey(attributeName = "voyager_id")
    private String id;

    @DynamoDBAttribute
    private String name;
}
