# About the project

This is a sample project that explain the usage of Kafka as a message broker.
It's a multimodule maven project with producer service and consumer service are designed as separate modules

# How to run the project

* Make sure you have docker engine (or Docker desktop) installed and running
* Run the docker-compose.yml from the project root to create and start kafka broker and kafka ui as docker containers
  *  **`docker compose up -d`**
* cd to each consumer-service and producer-service and run the spring boot apps in separate terminals
  *  **`mvn spring-boot:run`**
* Producer is sending a message in every 3 seconds to the kafka broker which supposed to be consumed by the consumer
* Kafka ui is available in : http://localhost:8090/
