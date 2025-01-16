import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TicTacToeGUI {

    private TicTacToe game;
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private boolean multiplayer;
    private static final String SAVE_FILE_PATH = "tictactoe.ser"; // Specify the file path for saving/loading

    public TicTacToeGUI() {
        game = new TicTacToe();
        buttons = new JButton[3][3];
        frame = new JFrame("Tic Tac Toe");
        statusLabel = new JLabel("Player X's turn", JLabel.CENTER);

        // Ask the user for game mode (Single-player vs Multiplayer)
        String[] options = {"Multiplayer", "Single Player"};
        int choice = JOptionPane.showOptionDialog(frame, "Select Game Mode", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        multiplayer = (choice == 0); // If 0 -> Multiplayer, otherwise Single Player

        // Game GUI setup
        frame.setLayout(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        JButton saveGameButton = new JButton("Save Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton toggleModeButton = new JButton("Toggle Mode (Multiplayer/AI)");

        newGameButton.addActionListener(e -> newGame());
        saveGameButton.addActionListener(e -> saveGame());
        loadGameButton.addActionListener(e -> loadGame());
        toggleModeButton.addActionListener(e -> toggleMode());

        controlPanel.add(newGameButton);
        controlPanel.add(saveGameButton);
        controlPanel.add(loadGameButton);
        controlPanel.add(toggleModeButton);

        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private final int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.isGameOver() || game.getBoard()[row][col] != TicTacToe.EMPTY) {
                return;
            }

            boolean success = game.makeMove(row, col);
            if (success) {
                buttons[row][col].setText(String.valueOf(game.getBoard()[row][col]));
                checkGameStatus();

                // If multiplayer mode, alternate turn
                if (!multiplayer && !game.isGameOver()) {
                    // AI makes a move in single-player mode
                    aiMove();
                }
            }
        }
    }

    private void checkGameStatus() {
        if (game.isGameOver()) {
            if (game.getWinner() == TicTacToe.PLAYER_X) {
                statusLabel.setText("Player X wins!");
            } else if (game.getWinner() == TicTacToe.PLAYER_O) {
                statusLabel.setText("Player O wins!");
            } else {
                statusLabel.setText("It's a tie!");
            }
        } else {
            statusLabel.setText(game.isPlayerXTurn() ? "Player X's turn" : "Player O's turn");
        }
    }

    private void newGame() {
        game.resetGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        statusLabel.setText("Player X's turn");
    }

    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            out.writeObject(game); // Save the game object to the file
            JOptionPane.showMessageDialog(frame, "Game saved to " + SAVE_FILE_PATH);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving game.");
        }
    }

    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            game = (TicTacToe) in.readObject(); // Load the saved game object
            JOptionPane.showMessageDialog(frame, "Game loaded from " + SAVE_FILE_PATH);
            updateBoard();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error loading game. Ensure the save file exists.");
        }
    }

    private void updateBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
            }
        }
        checkGameStatus();
    }

    private void toggleMode() {
        multiplayer = !multiplayer;
        JOptionPane.showMessageDialog(frame, "Mode switched: " + (multiplayer ? "Multiplayer" : "Single Player"));
    }

    private void aiMove() {
        // Simple AI move (random)
        int[] move = TicTacToeAI.getNextMove(game); // This will now work
        if (move != null) {
            game.makeMove(move[0], move[1]);
            buttons[move[0]][move[1]].setText(String.valueOf(game.getBoard()[move[0]][move[1]]));
            checkGameStatus();
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
