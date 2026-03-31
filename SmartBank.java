import java.util.Scanner;

public class SmartBank {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BankManager bankManager = new BankManager();
    private static BankAccount currentAccount = null;

    public static void main(String[] args) {
        bankManager.loadData();
        System.out.println("==========================================");
        System.out.println("       Welcome to SMARTBANK ATM           ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            if (currentAccount == null) {
                running = showMainMenu();
            } else {
                showAccountMenu();
            }
        }
        System.out.println("Thank you for using SmartBank.");
    }

    private static boolean showMainMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Open New Account");
        System.out.println("3. Exit");
        System.out.print("Select Option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> login();
            case "2" -> openAccount();
            case "3" -> { return false; }
            default -> System.out.println("Invalid option.");
        }
        return true;
    }

    private static void login() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        BankAccount acc = bankManager.getAccount(accNum);
        if (acc != null && acc.validatePin(pin)) {
            currentAccount = acc;
            System.out.println("Login Successful! Welcome " + acc.getHolderName());
            AuditLog.log("LOGIN SUCCESS: " + accNum);
        } else {
            System.out.println("Invalid Credentials.");
            AuditLog.log("LOGIN FAILED: " + accNum);
        }
    }

    private static void openAccount() {
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();
        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.nextLine();
        
        // Generate simple account number
        String accNum = String.valueOf((long)(Math.random() * 1000000));
        
        System.out.println("Select Account Type:");
        System.out.println("1. Savings (Min Balance $500)");
        System.out.println("2. Current (Overdraft Allowed)");
        String type = scanner.nextLine();

        BankAccount newAcc;
        if (type.equals("1")) {
            newAcc = new SavingsAccount(accNum, pin, name, 1000.0); // Start with $1000
        } else {
            newAcc = new CurrentAccount(accNum, pin, name, 1000.0);
        }

        bankManager.addAccount(newAcc);
        System.out.println("Account Created! Your Account Number is: " + accNum);
        System.out.println("Please Login to continue.");
        AuditLog.log("NEW ACCOUNT: " + accNum);
    }

    private static void showAccountMenu() {
        System.out.println("\n--- ATM MENU (" + currentAccount.getBalance() + ") ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Logout");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> System.out.println("Current Balance: $" + currentAccount.getBalance());
            case "2" -> {
                System.out.print("Enter Amount to Deposit: ");
                double amt = Double.parseDouble(scanner.nextLine());
                currentAccount.deposit(amt);
                System.out.println("Deposited Successfully.");
                bankManager.saveData(); // Save changes
                AuditLog.log("DEPOSIT: " + currentAccount.getAccountNumber() + " - $" + amt);
            }
            case "3" -> {
                System.out.print("Enter Amount to Withdraw: ");
                double amt = Double.parseDouble(scanner.nextLine());
                try {
                    currentAccount.withdraw(amt);
                    System.out.println("Please collect your cash.");
                    bankManager.saveData(); // Save changes
                    AuditLog.log("WITHDRAW: " + currentAccount.getAccountNumber() + " - $" + amt);
                } catch (InsufficientFundsException e) {
                    System.out.println("Error: " + e.getMessage());
                    AuditLog.log("WITHDRAW FAILED: " + currentAccount.getAccountNumber() + " - " + e.getMessage());
                }
            }
            case "4" -> {
                System.out.print("Enter Target Account Number: ");
                String targetId = scanner.nextLine();
                BankAccount target = bankManager.getAccount(targetId);
                if (target == null) {
                    System.out.println("Target account not found.");
                    break;
                }
                System.out.print("Enter Amount: ");
                double amt = Double.parseDouble(scanner.nextLine());
                try {
                    currentAccount.withdraw(amt); // Deduct from sender
                    target.deposit(amt);          // Add to receiver
                    System.out.println("Transfer Successful!");
                    bankManager.saveData();
                    AuditLog.log("TRANSFER: " + currentAccount.getAccountNumber() + " -> " + targetId + " $" + amt);
                } catch (InsufficientFundsException e) {
                    System.out.println("Transfer Failed: " + e.getMessage());
                }
            }
            case "5" -> {
                currentAccount = null;
                System.out.println("Logged out.");
            }
            default -> System.out.println("Invalid option.");
        }
    }
}
