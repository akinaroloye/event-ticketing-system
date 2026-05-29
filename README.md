# Event Ticketing System

A Java object-oriented application for managing a live events ticketing platform. The system models a hierarchy of event types (music and performance), handles ticket inventory, supports multiple payment methods, and enforces age restrictions and access control through a role-based permission model.

## Tech stack

- **Java** (no external dependencies)
- Class hierarchy with inheritance and polymorphism
- File-backed inventory via `Stock.txt` and `UserAccounts.txt`

## Running

Compile and run from the project root:

```bash
javac *.java
java MusicEvents
```

Or open in an IDE (Eclipse, IntelliJ) and run `MusicEvents` as the entry point. A pre-compiled `.jar` is included:

```bash
java -jar phem.jar
```

## Architecture

| Class | Role |
|-------|------|
| `LiveEvent` | Abstract base — event ID, name, age restriction, stock, fee, ticket price |
| `MusicEvents` | Extends `LiveEvent` — adds performer count and event type |
| `PerformanceEvents` | Extends `LiveEvent` — adds performance-specific fields |
| `AgeRestrictionCategory` | Enum: `Adults` / `All` |
| `LiveEventCategory` | Enum: `MUSIC` / `PERFORMANCE` |
| `PaymentMethod` | Abstract base for payment strategies |
| `creditCardPayment` | Concrete payment — credit card |
| `paypalPayment` | Concrete payment — PayPal |
| `cart` / `cartItems` | Shopping cart with line items |
| `User` | Base user with login |
| `CustomerPerms` | Customer role — browse and purchase |
| `AdminPerms` | Admin role — manage inventory and events |
| `Login` | Authentication entry point |
| `Receipt` | Purchase confirmation |

## What I learned

Designing the class hierarchy was the most instructive part — deciding what belonged in the abstract base versus the concrete subclasses, and where the payment strategy pattern made sense over a simple conditional. The age restriction and permission system required thinking carefully about what each role should and shouldn't be able to do, which is a pattern that comes up constantly in real software. Reading and writing persistent state from flat text files without a database also highlighted why structured formats matter.
