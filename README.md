# Welcome to the keycloak hands-on workshop

To get started clone this repo to your local filesystem.

## Part 0 - Setup environment

0. Prerequisits
	* Linux OS, to run `install.sh`
	* [curl](https://curl.haxx.se/download.html)

1. Installation
	* Run `install.sh`.\
	This will setup a keycloak instance (v.9.0.0) running on **localhost:8180**.\
	At the same time the **_frontend_**, _**backend_** and **_email_** applications are started on ports **8081, 8090 and 8082** respectively.
	
## Part 1 - Configure KeyCloak

1. Go to **localhost:8180/auth** in your browser to open the _Admin Console_ of Keycloak.
	* Enter credentials (user: **admin**, password: **codefusion**).
	
### Create Realm
1. Create a new _realm_ with the name **springboot-example**.\
It's important to use this exact name because the applications are configured to use it to authenticate the user.
	
2. Create a clients.
	* For **guestbook-frontend-app** use:
		- Access Type: `public`
		- Valid Redirect URI: `http://localhost:8081/*`
		- Web Origins: `http://localhost:8081`
	* For **guestbook-backend-app** and **guestbook-mail-app** respectively, use:
		- Access Type: `bearer-only`
	
3. Create a new **_role_** called `user.
4. Create new user and assign the role `user` in the **Role Mappings** tab.
	
5. Start frontend application
	* Go to **localhost:8081** in your browser to open the **_Frontend_** application.
	* Login with the previously created user.
