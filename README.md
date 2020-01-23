# Spring Boot Demo
This Spring Boot demo application demonstrates several aspects of Spring Boot and surrounding
technologies. It might be a good starting point for Spring Boot projects with a similar technology
stack, or just can be used to write POCs in order to test new technologies or potential solutions
for certain Spring Boot related problems.

One main focus is on Spring Security's OAuth2 authentication and authorization, including login and
logout. The implemented solution does only rely on the OAuth2 (including OpenID Connect) protocol,
in order to keep the ability to easily exchange the OAuth2 provider, which is here Keycloak running
locally in a Docker container. Especially the authorization role model is not really a typical real
project model. The aim was here to restrict the REST Api to user roles provided by Keycloak and to
roles provided by a JPA datasource (Postgres).

As database a Postgres instance has been chosen, running also in a local Docker container. Note,
that the relational model might be a bit 'constructed' in order to reflect all relevant table
relationships and the resulting Hibernate uni- and bi-directional entity representations.


### Major Tech-Stack
- Spring Boot
- OAuth2 with Keycloak
- Hibernate
- Flyway
- JSP
- Postgres
- Docker
- Swagger API Documentation
- Sonarqube

### Setup Keycloak Docker Container

The are two options to setup a Keycloak instance running in a Docker container. The easy one is the
pre-configured option. Here the creation of the Keycloak realm, clients, secrets, users, roles and
all the other configuration work is already done and out of the box available. Also, there is no
need to exchange the client secret in the source code, as they are correct already.

If you want to get more used to Keycloak and Docker I recommend the manual configuration option.

#### Pre-configured Keycloak Docker Image

1. [Download](https://drive.google.com/uc?id=1-clZHNr_8btaPAqoFr_m0L_JdLyC6Cxc&export=download)
a fully pre-configured Docker Keycloak image as tar.

2. Load the tar and then just run it in a Docker container.

        $ docker load < [Path to downloaded tar]/boss-keycloak-demo-project-docker-image-v3.tar
        $ docker run --name demo-project-keycloak -p 8100:8080 jboss/keycloak

3. To access the Keycloak Administration Console navigate to http://localhost:8100 and enter the
following credentials

- Username: `nilsign@gmail.com`
- Password: `root`

Note, that the password for all other Keycloak Realm users is also `root`.

#### Manual Configuration of the Keycloak Docker Image/Container

If you want to manually setup the docker container follow these instruction here.

1. Get a virgin Keycloak Docker image

        $ docker pull jboss/keycloak

2. Start Keycloak in a Docker container named "demo-project-keycloak" and add the Keycloak Admin
User and restart Keycloak with port mapping (localhost:8100->8080).

        $ docker images
        $ docker run --name demo-project-keycloak -e KEYCLOAK_USER=nilsign@gmail.com -e KEYCLOAK_PASSWORD=root -p 8100:8080 jboss/keycloak

3. Navigate to http://localhost:8100
4. Login, use `nilsign@gmail.com` as user and `root` as password, and navigate to the Administration
Console.
5. Press Realm "Master" -> Add Realm
6. Create Realm with the name: `DemoProjectRealm`
7. Configure "DemoProjectRealm" realm

   7.1 DemoProjectRealm->Configure->Clients->Create
   - Enter as Client ID: `DemoProjectRestApiClient`

    7.2 DemoProjectRealm->Configure->Clients-DemoProjectRestApiClient
    - Enter as Valid Redirect URIs: `http://localhost:8080`

    7.3 DemoProjectRealm>Configure->Roles->Add roles
    - Enter as Role Name: `ROLE_REALM_GLOBALADMIN`

    7.4 DemoProjectRealm->Configure->Clients->DemoProjectRestApiClient->Roles
    - Enter as Role Name: `ROLE_CLIENT_ADMIN`
    - Repeat, 7.4 with the role names: `ROLE_CLIENT_SELLER`
    - Repeat, 7.4 with the role names: `ROLE_CLIENT_BUYER`

    7.5 DemoProjectRealm->Configure->Manage->Users->Add user
    - Enter as Username: `nilsign`
    - Enter as Email: `nilsign@nilsign.com`

    7.6 DemoProjectRealm->Configure->Manage->Users->nilsign->Credentials

    - Enter temporary password and press "Set Password"
    - Repeat, 7.5 and 7.6 with the email addresses: `ada.mistrate@nilsign.com`
    - Repeat, 7.5 and 7.6 with the email addresses: `selma.sellington@nilsign.com`
    - Repeat, 7.5 and 7.6 with the email addresses: `bud.buyman@nilsign.com`

    7.7 DemoProjectRealm->Configure->Manage->Users->nilsign->Role Mappings->Realm Roles
    - Select `ROLE_REALM_GLOBALADMIN` and press "Add selected"

    7.8 DemoProjectRealm>Configure->Manage->Users-> ...
    - ... nilsign->Role Mappings->Client Roles->DemoProjectRestApiClient
        - Select `ROLE_CLIENT_ADMIN` and press "Add selected"
    - ... ada->Role Mappings->Client Roles->DemoProjectRestApiClient
        - Select `ROLE_CLIENT_ADMIN` and press "Add selected"
    - ... selma->Role Mappings->Client Roles->DemoProjectRestApiClient
        - Select `ROLE_CLIENT_SELLER` and press "Add selected"
    - ... bud->Role Mappings->Client Roles->DemoProjectRestApiClient
        - Select `ROLE_CLIENT_BUYER` and press "Add selected"

    7.9 DemoProjectRealm>Configure->Clients->Account
    - Enable Service Accounts: ON
    - Enable Authorization: ON

8. The Keycloak setup up is not fully completed yet, as a temporary password was used for the
Keycloak admin user "nilsign@gmail.com" (Do NOT mix up this user with "nilsign@nilsign.com", which
is only a Realm user). To finish the setup navigate to the following URL and enter
the final password. Otherwise Keycloak requests will return "error": "invalid_grant - Account is not
fully set up"!

    http://localhost:8100/auth/realms/DemoProjectRealm/account/

9. (Optional) Commit the running Keycloak Docker container to a new Docker image.

        $ docker ps -a
        $ docker commit [CONTAINER ID] jboss/keycloak:demo-project-v3

10. (Requires: 9) To start the new jboss/keycloak:demo-project-v3 Docker image again when it was
shut down execute

        $ docker run -p 8100:8080 jboss/keycloak:demo-project-v3

11. To start a stopped container (e.g. after reboot, etc...) call start with the container name or
id.

        $ docker ps -a

        $ docker start demo-project-keycloak

        $ docker start [CONTAINER ID]

12. Open the application.yaml(s) in the project's root folder and set the correct (your) the
Keycloak's "client-secret".

Note, the client secret can be found at
DemoProjectRealm->Configure->Clients->Account->Credentials

13. Test the Keycloak instance with Postman

    POST REQUEST: http://localhost:8100/auth/realms/DemoProjectRealm/protocol/openid-connect/token

    BODY: x-www-form-urlencoded

    KEY: `client_id` => VALUE: `DemoProjectRestApiClient`<br>
    KEY: `username` => VALUE: `nilsign@nilsign.com`<br>
    KEY: `password` => VALUE: `root`<br>
    KEY: `grant_type` => VALUE: `password`<br>
    KEY: `client_secret` => VALUE: `6a06b69f-8108-4d40-af64-ed1325385c5d` <br>

    Note, the correct client secret can be found at
    DemoProjectRealm->Configure->Clients->Account->Credentials

### Setup Postgres

##### Get a Postgres Docker image and start a container

1. Get Postgres alpine image.<br>
    Note, that if you want to use Flyway as well, check here [which postgres version is supported](
    https://flywaydb.org/documentation/database/postgresql).

        $ docker pull postgres:11-alpine

2. Run the image named "postges:11.0-alpine" in detached mode in a container named
"demo-project-postgres" with "root" as Postgres password.

        $ docker images
        $ docker run --name demo-project-postgres -e POSTGRES_PASSWORD=root -d -p 5432:5432 postgres:11-alpine

3. (Optional) List all running Docker containers

        $ docker ps

4. (Optional) List all containers (including the stopped containers)

        $ docker ps -a  


##### Access Postgres CLI from the inside of the container

1. Bash into the running postgres Alpine Linux container with the name "demo-project-postgres"
(Note, on Windows with MINGW64 cmd use `winpty` as prefix).

        $ docker exec -it demo-project-postgres bash
        $ winpty docker exec -it demo-project-postgres bash

2. Switch into the Postgres CLI (psql) within the Docker container with the default user "postgres"

        $ psql -U postgres

3. (Use 2. xor 3.) Switch into the Postgres CLI (psql) from the outside of the Docker container

        $ psql -h localhost -p 5432 -U postgres

##### Create project database with psql

1. (Optional) List all postgres servers users

        postgres=# \du

2. Create a the Demo Project Postgres database (Do not forget the semicolon!)

        postgres=# CREATE DATABASE demoprojectdb;

3. (Optional) List all databases

        postgres=# \l

4. (Optional) Connect to database "demoprojectdb"

        postgres=# \c demoprojectdb;

5. (Optional) Disconnect from database "demoprojectdb"

        postgres=# \q

### Setup Sonarqube

1. Get Sonarqube Docker image and run it in a Docker container

        $ docker pull sonarqube
        $ docker run -d --name sonarqube -p 9000:9000 sonarqube

2. Execute Sonarqube's code analyses.

        $ mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=the-generated-token

3. Navigate to the to http://localhost:9000 and enter the default credentials to inspect the results
- Username: `admin`
- Password: `admin`

More detailed information can be found on the official [Sonarqube](https://docs.sonarqube.org/latest/)
and [Sonarqube Docker](https://hub.docker.com/_/sonarqube/) pages

### Swagger
A Swagger API documentation can be found here once the project is running (locally) on the DEV
environment.
- http://localhost:8080/swagger-ui.html

### Road Map
+ Angular Frontend (will be done in a separate repository)
+ Automatic TypeScript DTO generation.
+ RestAPI extensions depending on the concrete FE needs
+ RestAPI Request Error Handling
+ Unit tests
+ Integration tests
