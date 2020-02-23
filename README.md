# SPRING BOOT DEMO

This Spring Boot demo application demonstrates several aspects of Spring Boot and surrounding
technologies. It might be a good starting point for Spring Boot projects with a similar technology
stack, or just can be used as a quickstart to test new technologies or potential solution for
certain Spring Boot related problems.

One main focus is on Keycloak, which acts as authentication (OpenID Connect) and authorization
(OAuth2) provider. Keycloak's authorization is combined with Spring Security's controller function
annotation based role and authority management framework. Be aware, that especially the
authorization role model is not necessarily a typical real-world model. The core intention is here to
restrict the REST Api to user roles provided by Keycloak and to roles provided by a JPA datasource
(Postgres).

As database a Postgres instance is running also (as Keycloak) in a local Docker container. Note,
that the relational model might be as well a bit 'constructed' in order to reflect all relevant
database table relationships and the resulting Hibernate uni- and bi-directional entity
representations.

### Major Tech-Stack
- Spring Boot
- OAuth2 with Keycloak
- Hibernate
- Lombok
- Flyway
- REST Api
- JSP
- Postgres
- Docker
- Swagger API Documentation
- Sonarqube
- Typescript DTO Generation

## SETUP

### Setup Keycloak Docker Container

There are two options to setup a Keycloak instance running in a Docker container. The easy one is
the pre-configured option. Here the creation of the Keycloak realm, clients, secrets, users, roles
and all the other configuration work is already done and out of the box available. Also, there is
no need to exchange the client secret in the source code, as they are correct already.

To get more used to Keycloak and Docker the manual configuration option is recommended.

#### Pre-configured Keycloak Docker Image

For a manual setup of the docker container skip this chapter and instead follow the instructions of
the manual configuration.

1. [Download](https://drive.google.com/file/d/1syQ0qR7WjD2oNkev5vQFCtMAFVkWl1sD/view?usp=sharing)
a fully pre-configured Docker Keycloak image as tar.

2. Load the tar and then just run it in a Docker container.

        $ docker load < [Path to downloaded tar]/boss-keycloak-demo-project-docker-image-v3.tar
        $ docker run --name demo-project-keycloak -p 8100:8080 jboss/keycloak

3. To access the Keycloak Administration Console navigate to http://localhost:8100 and enter the
following credentials

    Username: `nilsign@gmail.com`<br>
    Password: `root`

Note, that the password for all other Keycloak Realm users is also `root`.

#### Manual Configuration of the Keycloak Docker Container

##### Get a Keycloak Image and start a Container

1. Get a virgin Keycloak Docker image

        $ docker pull jboss/keycloak

2. Start Keycloak in a Docker container named "demo-project-keycloak", add the Keycloak Admin User
and restart Keycloak with port mapping (localhost:8100->8080).

        $ docker images
        $ docker run --name demo-project-keycloak -e KEYCLOAK_USER=nilsign@gmail.com -e KEYCLOAK_PASSWORD=root -p 8100:8080 jboss/keycloak

3. Navigate to http://localhost:8100

4. Login as Keycloak Administrator, use `nilsign@gmail.com` as user and `root` as password and
navigate to the Administration Console.

5. If Keycloak responds to http requests with this payload `"error": "invalid_grant - Account is not
fully set up"`, ensure that the temporary Keycloak password is not active anymore! This can be
tested by a Keycloak logout and re-login or by simply browsing to http://localhost:8100 in the
web browsers Incognito-Mode. If required, there will be a prompt to enter a permanent password.

##### Create a Keycloak Realm

1. Navigate to http://localhost:8100/auth/admin/master/console

2. Press Realm "Master" -> Add Realm<br>

    Name: `DemoProjectRealm`

3. Click to DemoProjectRealm->Configure->Clients->"Account"
   http://localhost:8100/auth/realms/DemoProjectRealm/account

   Enable Service Accounts: ON<br>
   Enable Authorization: ON

##### Create a Keycloak Realm Roles and Users

1. Click to DemoProjectRealm->Configure->Roles->Realm Roles->"Add roles"<br>

    Role Name: `ROLE_REALM_GLOBALADMIN`

2. Click to DemoProjectRealm->Configure->Manage->Users->"Add user"<br>

    Username: `nilsign`<br>
    Email: `nilsign@nilsign.com`

    Do NOT mix up this user with "nilsign@gmail.com", which is administrator of Keycloak itself.

3. Click to DemoProjectRealm->Configure->Manage->Users->nilsign->"Credentials", enter temporary
password and press "Set Password".

4. Click to DemoProjectRealm->Configure->Manage->Users->nilsign->Role Mappings->"Realm Roles",
select `ROLE_REALM_GLOBALADMIN` and press "Add selected".

##### Create a Keycloak Realm Client

1. Click to DemoProjectRealm->Configure->Clients->"Create"<br>

    Client ID: `DemoProjectRestApiClient`

2. Click to DemoProjectRealm->Configure->Clients->"DemoProjectRestApiClient"<br>

    Enabled: ON<br>
    Valid Redirect URIs: `http://localhost:8080/*`<br>
    Web Origins: `+`

##### Create a Keycloak DemoProjectRealm Client Roles and Users

1. Click to DemoProjectRealm->Configure->Clients->DemoProjectRestApiClient->Roles->"Add Role"<br>

    Role Name: `ROLE_REALM_CLIENT_ADMIN`

    Repeat 1. with Role Name: `ROLE_REALM_CLIENT_SELLER`<br>
    Repeat 1. with the Role Name: `ROLE_REALM_CLIENT_BUYER`<br>

2.  Click to DemoProjectRealm->Configure->Manage->Users->"Add user"

    Username: `nilsign`<br>
    Email: `nilsign@nilsign.com`<br>
    Temporary Password: `root`<br>

    Do NOT this user mix up this with the Keycloak administrator "nilsign@gmail.com".

    Repeat 2. with:<br>

    Username: `ada`<br>
    Email: `ada.mistrate@nilsign.com`<br>
    Temporary Password: `root`<br>

    Username: `selma`<br>
    Email: `selma.sellington@nilsign.com`<br>
    Temporary Password: `root`<br>

    Username: `bud`<br>
    Email: `bud.buyman@nilsign.com`<br>
    Temporary Password: `root`<br>

    Username: `mad`<br>
    Email: `mad.allistoles@nilsign.com`<br>
    Temporary Password: `root`<br>

3. Click to DemoProjectRealm->Manage->Users-> ...

    ... nilsign->Role Mappings->Client Roles->"DemoProjectRestApiClient"

    Select role `ROLE_REALM_CLIENT_ADMIN` and press "Add selected"

    ... nilsign->Role Mappings->Client Roles->"realm-management"

    Select role `manage-user` and press "Add selected"
    Select role `realm-admin` and press "Add selected"
    Select role `view-realm` and press "Add selected"
    Select role `view-users` and press "Add selected"

    ... ada->Role Mappings->Client Roles->"DemoProjectRestApiClient"

    Select role `ROLE_REALM_CLIENT_ADMIN` and press "Add selected"<br>
    Select role `ROLE_REALM_CLIENT_SELLER` and press "Add selected"

    ... selma->Role Mappings->Client Roles->"DemoProjectRestApiClient"

    Select role `ROLE_REALM_CLIENT_SELLER` and press "Add selected"

    ... bud->Role Mappings->Client Roles->"DemoProjectRestApiClient"

    Select role `ROLE_REALM_CLIENT_BUYER` and press "Add selected"

##### Update Keycloak's Client Authenticator Secret in the Code

Open the `application.yaml(s)` in the project's 'resources' folder and set the correct (your)
Keycloak's "client-secret".

Note, that the correct client secret can be found at
DemoProjectRealm->Configure->Clients->Account->"Credentials"

#### Keycloak Docker Management

1. (Optional) Commit the running Keycloak Docker container to a new Docker image.

        $ docker ps -a
        $ docker commit [CONTAINER ID] jboss/keycloak:demo-project-v4

2. (Requires: 9) To start the new jboss/keycloak:demo-project-v4 Docker image execute

        $ docker run -p 8100:8080 jboss/keycloak:demo-project-v4

3. To start a stopped container (e.g. after reboot, docker update etc...) call start with the
container name or id.

        $ docker ps -a
        $ docker start demo-project-keycloak
        $ docker start [CONTAINER ID]

#### Test Keycloak DemoProjectRestApiClient with Postman

Test the Keycloak instance with [Postman](https://www.getpostman.com/)

POST REQUEST: http://localhost:8100/auth/realms/DemoProjectRealm/protocol/openid-connect/token

REQUEST BODY: x-www-form-urlencoded

KEY: `client_id` => VALUE: `DemoProjectRestApiClient`<br>
KEY: `username` => VALUE: `nilsign@nilsign.com`<br>
KEY: `password` => VALUE: `root`<br>
KEY: `grant_type` => VALUE: `password`<br>
KEY: `client_secret` => VALUE: `6a06b69f-8108-4d40-af64-ed1325385c5d` <br>

Note, that the correct client secret can be found at
DemoProjectRealm->Configure->Clients->Account->Credentials

### Setup Postgres

##### Get a Postgres Docker Image and start a Container

1. Get Postgres alpine image.<br>
    Note, that the selected Postgres version must be supported by Flyway. Check [here which postgres
    version is supported](https://flywaydb.org/documentation/database/postgresql).

        $ docker pull postgres:11-alpine

2. Run the image named "postgres:11.0-alpine" in detached mode in a container named
"demo-project-postgres" with "root" as Postgres password for the Postgres super admin user (named
"postgres"). 

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

Get Sonarqube Docker image and run it in a Docker container

        $ docker pull sonarqube
        $ docker run -d --name sonarqube -p 9000:9000 sonarqube


## DEV TOOLS

### Generate Typescript Data Transfer Objects (DTOs)

"[typescript-generator](https://github.com/vojtechhabarta/typescript-generator) is a tool for
generating TypeScript definition files (.d.ts) from Java JSON classes. If you have REST service
written in Java using object to JSON (wire format) mapping you can use typescript-generator to
generate TypeScript interfaces or classes from Java classes."

Execute the following maven goal and a directory named 'generated' will appear with a Typescript
file containing all Typescript DTO models. Copy the `dto-models.ts` file into the corresponding REST
API client (e.g. a Angular Frontend) project and use it there to communicate with this backend through
its REST API.

Adapt the [typescript-generator Maven plugIn](http://www.habarta.cz/typescript-generator/maven/typescript-generator-maven-plugin/plugin-info.html)
values in the the `pom.xml` to e.g. mark additional java packages to be including in the Typescript
DTO model generation path-scanner.

        $ mvn typescript-generator:generate

### Code Analysis

1. Execute Sonarqube's code analyses.

        $ mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login="admin" -Dsonar.password="admin"

2. Navigate to the to http://localhost:9000 and enter the default credentials to inspect the results

    Username: `admin`<br>
    Password: `admin`

    More detailed information can be found on the official [Sonarqube](https://docs.sonarqube.org/latest/)
    and [Sonarqube Docker](https://hub.docker.com/_/sonarqube/) pages

### Swagger

A Swagger API documentation can be found here once the project is running (locally) on the DEV
environment.

- http://localhost:8080/swagger-ui.html

## ROAD MAP

+ Angular Frontend (will be done in a separate repository)
+ RestAPI extensions depending on the concrete FE needs
+ RestAPI Request Error Handling
+ Unit tests
+ Integration tests
