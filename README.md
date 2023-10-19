# happy-chicken-challenge-backend
Backend repository for our WWC Hackathon Project

# commands
To run: mvn spring-boot:run
To run openAPIgen: mvn clean compile


# Running the app locally
Requirement: aws-cli, access-key for your IAM user
1. Download dynamodb local v2.x: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html
2. Add your access key and secret key from step 2 in application.yml under amazon section
3. Run the local dynamo db (instruction in website)
4. Login through awscli and create the necessary table. See AWS commands in next section.
5. Run the app. You should be able to interact with the endpoint with the data saved in your local database.

# Commands for managing dynamodb tables
1. Create user table
   ```
   aws dynamodb create-table \
      --table-name user \
      --attribute-definitions AttributeName=user_id,AttributeType=S \
      --key-schema AttributeName=user_id,KeyType=HASH \
      --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
      --endpoint-url http://localhost:8000
   ```
2. Create challenge table
   ```
   aws dynamodb create-table \
   --table-name user_challenge \
   --attribute-definitions AttributeName=challenge_id,AttributeType=S \
        AttributeName=user_id,AttributeType=S \
   --key-schema AttributeName=challenge_id,KeyType=HASH \
        AttributeName=user_id,KeyType=RANGE \
   --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
   --endpoint-url http://localhost:8000
   ```
3. Add global secondary index to user table so we can query by email
   ```
   aws dynamodb update-table \
    --table-name user \
    --attribute-definitions AttributeName=email,AttributeType=S \
    --global-secondary-index-updates \
        "[{\"Create\":{\"IndexName\": \"email-index\",\"KeySchema\":[{\"AttributeName\":\"email\",\"KeyType\":\"HASH\"}], \
        \"ProvisionedThroughput\": {\"ReadCapacityUnits\": 1, \"WriteCapacityUnits\": 1      },\"Projection\":{\"ProjectionType\":\"ALL\"}}}]" \
   --endpoint-url http://localhost:8000
   ```

# Publish docker image to ECR

To publish docker image to AWS ECR: First get temporary credentials and then execute Jib Plugin.
AWS should be configured in the computer with a user with the necessary permissions

aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 045222016985.dkr.ecr.eu-central-1.amazonaws.com 
mvn compile jib:build

