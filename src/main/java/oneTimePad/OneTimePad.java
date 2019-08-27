package oneTimePad;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
@Log4j
public class OneTimePad {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int desiredLenght = 100;

        final byte[] message = "message".getBytes();

        final byte[] encoded = new byte[message.length];
        final byte[] decoded = new byte[message.length];

//        String key = UUID.randomUUID().toString().replace("-", "");

        for (int i = 0; i < message.length; i++) {
            encoded[i] = (byte) (message[i] ^ encoded[i]);
        }

        StringBuilder sb = new StringBuilder();
        while (sb.length() < desiredLenght) {
            sb.append(UUID.randomUUID().toString().replace("-", ""));
        }
        String key = sb.substring(0, desiredLenght);

        for (int i = 0; i < encoded.length; i++) {
            decoded[i] = (byte) (encoded[i] ^ decoded[i]);

        }
        System.out.println(encoded);
        System.out.println(decoded);
    }
}
