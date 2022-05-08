# aws-sns-spring-boot
> Spring boot application with AWS Simple Notification Service


# refer the documentation for better understanding
https://aws.amazon.com/documentation/sns/
https://docs.aws.amazon.com/sns/latest/dg/SendMessageToHttp.prepare.html


# Prerequisites
  * set up aws sns account
  * install aws-cli on your computer
    > https://docs.aws.amazon.com/cli/latest/userguide/awscli-install-windows.html
  * set access key and secret key
  * create external url with ngrok 
  * create sns topic via src/main/resources/sns/create-topic-sns.sh
  
# Ngrok
  * Creating account via url:
    > https://dashboard.ngrok.com/get-started/setup
  * Run below command to create a proxy to your localhost port:
---
    $ cd src/main/resources/ngrok
    $ ./ngrok http 9090

# FAQ
  * https://gist.github.com/lobster1234/57e803ebca47c3c263a9d53ccd1f1783