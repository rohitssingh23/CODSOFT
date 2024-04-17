import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private BankAccount userAccount;
    private Scanner scanner;

    public ATM(BankAccount userAccount) {
        this.userAccount = userAccount;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Welcome to the ATM. Please select an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Change PIN");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    changePIN();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void checkBalance() {
        double balance = userAccount.getBalance();
        System.out.println("Your current balance: ₹" + balance);
    }

    private void deposit() {
        System.out.print("Enter the amount to deposit: ₹");
        double amount = getUserAmount();

        if (amount > 0) {
            System.out.print("Enter your PIN to deposit: ");
            int pin = scanner.nextInt();
            if (verifyPIN(pin)) {
                userAccount.deposit(amount);
                System.out.println("₹" + amount + " deposited successfully.");
                displayUpdatedBalance();
            } else {
                System.out.println("Incorrect PIN. Deposit failed.");
            }
        } else {
            System.out.println("Invalid amount. Deposit failed.");
        }
    }

    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ₹");
        double amount = getUserAmount();

        if (amount > 0 && userAccount.getBalance() >= amount) {
            System.out.print("Enter your PIN to withdraw: ");
            int pin = scanner.nextInt();
            if (verifyPIN(pin)) {
                userAccount.withdraw(amount);
                System.out.println("₹" + amount + " withdrawn successfully.");
                displayUpdatedBalance();
            } else {
                System.out.println("Incorrect PIN. Withdrawal failed.");
            }
        } else if (amount > 0) {
            System.out.println("Insufficient funds. Withdrawal failed.");
        } else {
            System.out.println("Invalid amount. Withdrawal failed.");
        }
    }

    private void changePIN() {
        System.out.print("Enter your current PIN: ");
        int currentPIN = scanner.nextInt();
        System.out.print("Enter your new PIN: ");
        int newPIN = scanner.nextInt();

        if (userAccount.changePIN(currentPIN, newPIN)) {
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("Incorrect current PIN. PIN change failed.");
        }
    }

    private void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : userAccount.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private double getUserAmount() {
        while (true) {
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid amount.");
                scanner.next();
            }
            double amount = scanner.nextDouble();
            if (amount >= 0) {
                return amount;
            } else {
                System.out.println("Invalid amount. Please enter a non-negative value.");
            }
        }
    }

    private boolean verifyPIN(int pin) {
        return userAccount.getPin() == pin;
    }

    private void displayUpdatedBalance() {
        double balance = userAccount.getBalance();
        System.out.println("Updated Balance: ₹" + balance);
    }

    public static void main(String[] args) {

        BankAccount userAccount = new BankAccount(1000.0, 1234);

        ATM atm = new ATM(userAccount);

        atm.start();
    }

    public static class BankAccount {
        private double balance;
        private int pin;
        private List<String> transactionHistory;

        public BankAccount(double initialBalance, int pin) {
            this.balance = initialBalance;
            this.pin = pin;
            this.transactionHistory = new ArrayList<>();
        }

        public double getBalance() {
            return balance;
        }

        public int getPin() {
            return pin;
        }

        public void deposit(double amount) {
            balance += amount;
            addTransaction("Deposit: +₹" + amount);
        }

        public void withdraw(double amount) {
            balance -= amount;
            addTransaction("Withdrawal: -₹" + amount);
        }

        public boolean changePIN(int currentPIN, int newPIN) {
            if (currentPIN == pin) {
                pin = newPIN;
                addTransaction("PIN changed");
                return true;
            }
            return false;
        }

        public List<String> getTransactionHistory() {
            return transactionHistory;
        }

        private void addTransaction(String transaction) {
            transactionHistory.add(transaction);
        }
    }
}
