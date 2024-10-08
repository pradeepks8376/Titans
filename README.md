# Interview Test

## Introduction

Welcome to MagiDash Corp - Our mission is to empower people in corporations to have one place to visualise any data relevant to their job. As such, we create rich and flexible dashboarding tools which will integrate seamlessly with any data source and present the data in many different formats. 

To get the project started we want to be able to show a list of dummy dashboards to users so that we can get a feel for if the clients like the concepts in a demo form.

Provided is a docker based environment that uses [docker compose](https://docs.docker.com/compose/) which contains a mysql container and an nginx container.

## Interview Task

Your task is to build an api service which implements a `/dashboards` endpoint that returns a list of dashboard objects retrieved from the mysql database provided.

The expected format of the dashboard object should be roughly:

```json
{
  "id": <Long>,
  "createdAt": <Date>,
  "updatedAt": <Date>,
  "title": <String>
}
```

You may choose any language and framework to build the api. During the onsite portion of the interview you will be expected to work through a set of stories which will evolve this api service to meet new requirements.

You will need to update the `docker-compose.yml` file to include your api service so that `smoke_tests.sh` runs against your service.

You should also ensure that the smoke tests pass. This will require a sql script that seeds the database with some data.

## Getting Started

### Prerequisites

Before running the environment you will need to ensure you have the required software installed.

Running `./check_prerequisites.sh` will check what software you need to install on your machine to get this working.

### Running the environment

To run the environment, run `docker compose up --force-recreate` in the terminal. This will bring up the terminal and the "fake" api service.

Once the environment is running, in another shell you should be able to run `./smoke_tests.sh` which will test that the service returns at least one dashboard and it has the correct properties.

The api is available on `http://localhost/` and the mysql server is available on mysql:3306. These ports can be easily changed in the `docker-compose.yml` file, however be aware that the `smoke_tests.sh` file will also need updating.

### Working with the database

The seed scripts for the database are located in the `database` folder. When the database container is created, the
scripts will be run automatically.

To apply any script changes when the database is already running you will need to stop the running database container
and destroy it. Running `docker compose down` will stop the environment and destroy the containers for you, the next
time you bring the environment up the containers will be created again.
