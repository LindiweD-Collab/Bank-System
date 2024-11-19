import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type; 
    private double amount;
    private String date;

    public Transaction(String type, double amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s of $%.2f on %s", type, amount, date);
    }
}

class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private List<Transaction> transactionHistory;
    private boolean isPremium;

    public BankAccount(String accountNumber, String ownerName, boolean isPremium) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.isPremium = isPremium;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount, getCurrentDate()));
            System.out.printf("Deposited $%.2f. New balance: $%.2f.%n", amount, balance);
        } else {
            System.out.println("Error: Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Error: Insufficient funds for this withdrawal.");
        } else if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount, getCurrentDate()));
            System.out.printf("Withdrew $%.2f. New balance: $%.2f.%n", amount, balance);
        }
    }

    public String checkBalance() {
        return String.format("Account balance: $%.2f", balance);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void applyInterest() {
        if (isPremium) {
            double interest = balance * 0.05; 
            deposit(interest);
            System.out.printf("Interest of $%.2f applied. New balance: $%.2f.%n", interest, balance);
        }
    }

    // Mock method to simulate getting the current date
    private String getCurrentDate() {
        return "2024-11-18"; 
    }
}

class Customer {
    private String name;
    private BankAccount account;

    public Customer(String name) {
        this.name = name;
    }

    public void createAccount(String accountNumber, boolean isPremium) {
        if (account == null) {
            account = new BankAccount(accountNumber, name, isPremium);
            System.out.printf("Account created for %s. Account Number: %s.%n", name, accountNumber);
        } else {
            System.out.println("Error: Account already exists.");
        }
    }

    public BankAccount getAccount() {
        return account;
    }
}

public class SimpleBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Simple Banking System!");

        
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName);

        
        System.out.print("Choose account type (1 for Basic, 2 for Premium): ");
        String accountTypeChoice = scanner.nextLine();
        boolean isPremium = accountTypeChoice.equals("2");

        
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        customer.createAccount(accountNumber, isPremium);

        
        if (isPremium) {
            customer.getAccount().applyInterest();
        }

        
        while (true) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    customer.getAccount().deposit(depositAmount);
                    break;
                case "2":
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    customer.getAccount().withdraw(withdrawAmount);
                    break;
                case "3":
                    System.out.println(customer.getAccount().checkBalance());
                    break;
                case "4":
                    System.out.println("Transaction History:");
                    for (Transaction transaction : customer.getAccount().getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                    break;
                case "5":
                    System.out.println("Thank you for using the Simple Banking System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


