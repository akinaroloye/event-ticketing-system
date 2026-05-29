# Event Ticketing System

A Java OOP application for a live events ticketing platform. Covers a class hierarchy for different event types, ticket inventory management, multiple payment methods, and age restrictions with role-based access control.

## Tech stack

- **Java** (no external dependencies)
- Flat-file persistence via `Stock.txt` and `UserAccounts.txt`

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

Working out what belongs in the abstract base versus the concrete subclasses was the most instructive part, and deciding where the payment strategy pattern was actually worth using over a simple switch. The permission model required thinking carefully about what each role should and shouldn't be able to do, which comes up constantly in real software. Flat file persistence without a database is manageable at small scale but makes it obvious pretty quickly why structured formats exist.
