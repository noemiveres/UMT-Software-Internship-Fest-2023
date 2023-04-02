import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrongPasswordChecker {
    public static void main(String[] args) {
        String pwd = "";
        while (!pwd.equals("Quit")) {
            System.out.print("Enter a password: ");
            pwd = new Scanner(System.in).nextLine();
            int minChanges = getMinimumChanges(pwd);
            System.out.println("Minimum changes required: " + minChanges + "\n");
        }
    }

    public static int getMinimumChanges(String pwd) {
        int minChanges = 0;

        if (pwd.length() < 6) { // not enough characters
            minChanges += 6 - pwd.length();
        }

        if (pwd.length() > 20) {// too many characters
            minChanges += pwd.length() - 20;
        }

        if (!pwd.matches(".*[a-z].*")) { // no lowercase
            minChanges++;
        }

        if (!pwd.matches(".*[A-Z].*")) { // no uppercase
            minChanges++;
        }

        if (!pwd.matches(".*[0-9].*")) { // no digits
            minChanges++;
        }

        //check for repeating characters with regex
        Pattern pattern = Pattern.compile("(.)\\1{2,}");
        Matcher matcher = pattern.matcher(pwd);
        while (matcher.find()) {
            minChanges += matcher.group().length()/3;
        }

        return minChanges;
    }
}
