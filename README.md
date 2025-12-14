# ⚙️ GearOps: Digital Commerce Platform Microservices

## 🌟 1. Project Summary

**GearOps** establishes a robust, multi-role **Digital Commerce Platform** built on a **Microservices Architecture**. It is engineered to handle user identity, detailed inventory management, and complex billing processes, providing distinct operational environments for Normal Users, Shop Owners, and Administrators.

**Goal:** To serve as a comprehensive learning platform for modern Spring Boot development, **Domain-Driven Design (DDD)**, and distributed system patterns using a decoupled, scalable approach.

## 🏛️ 2. Architectural Overview

The GearOps system employs the **API Gateway** pattern for unified access and utilizes **Service Discovery** to maintain dynamic service connections. Communication uses a hybrid of synchronous (REST) and eventual consistent (Asynchronous/Messaging) patterns.

### A. Core Tech Stack

| Component | Technology | Rationale |
| :--- | :--- | :--- |
| **Application Name** | **GearOps** | Professional name signifying operational management and precision. |
| **Backend Framework** | **Spring Boot 3.x (Java/Kotlin)** | Industry standard for enterprise microservices. |
| **Build Tool** | **Gradle (Kotlin DSL)** | Modern, flexible, and type-safe configuration for multi-module projects. |
| **Persistence** | **Spring Data JPA / Hibernate** | Focus on rapid development and Domain-Driven Design principles. |
| **Database** | **PostgreSQL** | Free, powerful, and easily managed for the **Database Per Service** model. |
| **Service Discovery** | **Spring Cloud Netflix Eureka** | Simplest, native, and highly integrated solution for Spring Cloud environments. |
| **API Gateway** | **Spring Cloud Gateway** | Central request routing, security enforcement, and client-side load balancing. |
| **Asynchronous Comm.** | **RabbitMQ (Future Plan)** | Planned for reliable event handling and background processing (e.g., Sagas). |
| **Security** | **Spring Security & JWT** | Implements stateless, token-based authentication. |
| **Frontend (Planned)**| **Angular** | Robust framework for complex, multi-role user interfaces. |

-----

## 💻 3. Service Roles and Responsibilities

Each service in **GearOps** is an independent application with specific responsibilities and an assigned architectural role.

| Service Name | Architectural Role | Responsibilities |
| :--- | :--- | :--- |
| **`GearOps-Discovery-Server`** | **Discovery Server** | Central registry for registering and tracking the network location of all GearOps microservices. |
| **`GearOps-Gateway`** | **Gateway & Discovery Client** | The single entry point for all client requests. Routes to internal services and enforces security policies (JWT validation). |
| **`GearOps-Auth-Service`** | **Discovery Client** | Manages user login, registration, and issues JWT tokens upon successful authentication. |
| **`GearOps-User-Service`** | **Discovery Client** | Stores and manages the current state of user profile data, roles, and address information. |
| **`GearOps-Inventory-Service`** | **Discovery Client** | Manages the full product catalog, category structure, and tracks real-time stock levels. |
| **`GearOps-Billing-Service`** | **Discovery Client** | Handles `SHOP_ORDER` creation, invoicing, and manages the flow of distributed transactions (Sagas) related to order fulfillment. |
| **`GearOps-Admin-Service`** | **Discovery Client** | Central control panel for system configuration, audit logging, and elevated management tasks (e.g., user role assignment). |

-----

## 💾 4. Persistence Strategy & Tables

The **Database Per Service** pattern is strictly enforced across all GearOps services.

### A. Core Tables by Service (JPA Entities)

| Service | Key Entities / Tables |
| :--- | :--- |
| **Auth Service** | `AuthUser` (Credentials), `UserRole`, `AuthUserRoles` (Join Table) |
| **User Service** | `UserProfile`, `UserAddress` |
| **Inventory Service** | `Product`, `Category`, `Inventory`, `ProductAttribute` |
| **Billing Service** | `ShopOrder`, `OrderLineItem`, `Invoice`, `PaymentTransaction` |
| **Admin Service** | `SystemLog`, `ConfigParameter`, `OwnerSettings` |

## 🔄 5. Communication Patterns

| Pattern | Usage | Notes |
| :--- | :--- | :--- |
| **Synchronous** | REST/HTTP (via Spring `WebClient`) | Used for **queries** where an immediate response is required (e.g., Gateway validating a token via Auth Service). |
| **Asynchronous** | RabbitMQ (Planned) | Used for **commands/events** where reliable background processing is needed (e.g., Inventory updates). |

-----

## 🚀 6. Getting Started (Local Setup)

### A. Prerequisites (Manual Setup)

  * **Java 17+**
  * **Gradle** (Managed by wrapper)
  * **PostgreSQL Installed:** A local PostgreSQL server must be running (e.g., on default port 5432).
  * **RabbitMQ Installed:** A local RabbitMQ broker must be running (e.g., on default ports).

### B. Running Services

1.  **Clone the repository:**
    ```bash
    git clone [YOUR_REPO_URL]
    ```
2.  **Build all services:**
    ```bash
    ./gradlew build
    ```
3.  **Run Order:** The run order is crucial for service discovery:
      * **1. Infrastructure:** Start the `GearOps-Discovery-Server`.
      * **2. Entry Point:** Start the `GearOps-Gateway`.
      * **3. Clients:** Start all other client services (Auth, User, Inventory, etc.).

### C. Key Access Points

| Component | Local URL | Notes |
| :--- | :--- | :--- |
| **Eureka Dashboard** | `http://localhost:8761` | Check service registration status here. |
| **API Gateway** | `http://localhost:8080` | All client requests go through this port. |

-----
