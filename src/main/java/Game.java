import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Scanner;

public class Game {
  public static String[] options;
  public static Table table;
  public static SecureRandom random = new SecureRandom();
  public static byte[] key;
  public static byte[] hmac;

  public static void main(String[] args) {
    try {
      table = new Table(args);
      options =  table.getOptions();
      start();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  public static void start() throws NoSuchAlgorithmException, InvalidKeyException {
    int userMove;
    int pcMove;
    do {
      pcMove = getPcMove();
      System.out.println("HMAC: " + HMACGenerator.bytesToHex(hmac));
      userMove = getUserMove();
      if (userMove != 0){
        System.out.println("Your move: " + options[userMove-1] + "\nComputer move: " + options[pcMove]);
        getResult(userMove-1, pcMove);
      }
    } while (userMove != 0);
    System.out.println("End game.");
  }

  public static int getPcMove() throws NoSuchAlgorithmException, InvalidKeyException {
    int pcMove = random.nextInt(options.length);
    key = HMACGenerator.getKey();
    hmac = HMACGenerator.getHmac(options[pcMove], key);
    return pcMove;
  }

  public static void printMenu() {
    System.out.println("Available moves:");
    for (int i = 0; i < options.length; i++) {
      System.out.println(i+1 + " - " + options[i]);
    }
    System.out.println("0 - exit");
    System.out.println("? - help");
  }

  public static int getUserMove() {
    Scanner scanner = new Scanner(System.in);
    String s;
    do {
      printMenu();
      System.out.print("Enter your move: ");
      s = scanner.nextLine();
      if (s.equals("?")) {
        table.printTable();
        continue;
      } else if (s.matches("\\d")) {
        int moveId = Integer.parseInt(s);
        if (moveId >= 0 && moveId <= options.length) {
          return moveId;
        }
      }
      System.out.println("Invalid input, try again!");
    } while (true);
  }

  public static void getResult(int userMove, int pcMove) {
    String result = Rules.getResult(userMove, pcMove, options.length).toLowerCase(Locale.ROOT);
    System.out.println(result.equals("draw") ? result + "!" : "You " + result + "!");
    System.out.println("HMAC key: " + HMACGenerator.bytesToHex(key) + "\n");
  }
}
