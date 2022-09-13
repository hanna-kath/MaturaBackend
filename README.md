
# Server with Patient, Practitioner and Encounter

_Revive your project from the dead_

You should have modeled the FHIR Patient, Practitioner and Encounter Resource.

Including the corresponding `import.sql` statements, (spring data) repositories, and (spring web) controller.

Also there should be automated test for these as well.

As a guideline, `Patient` is already present in this template (Feel free to replace it though).

## Setup the project in IntelliJ

In the menu `[Git] -> [Clone...]`.

This [detailed guide](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) explains the process in more depth.

## How to handle your changes?

Just commit and push them regularly.

## Startup the db

The db is configured in [docker-compose.yml](./docker-compose.yml).

You may start it up with a simple

```
docker compose up
```

To destroy it (including all data) run:

```
docker compose down
```

## Running tests

Verify that the test suite runs on your local machine.

It is checked by github as well.

A successful run of the test suite is required!
