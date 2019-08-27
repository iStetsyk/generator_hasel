package generator;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class FileManager {

    public static void dumpPasswordEntriesToFile(Map<String, PasswordEntry> passwordEntries) throws IOException {
        FileWriter fileWriter = new FileWriter("Passwords.csv");
        for (PasswordEntry passwordEntry : passwordEntries.values()) {
            String row = "\"" + passwordEntry.getWebsiteName() + "\";"
                       + "\"" + passwordEntry.getLogin() + "\";"
                       + "\"" + passwordEntry.getPassword() + "\"";

            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword("password");
            String encryptedData = textEncryptor.encrypt(row);
            fileWriter.write(encryptedData + "\n");
        }
        fileWriter.close();
    }

    public static String[] passwordEntriesToArray(PasswordEntry passwordEntry) {
        return new String[]{
                passwordEntry.getWebsiteName(),
                passwordEntry.getLogin(),
                passwordEntry.getPassword()};
    }


    public static List<PasswordEntry> readPasswordEntriesFromFile(DescriptionFileReader myreader) throws IOException {


        CSVParserBuilder parserBuilder = new CSVParserBuilder() // parser builder do parametrów
                .withEscapeChar('\\')
                .withIgnoreLeadingWhiteSpace(true)
                .withQuoteChar('"')
                .withSeparator(';');

        CSVReaderBuilder readerBuilder = new CSVReaderBuilder(myreader).withCSVParser(parserBuilder.build());
        CSVReader reader = readerBuilder.build();
        return reader.readAll().stream()
                .map(FileManager::arrayToPasswordEntry)
                .filter(Objects::nonNull)
                .collect(toList());
    }


    private static PasswordEntry arrayToPasswordEntry(String[] row) {

        if (row.length != 3) {
            return null;
        }

        String websiteName = row[0];
        String login = row[1];
        String password = row[2];

        return new PasswordEntry(websiteName, login, password);
    }
}