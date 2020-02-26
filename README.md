# Welcome to the keycloak hands-on workshop

To get started clone this repo to your local filesystem.

0. Prerequisits
	* Linux OS, to run `install.sh`
	* [curl](https://curl.haxx.se/download.html)

1. Installation
	* Run `install.sh`.\
	This will setup a keycloak instance (v.9.0.0) running on **localhost:8180**.\
	At the same time the **_frontend_**, _**backend_** and **_email_** applications are started on ports **8081, 8090 and 8082** respectively.
	
2. Configure KeyCloak
	* Go to **localhost:8180** in your browser to open the _Admin Console_ of Keycloak.
	* Enter credentials (user: **admin**, password: **codefusion**).
	
3. Start frontend application
	* Go to **localhost:8081** in your browser to open the **_Frontend_** application.
