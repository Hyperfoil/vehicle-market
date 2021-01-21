#!/bin/bash

VEHICLE_LOADER=${VEHICLE_LOADER:-http://localhost:8081}
USER_LOADER=${USER_LOADER:-http://localhost:8083}
USER_COUNT=${USER_COUNT:-1000}
TOKEN_COUNT=${USER_COUNT:-100}

while ! curl $VEHICLE_LOADER/vehicle-loader/local -X POST -q; do
  sleep 1;
done;
while ! curl ${USER_LOADER}'/user-loader?users='${USER_COUNT}'&tokens='${TOKEN_COUNT} -X POST -q; do
  sleep 1;
done;

/deployments/run-java.sh