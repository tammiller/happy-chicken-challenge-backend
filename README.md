# happy-chicken-challenge-backend
Backend repository for our WWC Hackathon Project

# commands
To run: mvn spring-boot:run
To run openAPIgen: mvn clean compile

To publish docker image to AWS ECR: First get temporary credentials and then execute Jib Plugin.
AWS should be configured in the computer with a user with the necessary permissions

aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 045222016985.dkr.ecr.eu-central-1.amazonaws.com 
mvn compile jib:build