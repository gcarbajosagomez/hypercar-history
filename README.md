Introduction
================

`Hypercar History` is a pet project that combines two of my biggest passions: cars and coding.

I originally created this project in 2014 as a means to learn and improve my coding skills and continued developing it until 2019.
The websites (`www.paganihistory.com` and `www.bugattihistory.com`) were up and running between 2016 and 2019 (they're no longer up).

The project is designed to show the history and cars of multiple car manufacturers. There is a web UI that displays various data about each manufacturer 
such as a detailed view of each car it has ever produced, the history of the brand, the founders, etc. It also contains a `Contact Us` section that will email
contact request forms to `paganihistory.contact.us@gmail.com`.

Besides this, there's a password-protected CMS (Content Management System) area that allows the administrator to manage the content of the website. Including:

- Add, remove and edit Manufacturers.
- Add, remove and edit Cars, including adding pictures and YouTube videos.

Technology Stack
================

The technology stack is as follows:

- **Java 17**
- **Spring Boot Framework**
- **Hibernate**
- **MySQL database**
- **Freemarker**
- **jQuery**
- **Bootstrap**


Software Requirements
=====================

And the software requirements are the following:

- **JDK 17 or higher**
- **MySQL database**

The server will start on port `8080` by default, and the database should be listening on port `3306`. 
These properties are configurable by editing the `application.properties` file.
