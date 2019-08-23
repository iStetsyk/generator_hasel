package generator;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FileManager {

    public static void dumpPasswordEntriesToFile(List<PasswordEntry> passwordEntries) throws IOException {
        CSVWriter writer = new CSVWriter(
                new FileWriter("Passwords.csv"),
                ';',
                '"',
                '\\',
                "\n");

        writer.writeAll(passwordEntries.stream()
                .map(FileManager::passwordEntriesToArray)
                .collect(toList()));
        writer.close();
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