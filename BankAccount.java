import java.io.Serializable;

public abstract class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String accountNumber;
    private final String pin;
    private final String holderName;
    protected double balance;

    public BankAccount(String accNum, String pin, String name, double bal) {
        this.accountNumber = accNum;
        this.pin = pin;
        this.holderName = name;
        this.balance = bal;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    // Abstract method: Child classes must define their own withdrawal rules
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", holderName, accountNumber, balance);
    }
}
