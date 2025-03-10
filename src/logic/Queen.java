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
            }
        }

        // Vertical Check
        for (int i = 0; i < 8; i++) {
            if (i == row) continue;
            if (chrom[i * 8 + col] != null) {
                conflicts++;
            }
        }

        // Diagonal Check (Top-Left to Bottom-Right)
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (chrom[(row - i) * 8 + (col - i)] != null) {
                conflicts++;

            }
        }
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            if (chrom[(row + i) * 8 + (col + i)] != null) {
                conflicts++;
            }
        }

        // Diagonal Check (Top-Right to Bottom-Left)
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            if (chrom[(row - i) * 8 + (col + i)] != null) {
                conflicts++;
            }
        }
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            if (chrom[(row + i) * 8 + (col - i)] != null) {
                conflicts++;
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
            }
        }

        // Vertical Check
        for (int i = 0; i < 8; i++) {
            if (i == row) continue;
            if (board[i][col] != null) {
                conflicts++;
            }
        }

        // Diagonal Check (Top-Left to Bottom-Right)
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (board[row - i][col - i] != null) {
                conflicts++;
            }
        }
        for (int i = 1; row + i < 8 && col + i < 8; i++) {
            if (board[row + i][col + i] != null) {
                conflicts++;
            }
        }

        // Diagonal Check (Top-Right to Bottom-Left)
        for (int i = 1; row - i >= 0 && col + i < 8; i++) {
            if (board[row - i][col + i] != null) {
                conflicts++;
            }
        }
        for (int i = 1; row + i < 8 && col - i >= 0; i++) {
            if (board[row + i][col - i] != null) {
                conflicts++;
            }
        }

        return conflicts;
    }

}
