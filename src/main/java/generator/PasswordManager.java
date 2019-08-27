package generator;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PasswordManager {

    private static Map<String, PasswordEntry> passwordMap = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("Proszę wpisać hasło : ");
        String mainPassword = scanner.nextLine();

        DescriptionFileReader descriptionFileReader = new DescriptionFileReader("Passwords.csv", mainPassword);

        List<PasswordEntry> passwordEntries = FileManager.readPasswordEntriesFromFile(descriptionFileReader);

        for (PasswordEntry passwordEntry : passwordEntries) {
            passwordMap.put(passwordEntry.getWebsiteName(), passwordEntry);
        }

        while (true) {
            System.out.println("Wciśnij [1] jeżeli chcesz wyszukać hasło do witryny\n" +
                            "\n" +
                            "Wciśnij [2] jeżeli chcesz wprowadzić nową witrynę \n" +
                            "\n" +
                            "Wciśnij [3] jeżeli chcesz skopiować hasło do pamięci podręcznej \n" +
                            "\n" +
                            "Wciśnij [4] jeżeli chcesz wyświetlić wszystkie serwisy \n" +
                            "\n" +
                            "Wciśnij [0] jeżeli chcesz opuścić manager");
            String userInput = scanner.nextLine();


            if (userInput.equals("1")) {
                System.out.println("Proszę wpisać nazwę serwisu. ");
                String searchingForWebsiteInMap = scanner.nextLine();

                try {
                    PasswordEntry passwordEntry = passwordMap.values().stream()
                            .filter(pe -> pe.getWebsiteName().matches(".*" + searchingForWebsiteInMap + ".*"))
                            .findFirst()
                            .get();

                    System.out.println("Wciśnij [1] jeżeli chcesz skopiować hasło do pamięci podręcznej. ");

                    String userInput1 = scanner.nextLine();

                    if (userInput1.equals("1")) {
                        String password = passwordEntry.getPassword();
                        StringSelection stringSelection = new StringSelection(password);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    }

                } catch (NullPointerException npe) {
                    System.out.println("Brak takiego serwisu w bazie");
                }
            }
            if (userInput.equals("2")) {
                System.out.println("Proszę podać nazwę serwisu : ");
                String webSiteName = scanner.nextLine();
                System.out.println("Proszę podać Login : ");
                String login = scanner.nextLine();
                System.out.println("Wciśnij [1] jeżeli chcesz wygenerować losowe hasło, jeżeli nie wciśnij [0]");
                String checkIfWantToGeneratePassword = scanner.nextLine();
                String password = "";
                if (checkIfWantToGeneratePassword.equals("1")) {
                    Generator passwordGenerator = new Generator();
                    password = passwordGenerator.generatePassword(9);
                } else if (checkIfWantToGeneratePassword.equals("0")) {
                    System.out.println("Proszę podać hasło : ");
                    password = scanner.nextLine();
                }

                passwordMap.put(webSiteName, new PasswordEntry(webSiteName, login, password));

            }
            if (userInput.equals("3")) {
                Generator passwordGenerator = new Generator();
                System.out.println(passwordGenerator.generatePassword(9));
            }
            if (userInput.equals("4")) {
                passwordMap.keySet().forEach(System.out::println);
            }
            if (userInput.equals("0")) {
                break;
            }
        }
        FileManager.dumpPasswordEntriesToFile(passwordMap);
    }
}