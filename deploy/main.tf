provider "aws" {
  region = "eu-central-1"
}

locals {
  account_id = "045222016985"
  user_names = ["bai", "ems", "manasa", "salwa", "tamara", "amber"]
}

terraform {
  backend "s3" {
    bucket = "running-state-happy-chicken"
    key = "global/s3/terraform.tfstate"
    region = "eu-central-1"

    dynamodb_table = "running-state-happy-chicken-lock"
    encrypt = true
  }
}

variable "account_id" {
  description = "ID of the root account"
  type = string
  default = "045222016985"
}

resource "aws_dynamodb_table" "dynamodb_table_user_challenge" {
  name        = "user_challenge"
  read_capacity  = 10
  write_capacity = 10
  hash_key       = "user_id"
  range_key      = "challenge_id"
  attribute {
    name = "user_id"
    type = "S"
  }
  attribute {
    name = "challenge_id"
    type = "S"
  }

  tags = {
    environment       = "dev"
  }
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "running-state-happy-chicken"
  lifecycle {
    prevent_destroy = false
  }
}

resource "aws_s3_bucket_versioning" "enabled" {
  bucket = aws_s3_bucket.terraform_state.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "default" {
  bucket = aws_s3_bucket.terraform_state.id
  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

resource "aws_s3_bucket_public_access_block" "public_access" {
  bucket = aws_s3_bucket.terraform_state.id
  block_public_acls = true
  block_public_policy = true
  ignore_public_acls = true
  restrict_public_buckets = true
}

resource "aws_dynamodb_table" "terraform_locks" {
  name = "running-state-happy-chicken-lock"
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "LockID"
  attribute {
    name = "LockID"
    type = "S"
  }
}

resource "aws_iam_user" "iam_users" {
  for_each = toset(local.user_names)
  name = each.value
}

resource "aws_iam_policy" "iam_policy_admin" {
  name        = "ChickenAdmin"
  description = "An example policy allowing all actions on all resources"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = "*",
        Resource = "*",
      },
    ],
  })
}

resource "aws_iam_user_policy_attachment" "iam_user_policy_attachment" {
  for_each = toset(local.user_names)
  user       = each.value
  policy_arn = aws_iam_policy.iam_policy_admin.arn
}

resource "aws_iam_user_login_profile" "iam_user_login_profile" {
  for_each = toset(local.user_names)
  user    = each.value
  password_reset_required = false
}

resource "aws_ecr_repository" "ecr_repository" {
  name = "happy-chicken-challenge-backend"
  image_tag_mutability = "MUTABLE"
}

# deployment
resource "aws_key_pair" "key_pair" {
  key_name   = "key-pair"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCUWdH4/r2R3B2WJ97W+mJnUcjJ8rpKhAccXcrk3NajCnnCrU2Gx6Mmpokaw3DN95MIV0VFTaBcACYwnw0tMOsO8kfs1WO1ZQcmR80hlMW8Tvv6LSH9cUOWl+E8LFapoRBY9jQO49NyPYnezpWTX0P5ywuQbSKKgkMoBiLZdkTdjKDwx98ILSxq1YxvZJ4Q2zRed68PuHryD9Cmp81KxJu58jPD8yV4wVgKPdo9aA62hkU2UeiYWCE9+9HqOb6rVr23loGuqCbH64u52HDdR4Ta8/AXOtABQOXsFKMbAj9O6CsqUQx087bHTB7r8aTOi+YCotb3Q/nJH24Im923jfxsKqX9Pomso5UcMq8O9w76NkWkfg4rboxohm9lEvzMXu2Y/fXI/U7GFeyXP0W34EbFFK6ZmjwAGTlCA3z6ir3GKUVhGIKpAhIU0EqX//6F80oQJ4WRZPchWGa9zjLaco5vR65VfVXF0+l6CmclnnScpgWCcIx6O98bCh/gPILFYfM="
}

#data "aws_ami" "amazon_linux_2" {
#  most_recent = true
#  filter {
#    name   = "name"
#    values = ["amzn2-ami-hvm-*"]
#  }
#  filter {
#    name   = "owner-alias"
#    values = ["amazon"]
#  }
#  filter {
#    name   = "state"
#    values = ["available"]
#  }
#  owners = ["amazon"]
#}
#
#resource "aws_instance" "ec2_instance" {
#    ami           = data.aws_ami.amazon_linux_2.id
#    instance_type = "t2.micro"
#    key_name      = aws_key_pair.key_pair.key_name
#
#  user_data = <<-EOF
#    #!/bin/bash
#    sudo yum update -y
#    sudo amazon-linux-extras install docker -y
#    sudo service docker start
#    sudo usermod -aG docker ec2-user
#    sudo docker login -u AWS -p $(aws ecr get-login-password --region eu-central-1) ${local.account_id}.dkr.ecr.eu-central-1.amazonaws.com
#    sudo docker pull ${local.account_id}.dkr.ecr.eu-central-1.amazonaws.com/${aws_ecr_repository.ecr_repository.name}:latest
#    sudo docker run -d -p 80:8080 ${local.account_id}.dkr.ecr.eu-central-1.amazonaws.com/${aws_ecr_repository.ecr_repository.name}:latest
#  EOF
#}
#
#resource "aws_security_group" "instance_sg" {
#  name        = "instance_sg"
#  description = "Security group for the EC2 instance"
#
#  ingress {
#    from_port   = 80
#    to_port     = 80
#    protocol    = "tcp"
#    cidr_blocks = ["0.0.0.0/0"] # Allow incoming HTTP traffic from anywhere
#  }
#}



