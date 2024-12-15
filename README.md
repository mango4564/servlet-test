# Online Banking System

A Java Web-based online banking system with features including user authentication, fund transfers, and transaction history.

## Features

- User Authentication (Login/Logout)
- Balance Inquiry
- Fund Transfer
- Transaction History
- Password Management
- Concurrent Transaction Handling

## Technology Stack

- Java 11
- Servlet API 4.0.1
- MySQL 8.0
- JUnit 4.13.2
- Selenium WebDriver (for UI testing)
- Maven
- JaCoCo (for code coverage)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/test/
│   │       ├── dao/         # Data Access Objects
│   │       ├── filter/      # Servlet Filters
│   │       ├── pojo/        # Plain Old Java Objects
│   │       ├── service/     # Business Logic
│   │       └── servlet/     # Servlet Controllers
│   ├── resources/          # Configuration Files
│   └── webapp/            # Web Resources
└── test/
    └── java/              # Test Cases
```

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven 3.6 or higher
- MySQL 8.0
- Tomcat 9.0

### Setup

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Configure database:
- Create a MySQL database
- Update database configuration in `DbUtils.java`

3. Build the project:
```bash
mvn clean install
```

4. Deploy to Tomcat:
- Copy the generated WAR file to Tomcat's webapps directory
- Start Tomcat server

### Running Tests

```bash
mvn test
```

To generate code coverage report:
```bash
mvn clean test
```
The report will be available in `target/site/jacoco/index.html`

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details 