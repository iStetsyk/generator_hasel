package generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class DescriptionFileReader extends Reader {

    private BufferedReader bufferedReader;
    private String decrypted;
    private int indexOfNextCharacter = 0;

    public DescriptionFileReader(String fileName, String key) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(fileName));
        List<String> lines = new LinkedList<>();

        String line = null;

        while ((line = this.bufferedReader.readLine()) != null) {
            // TODO: 23.08.2019 "Tutaj trzeba będzie odszyfrować liniejkę przed dodaniem do linijek
            lines.add(line);
        }
        decrypted = String.join("\n", lines) + "\n";
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        if (decrypted.length() <= indexOfNextCharacter) {
            return -1;
        }

        for (int i = 0; i < len; i++) {
            if (decrypted.length() > indexOfNextCharacter) {
                char readCharacter = decrypted.charAt(indexOfNextCharacter++);
                cbuf[off + 1] = readCharacter;
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
