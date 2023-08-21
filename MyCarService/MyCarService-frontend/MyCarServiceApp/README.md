# MyCarServiceApp(BestService)

## Introduction

BestService is a web application, where customers can make a booking for service of their vehicle,
and the service owner can keep track of the incoming bookings and the ongoing vehicle procedures.

This project was built using Angular as a frontend framework, Java Spring Boot as a backend framework and
MySQL as a database.

## Table of Contents

1. [Installation](#installation)
1. [Usage](#usage)
1. [License](#license)

## Installation

To run this project locally, you need to have Angular v16, Node.js, JS installed for the frontend, and Java 17 (or higher) and MySQL Server for the backend and database. Then follow these steps:

* Clone or download this repository to your local machine
  * Database:
    * Open your Task Manager and run the MySQL80 Service
  * Backend:
    * Open `MyCarService-backend` in your IDE
    * Edit the `application.yml` file in the src/main/resources folder and change the username and password for your MySQL connection
    * Run the application from your IDE
    * Backend (RestApi) will be started at `http://localhost:8080/`
  * Frontend:
    * Open `MyCarServiceApp` in you IDE
    * In the terminal run `npm install` to install all dependencies
    * Then run `ng serve` to start the application
    * Navigate to `http://localhost:4200/` in your web browser

## Usage

* When you start the backend there will be two user already created in the database:
    - Normal User:  email: user@test.com, password:123123
    - Administrator: email admin@test.com, password:123123
    - You can use them in order to explore the application
* You also can register new normal user in the application

* Normal user can:
  * `Register`, `Login`
  * Update its profile
  * Add, Edit or Delete the information for it's vehicle
  * Add, Edit or Delete booking for it's vehicle

* Administrator can:
  * Check the bookings
  * Update information of the current booking
  * Delete a booking

## License

This project is not licensed and is only for educational purposes.
