#!/bin/bash

curl -s http://localhost:8081/vehicle-loader -X DELETE &&
curl -s http://localhost:8081/vehicle-loader/local -X POST &&
curl -s http://localhost:8082/loader/features -X POST &&
curl -s http://localhost:8082/loader/offering -X DELETE &&
curl -s http://localhost:8082/loader/offering/1000 -X POST &&
curl -s http://localhost:8083/user-loader -X DELETE &&
curl -s 'http://localhost:8083/user-loader?users=1000&tokens=100' -X POST