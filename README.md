Neo4J Demo
==========

## About

This is a simple Java + Spring Boot project which uses Neo4J with Spring Data. The project shows how a simple domain
model can be implemented with some basic use-cases.

## What to see here

- Basic project setup (Java + Spring Boot + Neo4J + Spring Data)
- Domain Model (Product <> Shop <> Location)
- Implementation of repositories
- Partial read and write operations using projections
- Some REST-controllers
- Tests

## Run locally

    docker run --publish=7474:7474 --publish=7687:7687 --env=NEO4J_AUTH=none neo4j:4.3.6

    ./gradlew bootrun

## Run the tests

    ./gradlew check

## Login to Neo4J Web Console

    http://localhost:7474/browser/
    With: neo4j / neo4j

See: https://hub.docker.com/_/neo4j
