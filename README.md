# BeholdTracker

BeholdTracker is a financial application engineered to track investment growth on the B3 (Brazilian Stock Exchange). It evaluates portfolio performance by comparing real net returns against user-defined growth targets and macroeconomic benchmarks, specifically accumulated IPCA inflation.

The system is architected to maximize operational clarity, maintainability, and decision-making utility for investors seeking accurate, inflation-adjusted portfolio tracking.

---

## Key Features

- **Liquid Balance & Account Management**: Track cash reserves across multiple brokerage accounts with invariants preventing overdrafts and negative balances.
- **Asset & Transaction Tracking**: Register BUY and SELL transactions for equities, tracking unit costs, transaction fees, and target annual profit rates ($p.a.$).
- **Benchmark & Inflation Adjustment**:
  - Incorporates accumulated IPCA rates (via Central Bank of Brazil / BACEN API or local simulation) to compute inflation-adjusted acquisition costs ($V_{\text{inflated}}$).
  - Evaluates performance against expected target benchmarks over elapsed holding time.
- **Resilient Market Data Providers**:
  - Pluggable market price feeds (Mock and Brazilian Exchange integrations via `AssetPriceProvider`).
  - Stale-while-revalidate caching mechanism to prevent external rate-limiting bottlenecks and minimize API consumption.
- **Clean Architecture Monolith**: Strict separation of concerns across Domain, Use Case, Adapter, and Infrastructure layers.
- **Integrated Responsive Interface**: Embedded single-page dashboard with a dark theme optimized for dense financial data visualization.

---

## Technical Stack

- **Runtime**: Java 17 (LTS) or higher
- **Framework**: Spring Boot 3.3.x (Spring Web, Spring Data JPA, Spring Validation)
- **Database**: PostgreSQL
- **Build Tool**: Apache Maven
- **Architecture**: Clean Architecture (Ports and Adapters)
- **Frontend**: Vanilla JavaScript (ES6+), HTML5, CSS3

---

## Project Structure

```
com.ladeschamps.beholdtracker
├── domain          # Core enterprise entities and business invariants
├── usecase         # Application interactors, business rules, and port interfaces
├── adapter         # Inbound/outbound adapters (REST controllers, JPA repositories, external gateways)
└── infrastructure  # Framework configuration, security, caching, and bean wiring
```

---

## Prerequisites

To build and run this application efficiently, ensure the following tools are installed:

- **JDK 17** or later
- **Maven 3.8+** (or use the IDE Maven integration)
- **PostgreSQL 14+** (running instance on `localhost:5432`)

---

## Configuration

Application settings and fallback parameters are configured in `src/main/resources/application.yml`. Key environment variables:

| Variable                     | Description                     | Default                                            |
|------------------------------|---------------------------------|----------------------------------------------------|
| `SPRING_DATASOURCE_URL`      | PostgreSQL JDBC connection URL  | `jdbc:postgresql://localhost:5432/beholdtracker`   |
| `SPRING_DATASOURCE_USERNAME` | Database username               | `postgres`                                         |
| `SPRING_DATASOURCE_PASSWORD` | Database password               | `postgres`                                         |

### Default Parameterization

Default transaction fees and target profit expectations can be tuned via configuration:
- `beholdtracker.defaults.buy-operational-cost`: Standard operational cost for BUY orders (default: `5.00` BRL).
- `beholdtracker.defaults.sell-operational-cost`: Standard operational cost for SELL orders (default: `7.00` BRL).
- `beholdtracker.defaults.expected-profit-rate-aa`: Benchmark expected profit rate per annum (default: `10.0` %).
- `beholdtracker.price-provider.active`: Active price provider engine (`MOCK` or `BRAZILIAN_EXCHANGE_API`).
- `beholdtracker.cache.prices.ttl-minutes`: In-memory price cache TTL (default: `15` min).

---

## Getting Started

### 1. Clone & Navigate
```bash
git clone https://github.com/ladeschamps/BeholdTracker.git
cd BeholdTracker
```

### 2. Build the Application
Compile the source code and package the artifact:
```bash
mvn clean package
```

### 3. Run Tests
Execute the unit and integration test suite:
```bash
mvn test
```

### 4. Run the Application
Launch the Spring Boot application:
```bash
mvn spring-boot:run
```

Once started, access the application at:
```
http://localhost:8080
```

---

## Roadmap

- [x] **Milestone 1**: Project Setup & Clean Architecture Persistence Foundation
- [x] **Milestone 2**: Accounts Domain & Liquid Cash Flow Operations
- [ ] **Milestone 3**: Asset Operations, Configuration Properties & Settings REST API
- [ ] **Milestone 4**: External Market & Inflation Providers with Resilient Caching
- [ ] **Milestone 5**: Core Tracking Calculator Engine & Consolidated Dashboard API
- [ ] **Milestone 6**: Embedded Dark Theme UI Frontend
