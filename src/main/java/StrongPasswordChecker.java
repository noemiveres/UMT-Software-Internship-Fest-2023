import java.util.ArrayList;
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
        int minChanges;

        int specificCharToAdd = 0;
        //check for missing characters at first - need change anyways
        if (!pwd.matches(".*[a-z].*")) { // no lowercase
            specificCharToAdd++;
        }

        if (!pwd.matches(".*[A-Z].*")) { // no uppercase
            specificCharToAdd++;
        }

        if (!pwd.matches(".*[0-9].*")) { // no digits
            specificCharToAdd++;
        }

        minChanges = specificCharToAdd;   // we need to change at least this many characters

        //check for length
        int charToAdd;  // in case the password is too short
        int charToDel;  // in case the password is too long
        int grCnt = 0;  // number of groups of 3 repeating characters
        int rem;    // remainder of sequence length (of same characters) divided by 3
        ArrayList<Integer> grLenList = new ArrayList<>();   // list of lengths of repeating characters
        if (pwd.length() < 6) { // not enough characters
            charToAdd = 6 - pwd.length();
            if (charToAdd > specificCharToAdd) {
                minChanges = charToAdd;
            }// for length < 6 only 1 group of same characters can arise, which is covered by the minChanges

        } else if (pwd.length() <= 20) { // correct number of characters
            //check for repeating characters with regex - groups of min 3
            Pattern pattern = Pattern.compile("(.)\\1{2,}");    // the first repeated char is repeated at least 2 times
            Matcher matcher = pattern.matcher(pwd);
            while (matcher.find()) {
                grCnt += matcher.group().length() / 3;    // add number of groups of 3 - remainder is not important
            }
            if (grCnt > specificCharToAdd) {
                minChanges = grCnt; // minChanges is exactly how many groups should be destroyed
            }

        } else {    // too many characters
            charToDel = pwd.length() - 20;
            minChanges += charToDel;    // we need to delete at least this many characters
            Pattern pattern = Pattern.compile("(.)\\1{2,}");
            Matcher matcher = pattern.matcher(pwd);
            while (matcher.find()) {
                grLenList.add(matcher.group().length());
            }
            // delete until you can and each length becomes of form 3k+2
            for (int i = 0; i < grLenList.size() && charToDel > 0; i++) {
                rem = grLenList.get(i) % 3;
                if (rem == 0) {  // if length is of form 3k we only need to delete one
                    grLenList.set(i, grLenList.get(i) - 1); // better to delete 1 from 2 sequences than 2 from one
                    charToDel--;
                }
            }
            // we delete 2 only after we deleted 1 from the cases where we obtained the best form of 3k+2
            for (int i = 0; i < grLenList.size() && charToDel > 0; i++) {
                rem = grLenList.get(i) % 3;
                if (rem == 1) {   // if length is of form 3k+1 we need to delete 2
                    if (charToDel > 1) { // if we can delete at least 2
                        grLenList.set(i, grLenList.get(i) - 2);
                        charToDel -= 2;
                    } else {    // if we can only delete 1
                        grLenList.set(i, grLenList.get(i) - 1);
                        charToDel--;
                    }
                }

            }

            for (Integer length : grLenList) {
                grCnt += length / 3;    // how many groups should be destroyed
            }
            grCnt -= charToDel / 3; // how many groups remain to be solved - we have to delete 3 chars for each group

            if (specificCharToAdd < grCnt) {
                minChanges += grCnt - specificCharToAdd;  // specificCharToAdd solved some groups, but not all
            }   // otherwise, we don't need to change anything else, specificCharToAdd solves the remaining groups
        }
        return minChanges;
    }
}
