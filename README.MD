# ProductAPI Spring Boot Application

This is a Spring Boot application for managing products using Spring Data JPA and Spring Web.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed (version 21 or higher)
- Maven installed (version 3.0.0 or higher)
- Your favorite IDE (IntelliJ IDEA, Eclipse, etc.) with Spring Boot support

## Getting Started

Follow these steps to run the Spring Boot application:

1. **Clone the repository:**

    ```bash
    git clone https://github.com/saviourumoeka/product-api.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd product-api
    ```

3. **Build the project:**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

   The application will start, and you can access it at [http://localhost:8080](http://localhost:8080).

5. **Access the H2 Database Console:**

   The H2 database console is available at [http://localhost:8080/h2-console](http://localhost:8080/h2-console). Use the following details to log in:

    - JDBC URL: `jdbc:h2:mem:testdb`
    - Username: `sa`
    - Password: (Leave it blank)

   Please note that the H2 database is an in-memory database used for development purposes.

6. Swagger Documentation UI at http://localhost:8080/swagger-ui/index.html

## Project Structure

- `src/main/java/com/product/ProductApiApplication.java`: Main class for the Spring Boot application.
- `src/main/java/com/product/controller/ProductController.java`: Controller class for managing products.
- `src/main/java/com/product/model/Product.java`: Entity class representing a product.
- `src/main/java/com/product/repository/ProductRepository.java`: Spring Data JPA repository for products.
- `src/main/java/com/product/service/ProductService.java`: Service class for business logic related to products.
- `src/test/java/com/product/controller/ProductControllerTest.java`: JUnit tests for the controller.

## Additional Information

- The project uses Lombok for reducing boilerplate code.
- The documentation is generated using Asciidoctor and Spring REST Docs.