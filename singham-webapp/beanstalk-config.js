module.exports = {

  region: 'us-west-2',
  description: 'Web interface to singham graph ecosystem',

  solutionStack: '64bit Amazon Linux 2015.09 v2.0.7 running Node.js',
  //CNAMEPrefix: 'singhamwebapp',

  version: '0.0.0', // optional, else will pull version from package.json
  tier: 'WebServer', // optional, else will use 'WebServer'
  environmentSettings: [
    {
      Namespace: 'aws:autoscaling:launchconfiguration',
      OptionName: 'IamInstanceProfile',
      Value: 'aws-elasticbeanstalk-ec2-role'
    },
    {
      Namespace: 'aws:autoscaling:launchconfiguration',
      OptionName: 'InstanceType',
      Value: 't1.micro'
    },
    {
      Namespace: 'aws:elasticbeanstalk:environment',
      OptionName: 'ServiceRole',
      Value: 'aws-elasticbeanstalk-service-role'
    }
  ],

  bucketConfig: { // optional - passed into S3.createBucket()
    //Bucket: 'singhamWebapp'
  },

}
