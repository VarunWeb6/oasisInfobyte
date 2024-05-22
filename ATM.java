import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

class BankAccount {
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
        transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        } else {
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    withdraw();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    showTransactionHistory();
                    break;
                case 6:
                    exitATM();
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Welcome to the ATM ===");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer Money");
        System.out.println("5. Transaction History");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        return scanner.nextInt();
    }

    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double withdrawAmount = scanner.nextDouble();
        if (account.withdraw(withdrawAmount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);
        System.out.println("Deposit successful.");
    }

    private void checkBalance() {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    private void transfer() {
        System.out.print("Enter the amount to transfer: ");
        double transferAmount = scanner.nextDouble();
        if (account.withdraw(transferAmount)) {
            System.out.print("Enter the recipient's account number: ");
            int recipientAccountNumber = scanner.nextInt();
            // Here you would implement the logic to transfer the amount to the recipient's account.
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    private void showTransactionHistory() {
        List<Transaction> transactions = account.getTransactionHistory();
        System.out.println("=== Transaction History ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private void exitATM() {
        System.out.println("Thank you for using the ATM.");
        scanner.close();
        System.exit(0);
    }
}

public class Task {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        ATM atm = new ATM(account);
        atm.start();
    }
}
