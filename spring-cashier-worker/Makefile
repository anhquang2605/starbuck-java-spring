
all: clean

clean: 
	mvn clean

docker-clean:
	docker stop rabbitmq
	docker rm rabbitmq

network-create:
	docker network create --driver bridge starbucks

network-inspect:
	docker network inspect starbucks

network-ls:
	docker network ls

rabbit-shell:
	docker exec -it rabbitmq bash 

rabbit:
	docker run --name rabbitmq \
	           --network starbucks \
			   -p 9091:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management
console:
	open http://localhost:9091

# Management Console: http://localhost:9091
# username and password of guest / guest:

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/spring-cashier-worker-1.0.jar

# Docker

docker-build: build
	docker build -t spring-cashier-worker .
	docker images

docker-run: docker-build
	docker run --network starbucks --name spring-cashier-worker -td -p 80:8080 spring-cashier-worker

docker-shell:
	docker exec -it spring-cashier-worker bash

docker-push:
	docker login
	docker build -t $(account)/spring-cashier-worker .
	docker push $(account)/spring-cashier-worker



