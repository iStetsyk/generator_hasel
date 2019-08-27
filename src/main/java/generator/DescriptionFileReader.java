package generator;

import org.jasypt.util.text.BasicTextEncryptor;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DescriptionFileReader extends Reader {

    private BufferedReader bufferedReader;
    private String decrypted;
    private int indexOfNextChar = 0;
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    public DescriptionFileReader(String fileName, String key) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(fileName));
        List<String> lines = new LinkedList<>();

        String line = null;
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(key);

        while ((line = bufferedReader.readLine()) != null) {
            try {
                lines.add(textEncryptor.decrypt(line));

            } catch (Exception e) {
                System.out.println("Wprowadziłeś błędne hasło.");
            }
        }
        decrypted = String.join("\n", lines) + "\n";
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (decrypted.length() <= indexOfNextChar) {
            return -1;
        }

        for (int i = 0; i < len; i++) {
            if (decrypted.length() > indexOfNextChar) {
                char readCharacter = decrypted.charAt(indexOfNextChar++);
                cbuf[off + i] = readCharacter;
            } else {
                return i;
            }
        }
        return len;
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}