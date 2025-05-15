# Spribe Interview API Test Framework

This is a lightweight, parallel-ready REST API test automation framework developed as a technical assignment for a Senior QA Automation Engineer role at **Spribe**.

---

## 🚀 Technologies Used

- **Java 11**
- **TestNG** — test runner and assertion framework
- **RestAssured** — HTTP client for API testing
- **Allure Reporting** — rich and interactive test reports
- **JavaFaker** — dynamic test data generation
- **Lombok** — boilerplate-free DTOs and builders
- **Maven** — build tool and dependency management

---

## 📁 Project Structure

```
src
├── main
│   ├── java
│   │   └── dto         # Request/Response DTOs
│   │   └── config      # Framework config
│   │   └── base        # Base test setup + spec factory
│   │   └── steps       # Reusable API step definitions
│   │   └── utils       # Utility classes
├── test
│   └── java
│       └── tests       # TestNG test classes
│       └── builder     # Request builders (Faker-based)
```

---

## 🔧 Setup

```bash
git clone https://github.com/Dermenji/spribe-interview-test-framework.git
cd spribe-interview-test-framework
mvn clean install
```

---

## ✅ How to Run Tests

### Via TestNG XML (default)

Runs all 5 test classes in parallel (3 threads):

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### With Allure Report Generation

```bash
mvn clean test
allure generate allure-results --clean -o allure-report
allure open allure-report
```

---

## 🧪 Covered Endpoints

- `GET /player/create/{editor}` – Create player (workaround due to Swagger bug)
- `POST /player/create/{editor}` – Correct creation
- `POST /player/get` – Get player by ID
- `GET /player/get/all` – Get all players
- `PATCH /player/update/{editor}/{id}` – Update player
- `DELETE /player/delete/{editor}` – Delete player

---

## 🧵 Parallel Execution

TestNG suite is configured to run test **classes in parallel** using 3 threads:

```xml
<suite name="Player API Suite" parallel="classes" thread-count="3">
```

Framework is **thread-safe** via:
- `ThreadLocal<Faker>`
- `ThreadLocal<RequestSpecification>` and `ResponseSpecification`
- No shared mutable state

---

## 📊 Allure Reporting

Each test method and API step is annotated with Allure:
- `@Epic`, `@Feature`, `@Story`, `@Severity`, `@Description`
- Step-level visibility via `@Step`
- Assertion context wrapped in `SoftAssert` for rich failure detail

---

## 👤 Author

**Alexandr Dermenji**  
Senior QA Automation Engineer  
[LinkedIn →](https://linkedin.com/in/dermenji)

---

## 📌 Notes

> Due to a Swagger inconsistency, the player creation endpoint is incorrectly documented as `GET`. The framework supports both versions (`GET` and `POST`) for test completeness.

---