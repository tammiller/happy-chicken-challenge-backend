package happy.chicken.backend.data.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "user")
public class UserDB {

    @DynamoDBHashKey(attributeName = "user_id")
    private String id;

    @DynamoDBAttribute
    private String name;
}
