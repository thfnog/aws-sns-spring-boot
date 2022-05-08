#!/bin/bash

aws --endpoint-url=http://localhost:4566 sns publish --topic-arn arn:aws:sns:us-east-2:000000000000:topic-subscriber --message 'Corpo da mensagem2' --subject 'Teste2 de mensagem'