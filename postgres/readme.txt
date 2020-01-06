Setup a Postgres Docker Container
=================================

Setup Postgres with Docker:

	1. Get Postgre alpine image

		Note, that if you want to use flyway as well, check which postgres version is supported:

		https://flywaydb.org/documentation/database/postgresql

		https://hub.docker.com/_/postgres

		$ docker pull postgres:11-alpine

	2. List all docker images

		$ docker images

	3. Run the image named "postges:11.0-alpine" in detached mode in a container named "postgres-spring-boot-demo" with "root" as postgres password.

		$ docker run --name postgres-spring-boot-demo -e POSTGRES_PASSWORD=root -d -p 5432:5432 postgres:11-alpine

	4. List all running Docker containers
		
		$ docker ps

	5. List all containers (including the stopped containers)

		$ docker ps -a  


Access postgres server from the inside of the container:

	1. Bash into the running postgres Alpine Linux container with the name "postgres-spring-boot-demo" (on windows with MINGW64 cmd, prefix command with "winpty")

		$ docker exec -it postgres-spring-boot-demo bash

		$ winpty docker exec -it postgres-spring-boot-demo bash

	2. Switch into the Postgres server (psql CLI) within the Docker container with the default user "postgres"

		$ psql -U postgres

	3. Switch into the Postgres server (psql CLI) from the outside of the Docker container
	
		$ psql -h localhost -p 5432 -U postgres


Create database and access it with psql CLI:

	1. Lists all postgres servers users
		
		postgres=# \du

	2. Creates a datatabse (Do not forget the semicolon!)
	
		postgres=# CREATE DATABASE bootshopdb;

	3. Connects to database "bootshopdb"

		postgres=# \c bootshopdb;

	4. List all databases

		postgres=# \l