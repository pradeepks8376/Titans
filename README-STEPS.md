# Bring your own interview technical task  - Steps to run

## Setup

To build and run the software:

```windows command prompt
cd bring-your-own-interview
docker compose build
docker-compose up --force-recreate
```

### 01-initial-db.sql
A new 'title' column has been added.

## Testing
Dashboard api service runs on the port 8080.

```bash
# Smoke tests
./smoke_tests.sh

# List all data
curl 'http://localhost:8080/anaplan/dashboards' | jq .
```

## REST API's Swagger document
```
http://localhost:8080/swagger-ui.html#/dashboard-controller
```
## Viewing data on mysql database
1. Login to Mysql workbench and query dashboards table from definitions schema
2. use definitions;
3. select * from dashboards;

#### Grafana
TODO

#### Alert manager
TODO

#### Logging - Centralised Logging mechanism
TODO

### CI/CD using Jenkin Pipeline, docker for Spring boot application
TODO
