# Midas
Project repo for the JPMC Advanced Software Engineering Forage program


Midas Project repo for the JPMC Advanced Software Engineering Forage program designed to develop practical skills and gain a hiring advantage with online courses that reflect the work done on the job.
This is a 5 step process to setup a modern web application.

# Step 1
🛠️ Step 1: Environment Initialization & Project Scaffold
Kicked off development on a modular backend system designed to process financial transactions with high reliability and scalability. The system—referred to as Midas Core—serves as the central processing unit for incoming transaction data, coordinating with external services for validation, persistence, and downstream incentives.

Project Context:
- Midas Core is part of a larger distributed architecture
- It interfaces with a message queue (Kafka), a relational database (SQL), and a REST API
- The system is built using Spring Boot for streamlined dependency management and service integration


Setup Tasks Completed:
- Forked and cloned the project scaffold from the team repository
- Opened the project in a Spring-compatible IDE and configured Java 17 runtime
- Reviewed the scaffold to understand existing class structure and integration points
- Added and pinned essential dependencies for web, data, messaging, and testing:
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-kafka
- h2 (in-memory DB for dev)
- spring-boot-starter-test
- spring-kafka-test
- testcontainers-kafka

Validation:
- Ran initial test suite (TaskOneTests) to confirm environment setup and verify that core dependencies are correctly wired

# Step 2
📡 Step 2: Kafka Listener Integration
Integrated a message queue between the frontend and backend to enable asynchronous, decoupled communication. Implemented a Kafka-based listener within the backend to receive incoming transaction data from a designated topic. This setup lays the foundation for scalable, resilient processing of financial events.

Architectural Benefits:
- Decoupling — Frontend and backend operate independently
- Asynchronous flow — Backend can process bursts of activity without blocking user actions
- Scalability — Supports multiple producers and consumers for horizontal scaling

Implementation Highlights:
- Configured a Kafka consumer using Spring Boot annotations and application-level properties
- Created a listener class that subscribes to a topic and deserializes messages into domain objects
- Verified integration using an embedded Kafka broker for local testing
- Captured initial transaction payloads via debugger for validation



# Step 3
🗄️ Step 3: Database Integration & Transaction Validation
Integrated a relational database into the backend to persist financial transactions and enforce validation logic. Leveraged Spring Data JPA with an H2 in-memory database for local development, ensuring strong consistency guarantees and simplified setup. This layer forms the backbone of Midas Core’s data integrity.

Design Rationale:
- Chose a SQL-based solution for its robustness and transactional safety
- Used H2 for development due to its seamless Spring Boot integration
- Abstracted persistence logic via JPA to allow easy migration to a production-grade database later

Validation Logic Implemented:
- Verified sender and recipient identities
- Ensured sender’s balance covers the transaction amount
- Recorded valid transactions and updated account balances accordingly
- Discarded invalid transactions without modifying the database

Entity Relationships:
- Created a new TransactionRecord entity to persist transactions
- Established many-to-one relationships between transactions and user entities
- Ensured referential integrity and balance updates via JPA mappings
  
Verification:
- Ran TaskThreeTests to confirm correct processing
- Used debugger to inspect post-transaction state and validate user balances


