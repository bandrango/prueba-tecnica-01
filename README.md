# Product Pricing Service (Hexagonal Architecture)

## Overview
The **Product Pricing Service** is a Spring Boot application designed to manage pricing offers for products within an e-commerce platform. It follows **Hexagonal Architecture** (Ports and Adapters), ensuring a clean separation between domain logic, application services, and infrastructure.

## Key Features
- **Create, delete, and query** offers in the system.
- **Flatten intervals** for a specific product, ensuring no overlapping time ranges and correct priority handling.
- **RESTful endpoints** documented with **Swagger**.
- **PostgreSQL** integration using **Spring Data JPA**.
- **Java 17**, **Lombok**, **SOLID principles**, and **functional programming** in domain logic.

## Architecture (Hexagonal)
- **Domain**: Contains the core business logic (e.g., `OfferDomainService`) and domain model (`Offer`).
- **Application**: Implements use cases (`OfferUseCaseImpl`) and defines ports (inbound/outbound).
- **Infrastructure**: Provides adapters (e.g., `OfferController` for REST, `OfferPersistenceAdapter` for JPA).

## Technology Stack
- **Java 17**  
- **Spring Boot** (Web, Data JPA)  
- **PostgreSQL**  
- **Lombok**  
- **Swagger/OpenAPI 3.0** for API documentation  
- **JUnit 5** for unit testing  
- **Docker** for containerization  

## How to Build and Run

1. **Database Setup**  
   - Create a PostgreSQL database named `product_pricing` and a user `pricing_user` with password `secure_password`.
   - Run the SQL script to create the `offer` table with the required columns.
   
```sql
-- Create the offers table.
CREATE TABLE offer (
    offer_id SERIAL PRIMARY KEY,
    brand_id INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    partnumber VARCHAR(50) NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    curr VARCHAR(10) NOT NULL
);

-- Create indexes to optimize queries.
CREATE INDEX idx_offer_brand ON offer(brand_id);
CREATE INDEX idx_offer_partnumber ON offer(partnumber);
CREATE INDEX idx_offer_start_end_date ON offer(start_date, end_date);

-- Insert sample data into the 'offer' table
INSERT INTO offer (brand_id, start_date, end_date, price_list, partnumber, priority, price, curr) VALUES
(1, '2020-06-14 00:00:00', '2020-06-30 23:59:59', 1, '0001002', 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, '0001002', 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, '0001002', 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, '0001002', 1, 38.95, 'EUR');
```

2. **Build the Application**  
   - Ensure you have **Maven** installed.  
   - Run `mvn clean package` to build the project.

3. **Run Locally**  
   - `mvn spring-boot:run`  
   - The service will start on port **8080** by default.
   - **Note:** Make sure to replace the environment variables `${DB_URL}`, `${DB_USER}` and `${DB_PASSWORD}` with the appropriate values for your PostgreSQL setup before running the application in the file **application.yml**.

4. **Run with Docker**  
   - Build the image: `docker build --platform linux/amd64 -t product-pricing-service .`  
   - **Run the container:**
   - If you have PostgreSQL installed locally, replace the Docker run command with the following to use the host’s PostgreSQL instance:
     `docker run -p 8080:8080 -e DB_URL="jdbc:postgresql://host.docker.internal:5432/otherdb" -e DB_USER="another_user" -e DB_PASSWORD="another_password" --platform linux/amd64 product-pricing-service`
   - Otherwise, adjust the environment variables as needed:
     `docker run -p 8080:8080 -e DB_URL="jdbc:postgresql://myhost:5432/otherdb" -e DB_USER="another_user" -e DB_PASSWORD="another_password" --platform linux/amd64 product-pricing-service`
   - **Note:** Make sure to replace the environment variables `DB_URL`, `DB_USER` and `DB_PASSWORD` with the appropriate values for your PostgreSQL setup before running the application.
   
# Postman Collection & Environment Files

This repository includes a Postman collection and an environment file to facilitate API testing.

- **Postman Collection**: Located at `collection/PRODUCT_PRICING.postman_collection.json`
- **Postman Environment**: Located at `collection/PRODUCT_PRICING_ENV.postman_environment.json`

## How to Use

1. Open Postman.
2. Import the collection:
   - Click **File > Import** in Postman.
   - Select the file located at `collection/PRODUCT_PRICING.postman_collection.json`.
3. Import the environment:
   - Click **File > Import** and choose the file at `collection/PRODUCT_PRICING_ENV.postman_environment.json`.
4. Set the imported environment as active.
5. You can now use the collection to test the API endpoints.

Feel free to modify the collection and environment variables as needed for your testing purposes.  

# Swagger Documentation

The API is documented using Swagger/OpenAPI 3.0.

- **Swagger File Location**: The Swagger documentation file is located at `swagger/swagger.yaml`.
- **How to View**: To view and interact with the API documentation, open your web browser and navigate to the [Swagger Editor](https://editor.swagger.io/). Then, open the file located at `swagger/swagger.yaml` in the editor.

## Endpoints Summary

- **POST** `/api/v1/offer` - Create a new offer
- **DELETE** `/api/v1/offer` - Delete all offers
- **DELETE** `/api/v1/offer/{id}` - Delete a specific offer by ID
- **GET** `/api/v1/offer` - Retrieve all offers
- **GET** `/api/v1/offer/{id}` - Retrieve a specific offer by ID
- **GET** `/api/v1/brand/{brandId}/partnumber/{partnumber}/offer` - Retrieve the flattened timetable for a product