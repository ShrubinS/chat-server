# chat-server

## Included demo client and server code

Hosted the code on OpenNebula, and ran tests, but faced an issue where the client server's response did not reach the chat server after the first 3 requests.

### Ran the below on server to instantiate server:
```git pull```
```mvn clean install -DproxySet=true -DproxyHost=tsproxy.scss.tcd.ie -DproxyPort=8080```
```/usr/java/jdk-9.0.1/bin/java -jar target/chat_server_sockets-1.0-SNAPSHOT-jar-with-dependencies.jar 8888```
