package happy.chicken.backend.data;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import happy.chicken.backend.model.User;
import happy.chicken.backend.data.model.UserDB;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class UserDynamoDBRepository extends DynamoDBRepository implements UserRepository {

    @Override
    public UserDB saveUser(User user, String password) {
        UserDB userDB = new UserDB();
        userDB.setId(user.getId().toString());
        userDB.setName(user.getName());
        userDB.setEmail(user.getEmailId());
        userDB.setPassword(password);
        dbMapper.save(userDB);
        return userDB;
    }

    @Override
    public UserDB getUserById(String userId) {
        return dbMapper.load(UserDB.class, userId);
    }

    @Override
    public UserDB getUserByEmailId(String emailId) {
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable("user");
        Index index = table.getIndex("email-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("email = :email_id")
                .withValueMap(new ValueMap()
                        .withString(":email_id",emailId));
        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();

        if (iter.hasNext()) {
            Item item = iter.next();
            return toUserDB(item);
        }

        return null;
    }

    private UserDB toUserDB(Item item){
        UserDB userDB = new UserDB();
        userDB.setId((String) item.get("user_id"));
        userDB.setName((String) item.get("name"));
        userDB.setEmail((String) item.get("email"));

        return userDB;
    }
}
