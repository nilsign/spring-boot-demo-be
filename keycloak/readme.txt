Spring Boot OAuth2 with Keycloak
================================


(A) Setup Keyclock with Docker:
	
	1. Get Keycloak Docker image

		$ docker pull jboss/keycloak

	2. Start Keycloak, add Admin User and restart Keycloak with port mapping (localhost:8100->8080)

	   	$ docker run -e KEYCLOAK_USER=nilsign@gmail.com -e KEYCLOAK_PASSWORD=root -p 8100:8080 jboss/keycloak

	3. Navigate to localhost:8100  
	
	4. Login, use KEYCLOAK_USER and KEYCLOAK_PASSWORD, and navigate to the Administration Console 

	5. You can now skip the manual Keycloak configuration (step 6 - 10) by importing the "realm-export.json"
	   SpringBootDemo->Manage->Import

	   realm-export.json -> keycloak-master-admin:
		username: nilsign@gmail.com
		password: root

	6. Press Realm "Master" -> Add Realm
	
	7. Create Realm with the name: SpringBootDemo

	8. Configure SpringBootDemo realm:
		8.1 SpringBootDemo->Configure->Clients->Create
			Enter as Client ID: SpringBootDemoLogin
		8.2 SpringBootDemo->Configure->Clients->SpringBootDemoLogin
			Enter as Valid Redirect URIs: http://localhost:8080*  
		8.3 SpringBootDemo->Configure->Roles->Add roles
			Enter as Role Name: GLOBAL_ADMIN
			(Repeat, 7.3 with the role names: ADMIN, SELLER, SUPPORT, BUYER)
		8.4 SpringBootDemo->Manage->Users->Add user
			Enter as Username: nilsign
			Enter as Email: nilsign@gmail.com
			Enable Email Verification: ON
		8.5 SpringBootDemo->Manage->Users->nilsign->Credentials
			Enter temporary password and press "Set Password"
			(Repeat, 7.4 and 7.5 with the email addresses: bud.buyman@gmail.com, mad.alistoles@gmail.com)
		8.6 SpringBootDemo->Manage->Users->nilsign->Role Mapping
			Select GLOBALADMIN and press "Add selected"
		8.7 SpringBootDemo->Manage->Users->budbyman->Role Mapping
			Select BUYER and press "Add selected"
			(Repeat 7.7 for the user "madalistoles")
		8.8 SpringBootDemo->Configure->Clients->Account
			Enable Service Accounts: ON
			Enable Authorization: ON

	9. Account setup up is not fully completed yet, as a tempory password was used for user "nilsign@gmail.com".
	   To finish the setup navigate to the following URL and enter the final password.
	
	   http://localhost:8100/auth/realms/SpringBootDemo/account/
	
	   Otherwise keykloak below will return "error": "invalid_grant - Account is not fully set up"
	   
	10. Export/persist Keycloak setting
		SpringBootDemo->Manage->Export

	12.	Commit running Keycloak docker container to new docker image. (4cfca7c93d87 = Container ID)
	   	$ docker commit 4cfca7c93d87 jboss/keycloak/springbootdemo

	13. To start the docker jboss/keycloak/springbootdemo docker image again when it was shut down execute:
		$ docker run -p 8100:8080 jboss/keycloak/springbootdemo

	14. All other users can be created by using the KeyCloak API or the Spring Boot Demo FE.


(B) Test: Get Access Token with Postman

	URL: Postman
	http://localhost:8100/auth/realms/SpringBootDemo/protocol/openid-connect/token

	BODY: x-www-form-urlencoded

	KEY: client_id VALUE: SpringBootDemoLogin
	KEY: username VALUE: nilsign@gmail.com
	KEY: password VALUE: root
	KEY: grant_type VALUE: password
	KEY: client_secret VALUE: c91bf094-8f59-497f-a9cd-76bd5c379509  

	Note, the client secret can be found at
	SpringBootDemo->Configure->Clients->"Account"->Credentials
