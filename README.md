# Welcome to the keycloak hands-on workshop

To get started clone this repo to your local filesystem.

## Part 0 - Setup environment

0. Prerequisits
	* Linux OS, to run `install.sh`
	* [curl](https://curl.haxx.se/download.html)
1. Installation
	* Run `install.sh`.\
	This will setup a keycloak instance (v.9.0.0) running on **localhost:8180**.\
	At the same time the **_frontend_**, **_backend_** and **_email_** applications are started on ports **8081, 8090 and 8082** respectively.
	
## Step 1 - Create a realm

0. In order for all the features to work you need to add an email address to the admin user:
	1. Go to **localhost:8180/auth** in your browser to open the _Admin Console_ of Keycloak.
		* Enter credentials (user: **admin**, password: **codefusion**).
	1. In the top right corner click on `admin` and select _Manage Account_.
	2. Enter `codefusion.devs@gmail.com` for the email.
	3. First and last name are required but can be chosen at will.

2. Similar to namespaces in Kubernetes there are _Realms_ in KeyCloak to seperate the configuration for different applications that can be managed with a single KeyCloak instance.
	1. Create a new **_realm_** with the name **springboot-example**.
	2. In the _Realm Settings_ go to the _Login_ tab and enable the following:
		* `User Registration`
		* `Verify email`
		* `Login email`
	3. In the _Realm Settings_ go to the _Email_ tab and enter the following values:
		* smtp: smtp.gmail.com
		* Port: 465
		* From: codefusion.devs@gmail.com
		* EnableSSL: true
		* Enable StartTLS: true
		* Enable Authentication: true
		* Username: codefusion.devs@gmail.com
		* Password: Key#Cloak123

3. Open a new tab in your browser and go to the frontend application on **localhost:8081**.
	1. Click on _Go to the guestbook_.
	2. You will be forwarded to KeyCloak with a message saying **We are sorry... Client not found**.\
	This is expected because we haven't added the applications to the realm, yet. We will do this in the next part.

## Step 2 - Setup the clients

1. Go back to the _Admin Console_ and select _Clients_ in the panel on the left hand side.
	1. Create a client called **guestbook-frontend-app** with the following attributes:
		- Access Type: `public`
		- Valid Redirect URI: `http://localhost:8081/*`
		- Web Origins: `http://localhost:8081`
	2. Create two more clients with called **guestbook-backend-app** and **guestbook-mail-app** respectively. Use the following attributes for both of them:
		- Access Type: `bearer-only`
2. Go back to the **frontend** application.
	1. You should be shown a Login mask.
	2. Since there is no user created yet, go ahead and Register now.
		* You can register using you PwC-email.
		* In order for the email verification to work you have to copy the link to a new tab in the browser in which your running the application.
	3. Login with the user and go to _Add new entry_.
	4. Enter some data and click **Save**.
	5. You will get an error saying **Uuuups! You are not allowed to send email.**\
	Also, if you go back to the list you won't see any entries there.
	This is due to the fact that your user doesn't have the required role assigned to it. Let's add them in the next step.

## Step 3 - Setup a role
1. Go back to the _Admin Console_. and select **Roles** in the left hand side panel.
2. Create a new **_role_** called `user`.
3. Select **Clients** in the left hand side panel.

	1. Select your previously created user and assign the role `user` in the **Role Mappings** tab.

4. Try to add an entry in the frontend application again.
	1. You will get the same error saying you cannot send emails again, but if you go back to the list you will see the entry there.\ This is because you are now able to access the backend service via the **user** role.
	2. In order to access the mail server you need to add another **role** called **mail** and also assign it to your user. Go ahead and do this now.
5. Create another entry in the guestbook.
	* This time you should get a success message.

## Step 4 - Add further features.
1. In the _Realm Settings_ **Login** tab you can add further features like:
	* `Forgot password`
	* `Remember me` in order to save the login after browser was closed.
2. In the **Identity providers** panel on the left hand side, you can also add Social logins like GitHub:
	1. Go to https://github.com/settings/applications/new and create a new authorization with:
		* HomepageURL: http://localhost:8180/auth
		* Authorization callback URL: Enter the `Redirect URI` that is shown in the _AdminConsole_ when you add GitHub.
		* Copy `Client ID` and `Client Secret` and enter in the respective fields when creating Github as _Identity Provider_.
3. Go to the **frontend** and log out.
4. Try to log-in again. You will now see GitHub as a way to login.