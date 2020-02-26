#!/bin/bash

# Define function to run spring-boot applications
run_spring_boot_application()
{
    gnome-terminal -- sh -c '
        cd "$1"
        mvn spring-boot:run
    ' sh $1
}

# Set variables
WORKSHOP_PATH=${PWD}
KEYCLOAK_STABLE_VERSION="9.0.0"
KEYCLOAK_DOWNLOAD_URL="https://downloads.jboss.org/keycloak/${KEYCLOAK_STABLE_VERSION}/keycloak-${KEYCLOAK_STABLE_VERSION}.zip"

# Change directory to keycloak folder
EXISTS="true"

mkdir -p $WORKSHOP_PATH/tmp && cd $WORKSHOP_PATH/tmp

# Download stable version if not present
KEYCLOAK_PATH=$WORKSHOP_PATH/tmp/keycloak-${KEYCLOAK_STABLE_VERSION}
echo ">> $KEYCLOAK_PATH"
if [[ ! -d "${KEYCLOAK_PATH}" ]]; then
    printf "Downloading ${KEYCLOAK_PATH} ...\n"
    curl -LO ${KEYCLOAK_DOWNLOAD_URL}

    printf "Unzipping ${KEYCLOAK_PATH}.zip ...\n"
    unzip -q ${KEYCLOAK_PATH}.zip

    printf "Removing ${KEYCLOAK_PATH}.zip ...\n"
    rm -rf ${KEYCLOAK_PATH}.zip

    EXISTS="false"
fi

run_spring_boot_application ${WORKSHOP_PATH}/guestbook-frontend-master
run_spring_boot_application ${WORKSHOP_PATH}/guestbook-backend-master
run_spring_boot_application ${WORKSHOP_PATH}/guestbook-mail-master

cd ${KEYCLOAK_PATH}/bin
# Add user 'admin' if keycloak was just installed
[[ $EXISTS == "false"  ]] && ./add-user-keycloak.sh -r master -u admin -p codefusion

echo "********************************************************"
echo "********************************************************"
echo -e "\n"
echo -e "Starting KeyCloak on http://localhost:8180/auth"
echo -e "\n"
echo "********************************************************"
echo "********************************************************"

# Start keycloak on port '8180' (offset of +100 from 8080)
./standalone.sh -Djboss.socket.binding.port-offset=100