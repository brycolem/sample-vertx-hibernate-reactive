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
This document provides an overview of the Questions API, which is designed for managing questions in a reactive and non-blocking manner. The API is built using Vert.x and Hibernate Reactive, ensuring high performance and scalability.

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
