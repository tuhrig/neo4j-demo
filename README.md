Neo4J Demo
==========

## About

This is a simple Java + Spring Boot project which uses Neo4J with Spring Data. The project shows how a simple domain
model can be implemented with some basic use-cases.

## Note

- To show different approaches, the same thing might be implemented in different ways in the code.
- I've used comments throughout the code to underline the ideas.

## What to see here

- Basic project setup (Java + Spring Boot + Neo4J + Spring Data)
- Domain Model (Product <> Shop <> Location)
- Implementation of repositories
- Partial read and write operations using projections
- Some REST-controllers
- Tests
- Use of Lombok for clean code

## Run locally

    docker run --publish=7474:7474 --publish=7687:7687 --env=NEO4J_AUTH=none neo4j:4.3.6

    ./gradlew bootrun

## Run the tests

    ./gradlew check

## Docs

    http://localhost:8080/swagger-ui/index.html

## Login to Neo4J Web Console

    http://localhost:7474/browser/
    With: neo4j / neo4j

See: https://hub.docker.com/_/neo4j
