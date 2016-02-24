## Synopsis

At this time, the primary goal is to do some coding for fun.

The idea is to use use a graph database to capture relationships between some of the key concepts in technology-business strategy viz.,

- Business opportunities
- Roadmaps
- Capability heatmaps
- Key business drivers

## Code Example

Coming soon...

## Motivation

For fun -)

## Pre-Installation

$  aws iam create-policy --policy-name credential-access-titandb --policy-document file://credential-access-titandb.policy

$  aws iam attach-role-policy --policy-arn arn:aws:iam::YOUR-ACCOUNT-NUMBER:policy/credential-access-titandb --role-name  aws-elasticbeanstalk-ec2-role

## Installation

$  aws s3 mb s3://singham-service-0.0.1-snapshot --region us-west-2

$  mvn compile war:war -Dtitan.backend=dynamodb

$  mvn beanstalk:upload-source-bundle beanstalk:create-application-version beanstalk:create-environment -Dbeanstalk.environmentName=intg -Dsingham.environemnt=integration

#### Test

$  curl http://singham-service-intg/us-west-2/elasticbeanstalk.com/meta

output

{"environment":"integration","titan.backend":"dynamodb"}

## API Reference

Coming soon...

## Tests

Coming soon...

## Contributors

Coming soon...

## License

Coming soon...

This readme created from this template https://gist.github.com/jxson/1784669#file-readme-md
