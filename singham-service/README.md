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

## Installation

$ aws s3 mb s3://singham-service-0.0.1-snapshot --region us-west-2

mvn compile war:war -Dtitan.backend=dynamodb -Denvironment=integration

$  mvn beanstalk:upload-source-bundle beanstalk:create-application-version beanstalk:create-environment -Dbeanstalk.environmentName=intg

#### Test

$  curl http://singham-service-intg/us-west-2/elasticbeanstalk.com/apps/99

output
[99]

## API Reference

Coming soon...

## Tests

Coming soon...

## Contributors

Coming soon...

## License

Coming soon...

This readme created from this template https://gist.github.com/jxson/1784669#file-readme-md
