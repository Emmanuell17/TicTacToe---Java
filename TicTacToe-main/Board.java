public class Board {
    public final char[] arr;

    public Board() {
        this.arr = new char[9];
        for (int x = 1; x <= 9; x++) {
            this.arr[x - 1] = ' ';
        }
    }

    public void setArr(int position, char player) {
        this.arr[position - 1] = player;
    }

    public void printBoard() {
        System.out.format("%-3c ! %-3c ! %-3c\n----+----+----\n%-3c ! %-3c ! %-3c\n----+----+----\n%-3c ! %-3c ! %-3c\n",
                this.arr[0], this.arr[1], this.arr[2],
                this.arr[3], this.arr[4], this.arr[5],
                this.arr[6], this.arr[7], this.arr[8]);
    }

    public char getBoardValue(int x) {
        return arr[x - 1];
    }

    public boolean checkWin(char player) {
        return (
                (arr[0] == player && arr[1] == player && arr[2] == player) || // Row 1
                        (arr[3] == player && arr[4] == player && arr[5] == player) || // Row 2
                        (arr[6] == player && arr[7] == player && arr[8] == player) || // Row 3
                        (arr[0] == player && arr[3] == player && arr[6] == player) || // Col 1
                        (arr[1] == player && arr[4] == player && arr[7] == player) || // Col 2
                        (arr[2] == player && arr[5] == player && arr[8] == player) || // Col 3
                        (arr[0] == player && arr[4] == player && arr[8] == player) || // Diagonal 1
                        (arr[2] == player && arr[4] == player && arr[6] == player)    // Diagonal 2
        );
    }

    public boolean checkDraw() {
        for (char cell : arr) {
            if (cell == ' ') {
                return false; // If any cell is empty, it's not a draw
            }
        }
        return true;
    }

    public void clear() {
        for (int x = 1; x <= 9; x++) {
            this.arr[x - 1] = ' ';
        }
    }
}
