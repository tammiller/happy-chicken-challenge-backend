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
4. Create the necessary table through aws cli. Example:
   `aws dynamodb create-table \
   --table-name voyager \
   --attribute-definitions AttributeName=voyager_id,AttributeType=S \
   --key-schema AttributeName=voyager_id,KeyType=HASH \
   --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
   --endpoint-url http://localhost:8000`
5. Run the app. You should be able to interact with the endpoint with the data saved in your local database.

# Publish docker image to ECR

To publish docker image to AWS ECR: First get temporary credentials and then execute Jib Plugin.
AWS should be configured in the computer with a user with the necessary permissions

aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 045222016985.dkr.ecr.eu-central-1.amazonaws.com 
mvn compile jib:build

