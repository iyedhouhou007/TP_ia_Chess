package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessBoard {
    private ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        // Create lists for positions in the top and bottom halves of the board.
        List<Integer> topPositions = new ArrayList<>();
        List<Integer> bottomPositions = new ArrayList<>();
        for (int pos = 0; pos < 64; pos++) {
            int row = pos / 8;
            if (row < 4) {
                topPositions.add(pos);
            } else {
                bottomPositions.add(pos);
            }
        }
        Collections.shuffle(topPositions);
        Collections.shuffle(bottomPositions);

        // Create a list of 10 pieces with the desired composition:
        // 4 Queens, 2 Bishops, 2 Rooks, 2 Knights.
        List<ChessPiece> piecesList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 4 < 2) {
                piecesList.add(new Queen(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Queen(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Knight(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Knight(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Bishop(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Bishop(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Rook(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Rook(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }

        for (ChessPiece cP : piecesList) {
            int row = cP.getPosition() / 8;
            int col = cP.getPosition() % 8;

            board[row][col] = cP;
        }
    }

    public void updateBoard(ChessPiece[] piecesList) {
        for (int i = 0; i < 8 ; i++){
            for (int j = 0; j < 8; j++){
                board[i][j] = null;
            }
        }

        for (ChessPiece cP : piecesList) {
            int row = cP.getPosition() / 8;
            int col = cP.getPosition() % 8;

            board[row][col] = cP;
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    // Display the first character of the piece’s class name.
                    System.out.print(board[i][j].getClass().getSimpleName().charAt(0) + " ");
                }
            }
            System.out.println();
        }
    }

    // Converts the current board into a Chromosome by collecting all placed pieces.
    public Chromosome toChromosome() {
        List<ChessPiece> pieceList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    pieceList.add(board[i][j]);
                }
            }
        }
        return new Chromosome(pieceList.toArray(new ChessPiece[0]));
    }

    public static void main(String[] args) {
        ChessBoard cb = new ChessBoard();
        cb.printBoard();
        Chromosome chrom = cb.toChromosome();

        chrom.swapPieces(chrom.getPieces()[3], chrom.getPieces()[7]);
        System.out.println("Chromosome fitness: " + chrom.getFitness());

        cb.updateBoard(chrom.getPieces());
        cb.printBoard();
    }
}
