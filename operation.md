## 1. Start Apache Kafka broker
### Step 1: Start Zookeeper
`~/kafka_2.13-3.0.0/bin/zookeeper-server-start.sh ~/kafka_2.13-3.0.0/config/zookeeper.properties`
### Step 2: Start Apache Kafka Server
`~/kafka_2.13-3.0.0/bin/kafka-server-start.sh ~/kafka_2.13-3.0.0/config/server.properties`

## 2. Run service 
### Order service
#### Build project: 
`cd ~/mongo-springboot-kafka-demo/order-serv`

`mvn clean install`
#### Run application:
`mvn spring-boot:run`

### Payment service
#### Build project:
`cd ~/mongo-springboot-kafka-demo/payment-serv`

`mvn clean install`
#### Run application:
`mvn spring-boot:run`

### Stock service
#### Build project:
`cd ~/mongo-springboot-kafka-demo/stock-serv`

`mvn clean install`
#### Run application:
`mvn spring-boot:run`




note:
List topic in broker

./bin/kafka-topics.sh --bootstrap-server=localhost:9092 --list

View message in topic

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orders --from-beginning
    