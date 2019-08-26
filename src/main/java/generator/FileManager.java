package generator;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class FileManager {

    public static void dumpPasswordEntriesToFile(Map<String, PasswordEntry> passwordEntries) throws IOException {
        FileWriter fileWriter = new FileWriter("Passwords.csv");

        for (PasswordEntry passwordEntry : passwordEntries.values()) {
            String row = "\"" + passwordEntry.getLogin() + "\";"
                       + "\"" + passwordEntry.getPassword() + "\";"
                       + "\"" + passwordEntry.getWebsiteName() + "\"";
            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
            String privateData = "secretKey";
            textEncryptor.setPasswordCharArray(row.toCharArray());
            fileWriter.write(row + "\n");
            String myEncryptedText = textEncryptor.encrypt(privateData);
        }
        fileWriter.close();
    }

    public static String[] passwordEntriesToArray(PasswordEntry passwordEntry) {
        return new String[]{
                passwordEntry.getWebsiteName(),
                passwordEntry.getLogin(),
                passwordEntry.getPassword()};
    }

    public static List<PasswordEntry> readPasswordEntriesFromFile() throws IOException {

        CSVParserBuilder parserBuilder = new CSVParserBuilder() // parser builder do parametrów
                .withEscapeChar('\\')
                .withIgnoreLeadingWhiteSpace(true)
                .withQuoteChar('"')
                .withSeparator(';');

        CSVReaderBuilder readerBuilder = new CSVReaderBuilder(new FileReader("Passwords.csv"))
                .withCSVParser(parserBuilder.build());
        CSVReader reader = readerBuilder.build();
        return reader.readAll().stream()
                .map(FileManager::arrayToPasswordEntry)
                .collect(toList());
    }

    private static PasswordEntry arrayToPasswordEntry(String[] row){

        String websiteName = row[0];
        String login = row[1];
        String password = row[2];
        return new PasswordEntry(websiteName, login, password);
    }
}