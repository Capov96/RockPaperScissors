public class Rules {

  public static String getResult(int firstPlayer, int secondPlayer, int numOfOptions) {
    String result = "";
    if (firstPlayer == secondPlayer){
      return "DRAW";
    } else if (((firstPlayer + (numOfOptions / 2)) < secondPlayer)
             || (firstPlayer > secondPlayer && (firstPlayer - (numOfOptions / 2)) <= secondPlayer)){
      return "WIN";
    } else return "LOSE";
  }
}
