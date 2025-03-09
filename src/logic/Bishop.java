package logic;

public class Bishop extends ChessPiece {

    public Bishop(int position) {
        super(position);
    }

    @Override
    public int countConflicts(ChessPiece[] chrom) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Check diagonal directions
        int[] rowMoves = {-1, -1, 1, 1};
        int[] colMoves = {-1, 1, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row;
            int newCol = col;
            while (true) {
                newRow += rowMoves[i];
                newCol += colMoves[i];
                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }
                int newIndex = newRow * 8 + newCol;
                if (chrom[newIndex] != null) {
                    conflicts++;
                    break;
                }
            }
        }
        return conflicts;
    }

    @Override
    public int countConflicts(ChessPiece[][] board) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        // Check diagonal directions
        int[] rowMoves = {-1, -1, 1, 1};
        int[] colMoves = {-1, 1, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row;
            int newCol = col;
            while (true) {
                newRow += rowMoves[i];
                newCol += colMoves[i];
                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }
                if (board[newRow][newCol] != null) {
                    conflicts++;
                    break;
                }
            }
        }
        return conflicts;
    }
}
