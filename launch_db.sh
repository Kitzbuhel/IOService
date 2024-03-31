#!/bin/bash

docker container run -d --name todos_db -p 5433:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=todos postgres

