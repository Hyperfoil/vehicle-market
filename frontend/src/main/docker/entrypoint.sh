#!/bin/bash

VEHICLE_LOADER=${VEHICLE_LOADER:-http://localhost:8081}

while ! curl $VEHICLE_LOADER/vehicle-loader/local -X POST -q; do
  sleep 1;
done;

/deployments/run-java.sh