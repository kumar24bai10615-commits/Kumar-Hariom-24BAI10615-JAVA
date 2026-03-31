import java.io.*;

public class AuditLog {
    private static final String LOG_FILE = "audit_log.txt";

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to audit log: " + e.getMessage());
        }
    }
}
