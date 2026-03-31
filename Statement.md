Project Statement: SmartBank ATM System

1. Problem Statement

Traditional banking systems often lack transparency and real-time tracking for users, while students often struggle to understand the internal logic of secure financial transactions. There is a need for a simulation system that demonstrates secure transaction handling, audit logging for security, and different account behaviors (Savings vs. Current) to educate users on banking logic and object-oriented design.

2. Scope of the Project

SmartBank is a Java-based console application that simulates core banking operations. The scope includes:

Account Management: Creation of Savings and Current accounts with distinct rules (Minimum Balance vs. Overdraft).

Transaction Engine: Secure handling of deposits, withdrawals, and peer-to-peer transfers.

Security: An immutable audit log that records every action to a text file for verification.

Data Persistence: User data is saved locally to ensure balances are maintained between sessions.

3. Target Users

Bank Customers: Individuals wishing to manage their funds digitally and perform transactions.

System Administrators: Staff who need to audit transaction logs for security and monitor system activity.

4. High-Level Features

Polymorphic Withdrawals: Different business rules applied for Savings (Min Balance) and Current (Overdraft) accounts.

Audit Logging: Automatically writes every transaction to audit_log.txt with a timestamp.

Exception Handling: Custom error handling (InsufficientFundsException) to prevent illegal transactions.

Data Persistence: Uses Java Serialization (.dat files) to store account objects securely.
