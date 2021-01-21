#!/bin/bash

while ! psql -U postgres vehicle -c 'CREATE DATABASE "user" WITH ENCODING="UTF8" OWNER=postgres;'; do
  echo "Waiting 1 second for DB to open port"
  sleep 1
done;
