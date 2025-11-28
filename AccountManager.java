import java.util.*;

class Account {
    int accountNumber;
    String name;
    double balance;

    public Account(int accNo, String n, double bal) {
        this.accountNumber = accNo;
        this.name = n;
        this.balance = bal;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        }
    }

    public void showDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
    }
}

public class AccountManager {

    Map<Integer, Account> accounts = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public void createAccount() {
        System.out.print("Enter Account Number: ");
        int acc = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String n = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double bal = sc.nextDouble();

        accounts.put(acc, new Account(acc, n, bal));
        System.out.println("Account Created!");
    }

    public void depositMoney() {
        System.out.print("Enter Account Number: ");
        int acc = sc.nextInt();

        if (!accounts.containsKey(acc)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        accounts.get(acc).deposit(amt);
    }

    public void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int acc = sc.nextInt();

        if (!accounts.containsKey(acc)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        accounts.get(acc).withdraw(amt);
    }

    public void checkBalance() {
        System.out.print("Enter Account Number: ");
        int acc = sc.nextInt();

        if (!accounts.containsKey(acc)) {
            System.out.println("Account not found!");
            return;
        }

        accounts.get(acc).showDetails();
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n=== Simple Bank Account Manager ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: createAccount(); break;
                case 2: depositMoney(); break;
                case 3: withdrawMoney(); break;
                case 4: checkBalance(); break;
                case 5: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }

    public static void main(String[] args) {
        new AccountManager().menu();
    }
}

