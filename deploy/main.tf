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
