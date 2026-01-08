# Students on AWS Lambda

https://aws.amazon.com/de/pm/lambda

Serverless Computing - AWS Lambda  
**Run code without thinking about servers or clusters**

## Students (Quarkus) app modifcation

No code changes!

add to pom.xml

    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-amazon-lambda-rest</artifactId>
    </dependency>

## Build

    mvnw package

New artefacts:

- `target/function.zip`
- `sam.jvm.yaml`
- `sam.native.yaml`

## Deploy

Deploy to AWS via _SAM_

https://docs.aws.amazon.com/serverless-application-model/

For installation follow: https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html

    sam deploy -t target/sam.jvm.yaml -g

Done!

# Use

Check the AWS Lambda deployment links at the dashboard

https://eu-central-1.console.aws.amazon.com/lambda/home?region=eu-central-1

There are _applications_ and _functions_. By using SAM we deploy as application.

Check here the API endpoint:

ServerlessRestApiProdStage

Example:

- https://qff6jewvb1.execute-api.eu-central-1.amazonaws.com/Prod
- https://qff6jewvb1.execute-api.eu-central-1.amazonaws.com/Prod/hello/complex
