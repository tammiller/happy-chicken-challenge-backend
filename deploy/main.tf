provider "aws" {
  region = "eu-central-1"
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

resource "aws_dynamodb_table" "challenge_table" {
  name        = "challenge-table"
  read_capacity  = 10
  write_capacity = 10
  hash_key       = "id"
  attribute {
    name = "id"
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
