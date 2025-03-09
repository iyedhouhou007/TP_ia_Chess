package logic;

public class Knight extends ChessPiece {

    public Knight(int position) {
        super(position);
    }

    @Override
    public int countConflicts(ChessPiece[] chrom) {
        int conflicts = 0;
        int row = getPosition() / 8;
        int col = getPosition() % 8;

        int[][] moves = {
                {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
                {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                int newIndex = newRow * 8 + newCol;
                if (chrom[newIndex] != null) {
                    conflicts++;
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

        int[][] moves = {
                {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
                {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                if (board[newRow][newCol] != null) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }
}
