package io.hyperfoil.nagen.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.hyperfoil.nagen.util.FileReader.readFileInJar;

// TODO: publish and use dependency on Nagen
public class NameGenerator {
    private static final Object LOCK = new Object();
    private static volatile NameGenerator INSTANCE;
    private static final char[] PASSWORD_CHARS;
    private static final String[] DOMAIN_NAMES = {
          "spec.org", "google.com", "apple.com", "ibm.com", "intel.com",
          "oracle.com", "redhat.com", "ebay.com", "facebook.com", "hpe.com",
          "msn.com", "microsoft.com", "hotmail.com", "aol.com", "yahoo.com",
          "gmail.com", "outlook.com", "hushmail.com", "mail.com",
          };

    private final List<String> firstName;
    private final List<String> lastName;

    static {
        PASSWORD_CHARS = Stream.of(
              IntStream.rangeClosed('0', '9'),
              IntStream.rangeClosed('A', 'Z'),
              IntStream.rangeClosed('a', 'z'),
              "!@#$%^&*()_+".chars()
        ).flatMapToInt(Function.identity())
              .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
              .toString().toCharArray();
    }

    private NameGenerator() {
        firstName = readFileInJar("female.txt");
        firstName.addAll(readFileInJar("male.txt"));
        lastName = readFileInJar("last.txt");
    }

    public static NameGenerator getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null)
                    INSTANCE = new NameGenerator();
            }
        }
        return INSTANCE;
    }

    public String getRandomFirstName() {
        return firstName.get(randInt(0, firstName.size()-1));
    }

    public String getRandomLastName() {
        return lastName.get(randInt(0, lastName.size()-1));
    }

    private String getRandomDomainName() {
        return DOMAIN_NAMES[ThreadLocalRandom.current().nextInt(DOMAIN_NAMES.length)];
    }

    public String getRandomEmail(String firstName, String lastName, int suffix) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "_" + suffix + "@" + getRandomDomainName();
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public String getRandomPhone() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder phoneBuilder = new StringBuilder(16);
        phoneBuilder.append('+').append(random.nextInt(1000));
        for (int i = 0; i < 9; ++i) phoneBuilder.append((char) ('0' + random.nextInt(10)));
        return phoneBuilder.toString();
    }

    public String getRandomPassword() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int length = random.nextInt(12, 20);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            sb.append(PASSWORD_CHARS[random.nextInt(PASSWORD_CHARS.length)]);
        }
        return sb.toString();
    }
}
