#!/usr/bin/env bash
docker volume create pgdata-vehicle
docker run --rm --name postgres-vehicle -e POSTGRES_PASSWORD=pgpass -d -p 5432:5432 -v pgdata-vehicle:/var/lib/postgresql/data postgres:12
until docker exec postgres-vehicle psql -q -h localhost -U postgres -c '\q'; do >&2 echo "Waiting for Postgres ..."; sleep 2; done
docker exec -i postgres-vehicle psql -U postgres -c "CREATE DATABASE vehicle WITH ENCODING='UTF8' OWNER=postgres;"
