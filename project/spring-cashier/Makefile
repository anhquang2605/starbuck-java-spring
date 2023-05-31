all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

combo:
	mvn clean
	docker image rm spring-cashier
	mvn package

combo2:
	mvn clean
	mvn package
	docker build --platform=linux/amd64 -t spring-cashier .

compose-up:
	docker-compose up --scale cashier=2 -d

redis-up:
	docker-compose up -d redis

mysql-up:
	docker-compose up -d mysql

#jumpbox

jumpbox:
	docker run --network spring-cashier_network --name jumpbox -t -d ubuntu

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/spring-cashier .
	docker push $(account)/spring-cashier