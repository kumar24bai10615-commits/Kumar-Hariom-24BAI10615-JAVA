import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankManager {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private final String DATA_FILE = "bank_data.dat";

    public void addAccount(BankAccount acc) {
        accounts.put(acc.getAccountNumber(), acc);
        saveData();
    }

    public BankAccount getAccount(String accNum) {
        return accounts.get(accNum);
    }

    public boolean accountExists(String accNum) {
        return accounts.containsKey(accNum);
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                accounts = (Map<String, BankAccount>) obj;
            }
        } catch (Exception e) {
            // File might not exist on first run, which is fine
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving bank data: " + e.getMessage());
        }
    }
}
