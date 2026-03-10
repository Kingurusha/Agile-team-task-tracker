# Agile Team Task Tracker

A backend-focused academic project for managing work in a small agile team environment.

The project is designed as a task tracking system that supports typical team workflow operations such as organizing work items, managing project data, and interacting through a REST API.

---

## Overview

The goal of this project was to build a practical team task tracker with a clear backend structure and an API-first approach.

It was developed as a university team project and includes:

- backend application logic
- REST API
- project documentation
- Postman collection for API testing

---

## Main idea

This project focuses on the core backend side of a task tracking system for agile-style team work.

Typical use cases include:

- managing task-related data
- organizing workflow entities through API endpoints
- testing and interacting with the system via Postman
- documenting the system structure and requirements

---

## Tech stack

- Java
- Spring Boot
- Maven

---

## Repository structure

~~~text
.
├── src/                         # application source code
├── doc/rest/                    # REST-related documentation
├── Task tracker.postman_collection.json
├── pom.xml
├── CP0.docx
├── CP1.docx
├── CP2.docx
└── README.md
~~~

---

## Documentation

The repository includes additional project documentation:

- `CP0.docx` — initial project description
- `CP1.docx` — project specification / analysis
- `CP2.docx` — final project documentation
- `doc/rest/` — REST documentation
- `Task tracker.postman_collection.json` — API collection for testing requests

---

## Running the project

### Prerequisites

- Java 17 or compatible Java version
- Maven

### Run locally

~~~bash
mvn spring-boot:run
~~~

Or build the project first:

~~~bash
mvn clean install
java -jar target/*.jar
~~~

---

## API testing

A Postman collection is included in the repository:

~~~text
Task tracker.postman_collection.json
~~~

It can be imported into Postman to test available endpoints.

---

## Project context

This repository is part of an academic software engineering project focused on backend development, API design, and structured project documentation.

---

## Authors

- Oleg Kovylov
- Dmytro Kovalov
