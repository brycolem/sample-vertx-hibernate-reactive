# Questions API

## Table of Contents
- [Overview](#overview)
- [API](#api)
  - [Add a Question](#add-a-question)
  - [Get All Questions](#get-all-questions)
  - [Get a Question by ID](#get-a-question-by-id)
  - [Update a Question](#update-a-question)
  - [Delete a Question](#delete-a-question)
- [Building](#building)

## Overview
Sample Questions API for managing questions with a reactive and non-blocking approach. Leveraging the power of Vert.x and Hibernate Reactive, this API ensures superior performance and scalability. However, it is important to note that authentication and authorization are not included in this example, as these factors may depend heavily on your specific environment. To ensure secure authorization, it is advisable to deploy a service mesh on your Kubernetes deployment and a robust authorization endpoint like Keycloak.

### Build image

```
docker build -t questions-api .
```

### Run image

This command presumed you have set envronmental variable to the location, username and password for your Postgres database authentication.

```
docker run -d -p 8887:8887 \
  -e DB_USERNAME=$DB_USERNAME \
  -e DB_PASSWORD=$DB_PASSWORD \
  -e DB_URI=$DB_URI \
  --name questions-api-test \
  question-api
```

## API
![Vert.x 4.4.1](https://img.shields.io/badge/vert.x-4.4.1-purple.svg)

### Add a Question

* Method: `POST`
* Endpoint: `/api/v1/question`
* Request Body: JSON representation of a `Question` object
* Response: `201 Created` with the created `Question` object

### Get All Questions

* Method: `GET`
* Endpoint: `/api/v1/question`
* Response: `200 OK` with an array of `Question` objects

### Get a Question by ID

* Method: `GET`
* Endpoint: `/api/v1/question/:id`
* Response: `200 OK` with the `Question` object, or `404 Not Found` if the question does not exist

### Update a Question

* Method: `PUT`
* Endpoint: `/api/v1/question/:id`
* Request Body: JSON representation of a `Question` object with updated fields
* Response: `200 OK` with the updated `Question` object, or `404 Not Found` if the question does not exist

### Delete a Question

* Method: `DELETE`
* Endpoint: `/api/v1/question/:id`
* Response: `204 No Content`

== Building

To launch your tests:
```
./mvnw clean test
```

To package your application:
```
./mvnw clean package
```

To run your application locally:
```
./mvnw clean compile exec:java
```
