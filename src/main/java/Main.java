import com.playtech.bankteldrassil.model.Event;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.processor.TransactionProcessor;
import com.playtech.bankteldrassil.service.bins.BinMappingService;
import com.playtech.bankteldrassil.service.bins.BinMappingServiceImpl;
import com.playtech.bankteldrassil.service.event.EventServiceImpl;
import com.playtech.bankteldrassil.service.transaction.TransactionService;
import com.playtech.bankteldrassil.service.transaction.TransactionServiceImpl;
import com.playtech.bankteldrassil.service.user.UserService;
import com.playtech.bankteldrassil.service.user.UserServiceImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        EventServiceImpl eventService = new EventServiceImpl();

        UserService userService = new UserServiceImpl(Paths.get(args[0]));
        TransactionService transactionService = new TransactionServiceImpl(Paths.get(args[1]));
        BinMappingService binMappingService = new BinMappingServiceImpl(Paths.get(args[2]));

        TransactionProcessor transactionProcessor = new TransactionProcessor(userService, transactionService, eventService, binMappingService);
        transactionProcessor.processTransactions();

        var events = eventService.findAllEvents();
        var users = userService.findAllUsers();

        Main.writeBalances(Paths.get(args[3]), users);
        Main.writeEvents(Paths.get(args[4]), events);
    }

    private static void writeBalances(final Path filePath, final List<User> users) throws IOException {
        try (final FileWriter writer = new FileWriter(filePath.toFile(), false)) {
            writer.append("USER_ID,BALANCE\n");
            for (final var user : users) {
                writer.append(user.getUserId())
                        .append(",")
                        .append(String.format("%.2f", user.getBalance()))
                        .append("\n");
            }
        }
    }

    private static void writeEvents(final Path filePath, final List<Event> events) throws IOException {
        try (final FileWriter writer = new FileWriter(filePath.toFile(), false)) {
            writer.append("TRANSACTION_ID,STATUS,MESSAGE\n");
            for (final var event : events) {
                writer.append(event.getTransactionId())
                        .append(",")
                        .append(event.getStatus().name())
                        .append(",")
                        .append(event.getMessage())
                        .append("\n");
            }
        }
    }
}