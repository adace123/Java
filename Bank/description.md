* Simulates a bank with customers and checking/savings accounts.
* Bank class stores list of customers and accounts that are updated on creation/deletion through singleton instance.
* Checking and savings account classes extend abstract BankAccount class' withdraw and deposit methods and override them with thread-safe modification of customer's balance.
* The checking account and savings account classes have different withdraw methods:
    1. The savings account class' withdraw method works normally - if the customer has sufficient funds then the withdrawal can proceed.
    2. The checking account class' withdraw method checks if the customer also has a savings account once the balance reaches 0 and if so withdraws from the savings account until it too is empty.
