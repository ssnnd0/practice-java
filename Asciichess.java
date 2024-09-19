import java.util.Scanner;

public class AsciiChess {
  private static final int BOARD_SIZE = 8;
  private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
  private static boolean whiteTurn = true;
  private static final String RESET = "\u001B[0m";
  private static final String WHITE = "\u001b[31m\u001b[1m";
  private static final String BLACK = "\u001b[1m";
  public static void main(String[] args) {
    initializeBoard();
    printBoard();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Enter your move (e.g., e2 e4, or 'exit'): ");
      String moveInput = scanner.nextLine().toLowerCase();
      if (moveInput.equalsIgnoreCase("exit")) {
        break;
      }
      if (isValidMove(moveInput)) {
        makeMove(moveInput);
        clearScreen();
        printBoard();
      } else {
        System.out.println("Invalid move. Try again.");
      }
    }
    scanner.close();
  }
  
  private static void initializeBoard() {
    for (int i = 0; i < 8; i++) {
      board[1][i] = 'P';
      board[6][i] = 'p';
    }
    board[0][0] = board[0][7] = 'R';
    board[7][0] = board[7][7] = 'r';
    board[0][1] = board[0][6] = 'N';
    board[7][1] = board[7][6] = 'n';
    board[0][2] = board[0][5] = 'B';
    board[7][2] = board[7][5] = 'b';
    board[0][3] = 'Q';
    board[7][3] = 'q';
    board[0][4] = 'K';
    board[7][4] = 'k';
    for (int i = 2; i < 6; i++) {
      for (int j = 0; j < 8; j++) {
        board[i][j] = ' ';
      }
    }
  }
  
  private static boolean isValidMove(String move) {
    if (move.length() != 5 || move.charAt(2) != ' ') {
      return false;
    }
    int startCol = move.charAt(0) - 'a';
    int startRow = move.charAt(1) - '1';
    int endCol = move.charAt(3) - 'a';
    int endRow = move.charAt(4) - '1';
    if (startCol < 0 || startCol > 7 || startRow < 0 || startRow > 7 || endCol < 0 || endCol > 7 || endRow < 0 || endRow > 7) {
      return false;
    }
    char piece = board[startRow][startCol];
    if (whiteTurn && Character.isLowerCase(piece)) {
      return false;
    } else if (!whiteTurn && Character.isUpperCase(piece)) {
      return false;
    }
    return true;
  }
  
  private static void makeMove(String move) {
    if (!isValidMove(move)) {
      return;
    }
    int startCol = move.charAt(0) - 'a';
    int startRow = move.charAt(1) - '1';
    int endCol = move.charAt(3) - 'a';
    int endRow = move.charAt(4) - '1';
    board[endRow][endCol] = board[startRow][startCol];
    board[startRow][startCol] = ' ';
    whiteTurn = !whiteTurn;
  }
  
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  
  private static void printBoard() {
    System.out.println("    A   B   C   D   E   F   G   H  ");
    System.out.println("  +---+---+---+---+---+---+---+---+");
    for (int row = BOARD_SIZE - 1; row >= 0; row--) {
      System.out.print((row + 1) + " |");
      for (int col = 0; col < BOARD_SIZE; col++) {
        char piece = board[row][col];
        if (Character.isUpperCase(piece)) {
          System.out.print(WHITE + " " + piece + " " + RESET + "|");
        } else {
          System.out.print(BLACK + " " + piece + " " + RESET + "|");
        }
      }
      System.out.println(" " + (row + 1));
      System.out.println("  +---+---+---+---+---+---+---+---+");
    }
    System.out.println("    A   B   C   D   E   F   G   H  ");
  }
}
