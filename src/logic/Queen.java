package logic;

public class Queen extends ChessPiece {

    public Queen(int position) {
        super(position);
    }


    @Override
    public int countConflicts(ChessPiece[] chrom) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Horizontal Check
        for (int i = 0; i < 8; i++) {
            if (i == col) continue;
            if (chrom[row * 8 + i] != null) {
                conflicts++;
                break;
            }
        }

        // Vertical Check
        for (int i = 0; i < 8; i++) {
            if (i == row) continue;
            if (chrom[i * 8 + col] != null) {
                conflicts++; // Increment conflict count by 1 (direction is conflicted)
                break;      // Stop checking vertically, conflict found in this direction
            }
        }

        // Diagonal Check (Top-Left to Bottom-Right)
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (chrom[(row - i) * 8 + (col - i)] != null) {
                conflicts++; // Increment conflict count by 1 (direction is conflicted)
                break;      // Stop checking diagonal direction, conflict found
            }
        }
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            if (chrom[(row + i) * 8 + (col + i)] != null) {
                conflicts++; // Increment conflict count by 1 (direction is conflicted)
                break;      // Stop checking diagonal direction, conflict found
            }
        }

        // Diagonal Check (Top-Right to Bottom-Left)
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            if (chrom[(row - i) * 8 + (col + i)] != null) {
                conflicts++; // Increment conflict count by 1 (direction is conflicted)
                break;      // Stop checking diagonal direction, conflict found
            }
        }
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            if (chrom[(row + i) * 8 + (col - i)] != null) {
                conflicts++; // Increment conflict count by 1 (direction is conflicted)
                break;      // Stop checking diagonal direction, conflict found
            }
        }

        return conflicts; // Now this will be between 0 and 8 (max 8 directions)
    }

    @Override
    public int countConflicts(ChessPiece[][] board) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Horizontal Check
        for (int i = 0; i < 8; i++) {
            if (i == col) continue;
            if (board[row][i] != null) {
                conflicts++;
                break;
            }
        }

        // Vertical Check
        for (int i = 0; i < 8; i++) {
            if (i == row) continue;
            if (board[i][col] != null) {
                conflicts++;
                break;
            }
        }

        // Diagonal Check (Top-Left to Bottom-Right)
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (board[row - i][col - i] != null) {
                conflicts++;
                break;
            }
        }
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            if (board[row + i][col + i] != null) {
                conflicts++;
                break;
            }
        }

        // Diagonal Check (Top-Right to Bottom-Left)
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            if (board[row - i][col + i] != null) {
                conflicts++;
                break;
            }
        }
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            if (board[row + i][col - i] != null) {
                conflicts++;
                break;
            }
        }

        return conflicts;
    }

}
