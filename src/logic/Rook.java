package logic;

public class Rook extends ChessPiece {

    public Rook(int position) {
        super(position);
    }

    @Override
    public int countConflicts(ChessPiece[] chrom) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Check horizontal and vertical lines
        for (int i = 0; i < 8; i++) {
            if (i != col && chrom[row * 8 + i] != null) {
                conflicts++;
            }
            if (i != row && chrom[i * 8 + col] != null) {
                conflicts++;
            }
        }
        return conflicts;
    }

    @Override
    public int countConflicts(ChessPiece[][] board) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Check left
        for (int c = col - 1; c >= 0; c--) {
            if (board[row][c] != null) {
                conflicts++;
                break;
            }
        }

        // Check right
        for (int c = col + 1; c < 8; c++) {
            if (board[row][c] != null) {
                conflicts++;
                break;
            }
        }

        // Check up
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][col] != null) {
                conflicts++;
                break;
            }
        }

        // Check down
        for (int r = row + 1; r < 8; r++) {
            if (board[r][col] != null) {
                conflicts++;
                break;
            }
        }

        return conflicts;
    }
}
