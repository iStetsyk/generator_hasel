package generator;

import java.util.HashSet;
import java.util.Set;

class Generator {
    public String generatePassword(int length) {

        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890!@#$%^&*()";

        final java.util.Random rand = new java.util.Random();

        final Set<String> identifiers = new HashSet<String>();

        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
}