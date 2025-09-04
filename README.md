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

# Step 4
🎁 Step 4: Incentive API Integration
Integrated an external Incentive API into Midas Core to enrich transaction processing with dynamic reward logic. This step demonstrates modular system design by decoupling incentive computation from core transaction validation, allowing independent development and deployment of each component.

Design Philosophy
- Separation of Concerns: Incentive logic is abstracted into a standalone service, enabling teams to iterate independently without cross-impact.
- Contract-Driven Development: The REST API serves as a stable interface between Midas Core and the Incentive service, ensuring compatibility as long as the contract remains unchanged.
- Client-Server Architecture: A RESTful POST request/response pattern was chosen for its simplicity, ubiquity, and alignment with stateless incentive evaluation.

Implementation Highlights
- Integrated a RestTemplate client to communicate with the Incentive API running on http://localhost:8080/incentive
- Serialized and posted validated Transaction objects to the API endpoint
- Received and parsed an incentive object containing a reward amount (>=0)
- Augmented the recipient’s balance with the incentive amount (without deducting it from the sender)
- Extended the TransactionRecord entity to persist the incentive alongside the transaction amount

Verification
- Ran TaskFourTests to validate correct incentive integration and balance updates
- Used debugger to inspect post-processing state of user accounts
- Verified that the “Wilbur” user received the correct incentive-adjusted balance

# Step 5
💰 Step 5: Balance Query Endpoint Integration
Exposed a RESTful endpoint within Midas Core to allow users to query their current account balances. This feature enhances transparency and usability by surfacing financial data directly from the backend, enabling informed decision-making and reducing the risk of user error due to unseen account states.

Architectural Decision:
- Although surfacing balance data could have warranted a separate microservice, the simplicity of the feature and ease of integration justified embedding it directly into Midas Core.
- This decision balances architectural purity with pragmatic development—future growth of balance-related features may prompt a refactor into a dedicated component.

Implementation Highlights:
- Added a new REST controller () to Midas Core
- Exposed a  endpoint that accepts a  as a request parameter
- Queried transaction records to compute the user’s current balance
- Returned a serialized  object in JSON format
- Defaulted to a balance of 0 for non-existent users
- Configured the application to run on port  to support the new endpoint

Verification:
- Ran  to confirm correct behavior and endpoint exposure
- Validated that balances were accurately computed and returned
- Ensured compatibility with the Incentive API and Kafka listener running in parallel
- Used debugger and test logs to inspect edge cases and confirm zero-balance fallback

this is a repo with the JPM completed version:
https://github.com/vagabond-systems/forage-midas-complete