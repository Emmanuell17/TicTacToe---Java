import java.io.*;
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
            System.out.println("Would you like to load a saved game? (Y/N)");
            char loadChoice = in.next().charAt(0);
            if (loadChoice == 'Y' || loadChoice == 'y') {
                if (loadGame(gameBoard, in)) {
                    break;
                }
            } else {
                break;
            }
        }

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

    private static boolean loadGame(Board gameBoard, Scanner in) {
        System.out.println("Enter the file name to load the game from:");
        String fileName = in.next();
        try {
            gameBoard.load(fileName);
            System.out.println("Game loaded successfully!");
            gameBoard.printBoard();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to load game. Starting a new game.");
        }
        return false;
    }

    private static void saveGame(Board gameBoard, Scanner in) {
        System.out.println("Enter the file name to save the game:");
        String fileName = in.next();
        try {
            gameBoard.save(fileName);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game.");
        }
    }

    private static void singlePlayerMode(Board gameBoard, Scanner in, Random compChoice) {
        // Existing single-player logic...
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
            if (gameBoard.checkWin(currentChar)) {
                System.out.println(currentPlayer + " wins! Do you want to save the game before exiting? (Y/N)");
                char saveChoice = in.next().charAt(0);
                if (saveChoice == 'Y' || saveChoice == 'y') {
                    saveGame(gameBoard, in);
                }
                System.exit(0);
            } else if (gameBoard.checkDraw()) {
                System.out.println("It's a draw! Do you want to save the game before exiting? (Y/N)");
                char saveChoice = in.next().charAt(0);
                if (saveChoice == 'Y' || saveChoice == 'y') {
                    saveGame(gameBoard, in);
                }
                System.exit(0);
            }

            isPlayer1Turn = !isPlayer1Turn;
        }
    }
}
