import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    public static void main(String[] args) {
        Board gameBoard = new Board();
        Scanner in = new Scanner(System.in);
        Random compChoice = new Random();

        System.out.println("              TIC-TAC-TOE");
        System.out.println("CREATIVE COMPUTING  MORRISTOWN, NEW JERSEY");
        System.out.println("\nTHE BOARD IS NUMBERED: ");
        System.out.println(" 1  2  3\n 4  5  6\n 7  8  9\n");

        while (true) {
            // Mode selection
            System.out.println("Select mode:");
            System.out.println("1. Single Player (Human vs AI)");
            System.out.println("2. Multiplayer (Human vs Human)");
            int mode = 0;

            while (true) {
                try {
                    mode = in.nextInt();
                    if (mode == 1 || mode == 2) {
                        break;
                    } else {
                        System.out.println("Invalid choice. Enter 1 or 2.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid choice. Enter 1 or 2.");
                    in.nextLine();
                }
            }

            if (mode == 1) {
                singlePlayerMode(gameBoard, in, compChoice);
            } else {
                multiplayerMode(gameBoard, in);
            }
        }
    }

    private static void singlePlayerMode(Board gameBoard, Scanner in, Random compChoice) {
        char yourChar;
        char compChar;

        System.out.println("Do you want 'X' or 'O'?");
        while (true) {
            try {
                char input = in.next().charAt(0);

                if (input == 'X' || input == 'x') {
                    yourChar = 'X';
                    compChar = 'O';
                    break;
                } else if (input == 'O' || input == 'o') {
                    yourChar = 'O';
                    compChar = 'X';
                    break;
                } else {
                    System.out.println("That's not 'X' or 'O'. Try again.");
                    in.nextLine();
                }
            } catch (Exception e) {
                System.out.println("That's not 'X' or 'O'. Try again.");
                in.nextLine();
            }
        }

        while (true) {
            // Player's turn
            System.out.println("Where do you move?");
            while (true) {
                try {
                    int input = in.nextInt();
                    if (gameBoard.getBoardValue(input) == ' ') {
                        gameBoard.setArr(input, yourChar);
                        break;
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }
                    in.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input. Try again.");
                    in.nextLine();
                }
            }
            gameBoard.printBoard();

            // Check for win or draw
            if (checkWinOrDraw(gameBoard, yourChar, "You win!", in)) break;

            // Computer's turn
            System.out.println("The computer moves to:");
            while (true) {
                int position = 1 + compChoice.nextInt(9);
                if (gameBoard.getBoardValue(position) == ' ') {
                    gameBoard.setArr(position, compChar);
                    break;
                }
            }
            gameBoard.printBoard();

            // Check for win or draw
            if (checkWinOrDraw(gameBoard, compChar, "You lose!", in)) break;
        }
    }

    private static void multiplayerMode(Board gameBoard, Scanner in) {
        System.out.print("Player 1, enter your name: ");
        String player1 = in.next();
        System.out.print("Player 2, enter your name: ");
        String player2 = in.next();

        System.out.println(player1 + ", do you want 'X' or 'O'?");
        char player1Char = 'X', player2Char = 'O';
        while (true) {
            try {
                char input = in.next().charAt(0);

                if (input == 'X' || input == 'x') {
                    player1Char = 'X';
                    player2Char = 'O';
                    break;
                } else if (input == 'O' || input == 'o') {
                    player1Char = 'O';
                    player2Char = 'X';
                    break;
                } else {
                    System.out.println("That's not 'X' or 'O'. Try again.");
                    in.nextLine();
                }
            } catch (Exception e) {
                System.out.println("That's not 'X' or 'O'. Try again.");
                in.nextLine();
            }
        }

        boolean isPlayer1Turn = true;

        while (true) {
            String currentPlayer = isPlayer1Turn ? player1 : player2;
            char currentChar = isPlayer1Turn ? player1Char : player2Char;

            System.out.println(currentPlayer + ", where do you move?");
            while (true) {
                try {
                    int input = in.nextInt();
                    if (gameBoard.getBoardValue(input) == ' ') {
                        gameBoard.setArr(input, currentChar);
                        break;
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }
                    in.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input. Try again.");
                    in.nextLine();
                }
            }

            gameBoard.printBoard();

            // Check for win or draw
            if (checkWinOrDraw(gameBoard, currentChar, currentPlayer + " wins!", in)) break;

            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    private static boolean checkWinOrDraw(Board gameBoard, char playerChar, String winMessage, Scanner in) {
        if (gameBoard.checkWin(playerChar)) {
            System.out.println(winMessage + " Play again? (Y/N)");
            if (!playAgain(in)) System.exit(0);
            gameBoard.clear();
            return true;
        } else if (gameBoard.checkDraw()) {
            System.out.println("It's a draw! Play again? (Y/N)");
            if (!playAgain(in)) System.exit(0);
            gameBoard.clear();
            return true;
        }
        return false;
    }

    private static boolean playAgain(Scanner in) {
        while (true) {
            try {
                char input = in.next().charAt(0);
                if (input == 'Y' || input == 'y') return true;
                else if (input == 'N' || input == 'n') return false;
                else System.out.println("That's not 'Y' or 'N'. Try again.");
            } catch (Exception e) {
                System.out.println("That's not 'Y' or 'N'. Try again.");
                in.nextLine();
            }
        }
    }
}
