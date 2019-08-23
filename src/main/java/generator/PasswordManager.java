package generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordManager {

    private static List<PasswordEntry> passwordList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        while (true){

            System.out.println("Czy chcesz zapisać nowe hasło do swojej bazy? Wciśnij 1 \n" +
                    "Jeżeli chcesz opuścić manager wciśnij 0");
            String userInput = scanner.nextLine();
            if(userInput.equals("1")) {
                System.out.println("Proszę podać nazwę serwisu : ");
                String webSiteName = scanner.nextLine();
                System.out.println("Proszę podać Login : ");
                String login = scanner.nextLine();
                System.out.println("Proszę podać hasło : ");
                String password = scanner.nextLine();

                passwordList.add(new PasswordEntry(webSiteName, login, password));
            }

            if (userInput.equals("0")){
                break;
            }
        }

        FileManager.dumpPasswordEntriesToFile(passwordList);
    }

    private static void savePassword(){
        passwordList.add(new PasswordEntry(
                "www.facebook.pl ",
                "MichałSkoczek69 ",
                "KubaJestNalepszy "));
    }


}