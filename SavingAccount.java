public class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accNum, String pin, String name, double bal) {
        super(accNum, pin, name, bal);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (balance - amount < MIN_BALANCE) {
            throw new InsufficientFundsException("Withdrawal failed: Minimum balance of $" + MIN_BALANCE + " must be maintained.");
        }
        balance -= amount;
    }
}
