# api-ui-taf-base

##Description

###Run test app
1) install docker
2) run rabbitMq docker run -d --hostname rabbitmq --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3-management
3) check rabbitMq (open http://localhost:15672/, login = guest, pass= guest)
4) run microservices from microservices.zip. you can run via command line (nvm -i nvm -start) or via idea
5) run wireMock (http://wiremock.org/docs/running-standalone/)(I use java -jar wiremock-standalone-2.27.2.jar --por9999 --verbose)
6) send Post request to wireMock (url= http://localhost:9999/__admin/mappings) (body = { "request" : { "urlPattern" : "/.*", "method" : "ANY"   },"response" : {"status" : 200}})
7) add test Queues: event:publish:test.d, event:aftermiddleware:test.d
8) bind test Queues Exchanges: event:publish:test.d -> event:publish, event:aftermiddleware:test.d -> event:aftermiddleware (key = all)
