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
  
# Ngrok
  * Creating account via url:
    > https://dashboard.ngrok.com/get-started/setup
  * Run below command to create a proxy to your localhost port:
---
    $ ngrok http 9090
