# 🏥 Online Pharmacy Management App 💊

The Online Pharmacy Management App is a Java-based back-end application that demonstrates object-oriented programming principles and a layered architecture. It provides a command-line interface (UI) for interacting with the application's functionality.

## 🚀 Features

- **Object-Oriented Programming**: The application showcases the effective implementation of object-oriented programming principles, including encapsulation, inheritance, and polymorphism.
- **Object-Oriented Design Patterns**: The application demonstrates the utilization of common object-oriented design patterns, such as the Repository pattern, to enhance code structure and maintainability.
- **Layered Architecture**: The application follows a layered architecture approach, separating concerns into distinct layers including domain, UI, service, and repository. This stratification promotes code organization, modularity, and maintainability.
- **Command-Line Interface**: The application utilizes a command-line interface (UI) to interact with users. This provides a straightforward and text-based interface for executing commands and accessing various features of the online pharmacy management system.
- **Validation**: The application includes validation mechanisms to ensure data integrity and enforce rules and constraints for medication records, client card information, and transaction data.
- **Error Handling**: The application is equipped with its own custom error classes and implements error handling mechanisms to handle exceptions and provide meaningful feedback to users in case of errors or exceptional scenarios.
- **Test Coverage**: The application has a comprehensive suite of unit tests implemented using the JUnit 5 framework. It achieves 100% test coverage for the classes found in the domain, repository, and service layers, ensuring the reliability and correctness of the application's functionality.
- **CRUD Operations**: The application implements Create, Read, Update, and Delete (CRUD) operations for managing medication records, client card information, and transaction data. This allows for efficient manipulation of data within the application.
- **Medication Management**: Easily create, read, update, and delete medication records. Each medication includes essential information such as ID, name, manufacturer, price, and prescription requirement. The app enforces that the price is strictly positive.
- **Client Card Management**: Efficiently manage client information by creating, reading, updating, and deleting client card records. Each client card contains details like ID, first name, last name, unique personal identification number (CNP), date of birth, and registration date. The app ensures the CNP is unique.
- **Transaction Management**: Facilitates the recording and tracking of transactions. Each transaction includes information such as ID, medication ID, client card ID (optional), quantity, and date/time. The app applies discounts of 10% or 15% on the medication price if a client card is provided, based on whether a prescription is required or not. It calculates and displays the final amount paid and the discounts applied.
- **Search Functionality**: Enables users to perform full-text searches to find specific medications or clients, making it convenient to retrieve relevant information quickly.
- **Transaction History**: Provides a clear overview of all transactions within a specified range of days, helping pharmacy administrators monitor and analyze sales activity.
- **Sales Analysis**: Displays a list of medications in descending order based on the number of sales, allowing pharmacy managers to identify popular products easily.
- **Client Discount Analysis**: Presents a sorted list of client cards in descending order based on the total value of discounts obtained, helping to identify loyal and high-value customers.
- **Data Management**: Allows the deletion of all transactions within a specific range of days, ensuring the database remains organized and up to date.
- **Price Adjustment**: Offers the ability to increase the price of medications by a specified percentage if their current price is below a given threshold. This feature helps maintain profitability and adjust prices based on market conditions.
- **Java Development**: The entire application is developed in Java, a powerful and cross-platform programming language, known for its robustness, flexibility, and extensive ecosystem of libraries and frameworks.
- **Backend Focus**: The application primarily focuses on back-end development, showcasing business logic, data management, and interaction with the underlying data layer.








