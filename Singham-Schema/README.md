## Synopsis

At this time, the primary goal is to do some coding for fun.

## Code Example

Coming soon...

## Motivation

For fun -)

## Installation

$ mvn compile package  -Dtitan.backend=dynamodb -Denvironment=integration

#### Modify Schema

Using the aws console create a specific user to manage our application using scripts
1. This user should have been attached to the AmazonDynamoDBFullAccess

$aws configure
AWS Access Key ID [None]: <Enter access key here>
AWS Secret Access Key [None]: <Enter secret key here>
Default region name [None]: <Enter your preferred region here>  
Default output format [None]:

$ mvn exec:java -Dexec.mainClass=org.geekolator.singham.apps.DefineSchema

## API Reference

Coming soon...

## Tests

Coming soon...

## Contributors

Coming soon...

## License

Coming soon...

This readme created from this template https://gist.github.com/jxson/1784669#file-readme-md
