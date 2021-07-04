# Warehouse pet project

It is nicely demonstrated in the Table of Contents of the Markdown Cheatsheet.

## Table of Contents

<!--ts-->

* [About The Project](#About-The-Project)
* [Technical details](#Technical-details)
    * [Technologies used](#Technologies-used)
    * [Project Structure](#Project-Structure)
    * [Database Schema](#Database-Schema)
    * [API Documentation](#API-Documentation)
* [Things TODO](#Things-TODO)
* [Getting Started](#Getting-Started)
    * [Local environment Setup](#Local-environment-Setup)
    * [Building application](#Building-application)
    * [Testing application](#Testing-application)
    * [Running application](#Running-application)
    * [Environment variables](#Environment-variables)
* [Authors](#Authors)

<!-- ABOUT THE PROJECT -->

## About The Project

This project represents a small service with holding inventory and product management capabilities. It is composed of
following functionality:

* Create, Update, Observe products
* Create, Update, Observe articles
* Observe products availability (number of available products based on inventory)
* Sell (fake) product instance according to current inventory
* Import products by JSON file upload
* Import articles by JSON file upload

This implementation is based on my understanding of the requirements for the warehouse application and following
assumptions:

* Everything should be implemented according to requirements
* Free choice of programming language, technologies and infrastructural components
* Compatibility with file formats shared for imports
* Articles and products should hold unique name attributes in order to verify that dedicated item exists
* Article update functionality works in a way of increasing article stock if article exists
* Product update functionality works in a way of updated product contained article items (full-replace) if product
  exists
* Product instance sell functionality works in a way of just reducing inventory stocks in case there is enough
* Articles import works in a way of increasing article stock if there is an article with passed name or creating the new
  one
* Products import works in a way of updating product contained article items if there is a product with passed name or
  creating the new product

## Technical details

### Technologies used

* Kotlin
* Spring Boot
* Gradle
* Spring Data JDBC
* PostgreSQL
* Flyway

### Project Structure

The project is structured "DDD" style:
There are 3 main modules:

* **core** - contains all core components (configurations, extension, common abstractions, etc.)
* **domain** - contains domain abstract components (aggregates, repositories, use-cases, etc.)
* **distribution** - contains application abstractions (api, application services, ports, adaptors, repositories
  implementations, etc.)

### Database Schema

The database schema it represented by 3 primary tables organised in many-many relationship:
<!-- DB SCHEMA -->
<br />
<p align="center">
  <img src="docs/images/db_schema.png.png" alt="Logo" width="50%" height="50%">
</p>

### API Documentation

In order to access Swagger API doc please run th application as described below and visit the URL:

```http request
    http://{host}:{port}/swagger-ui/
```

## Things TODO

* Introduce Gradle multi-module project, extract domain and distribution packages into separate modules with all
  dependencies isolation
* Add caching for the places marked with TODO (primarily for articles and products)
* Change findAll kind of APIs to paginated (PaginatedRequest/PageableResponse) with all dependent components and return
  them in HATEOAS way
* Add Unit/Int test coverage (Due to the lack of time I was focused on required logic implementation)
* Add authorization for end points by introducing user context holder (aka token)
* Enhance error handling, add more logs for monitoring
* Extract use cases common logic (small parts)

The project was tested manually for positive and most important negative cases.

<!-- GETTING STARTED -->

## Getting Started

### Local environment Setup

Ensure you have a docker installed with docker-compose. In order to setup all the resources necessary for the local
environment please run following instruction in console being in the project root directory:

```sh
docker-compose up
```

It will automatically create postgres container and initialize it with new database and users.

### Building application

In order to build the application please run following instruction in console being in the project root directory:

```sh
./gradlew clean build
```

### Testing application

In order to execute unit tests please run following instruction in console being in the project root directory:

```sh
./gradlew test
```

### Running application

In order to run application run following instruction in console being in the project root directory:

```sh
./gradlew bootRun
```

For simplicity Flyway is configured to run during the app startup so it will ensure migrations application for DB.

### Environment variables

There are following environment variables used to configure application:

* [string] PRIMARY_DS_URL - database URL for primary datasource used by app
* [string] PRIMARY_DS_USERNAME - database username for primary datasource used by app
* [string] PRIMARY_DS_PASSWORD - database user password for primary datasource used by app
* [string] MIGRATION_DS_URL - database URL for migration datasource used by Flyway
* [string] MIGRATION_DS_USERNAME - database username for migration datasource used by Flyway
* [string] MIGRATION_DS_PASSWORD - database user password for migration datasource used by Flyway

## Authors

Mikhail Sycheuski



