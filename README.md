# Marten Kustav Klais - Bank Teldrassil

## Bank Teldrassil
Java application designed to process and reconstruct customer financial transaction from raw data files into a consistent state.
Application parses transaction logs, validates transaction integrity, and restores accurate customer balance sheets.

---

## Features
- Transaction Processing: Reads frin stryctyred CSV fukes ti oricess nukuibs if trabsactuib recirds efficiently.
- Compliance and Validation: Ensures all transactions comply with busineess rules, such as verifying user statuses, transaction types, and payment methods.
- Security Checks: Includes validation of card authenticity and transaction integrity based on BIN mappings and user-specific rules.
- Output Generation: Produces detailed reports on updated user balances and transaction outcomes, aiding in transparency and auditing.

## Main Components
- User Management: Handles user data, including balance checks and status management.
- Transaction Validation: Filters and validates transactions based on predefined rules and conditions.
- BIN Mapping Verification: Validates debit and credit card transactions against BIN ranges and types.
- Report Generation: Outputs the results in CSV format, detailing every processed transaction and updated user balances.

### Project
The project is done without any 3rd party libraries, only utilized what is available in the JDK.
---

## How to run
Run application with providing paths to INPUT files (Users, Transactions, BinMappings) and OUTPUT files (Balances, Events) in the following order:
Users, Transactions, BinMappings, Balances, Events.

File location can be absolute path such as: `YourDrive:/location/to/input/file.csv` OR you can use provided test files and pass the path as `src/main/resources/test-data/small/users.csv`

## File formats
Each input file is expected to have first row as header OR file must have data columns in following input formats:

| File         | Header                                                                        |
|--------------|-------------------------------------------------------------------------------|
| Users        | USER_ID,USERNAME,BALANCE,COUNTRY,FROZEN,DEPOSIT_MIN,WITHDRAW_MIN,WITHDRAW_MAX |
| Transactions | TRANSACTION_ID,USER_ID,TYPE,AMOUNT,METHOD,ACCOUNT_NUMBER                      |
| BinMappings  | NAME,RANGE_FROM,RANGE_TO,TYPE,COUNTRY                                         |

### File header translations
#### Users
- `user_id` - used in transactions to refer to the user.
- `username` - not used for any validations; a readable name may help with debugging.
- `balance` - user's current balance amount.
- `country` - **two**-letter country code, ISO 3166-1 alpha-2.
- `frozen` - 0 for an active user, 1 for frozen.
- `deposit_min` - amount, minimum allowed deposit, inclusive.
- `deposit_max` - amount, maximum allowed deposit, inclusive.
- `withdraw_min` - amount, minimum allowed withdrawal, inclusive.
- `withdraw_max` - amount, maximum allowed withdrawal, inclusive.

#### Transactions
- `transaction_id` - ID of the transaction.
- `user_id` - ID of the user.
- `type` - transaction type (allowed values are DEPOSIT or WITHDRAW).
- `amount` - amount.
- `method` - payment method (allowed values are CARD or TRANSFER).
- `account_number` - depends on method; card number for method=CARD, or IBAN for method=TRANSFER.

#### Bin Mapping
- `name` - issuing bank name.
- `range_from` - the lowest possible card number (first 10 digits of card number) that would be identified within this card range, inclusive.
- `range_to` - the highest possible card number (first 10 digits of card number) that would be identified within this card range, inclusive.
- `type` - **DC** for debit and **CC** for credit cards.
- `country` - **three**-letter country code, ISO 3166-1 alpha-3.

