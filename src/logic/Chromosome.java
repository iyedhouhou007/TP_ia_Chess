package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Chromosome {
    private final ChessPiece[] pieces; // Array of 10 pieces: 4 Queens, 2 Bishops, 2 Rooks, 2 Knights
    private double fitness; // Fitness calculated as 1/(1 + nb_conflicts)

    // Constructor accepts an array of ChessPiece objects (10 pieces)
    public Chromosome(ChessPiece[] pieces) {
        this.pieces = pieces;
        evaluateFitness();
    }

    // Generate a random chromosome ensuring half the pieces are on the top-half and half on the bottom-half.
    public static Chromosome generateRandom() {

        // Create lists for positions in the top half (rows 0–3) and bottom half (rows 4–7).
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
        List<ChessPiece> piecesList = new ArrayList<>();
        // 4 Queens
        for (int i = 0; i < 4; i++) {
            if (i % 4 < 2) {
                piecesList.add(new Queen(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Queen(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }
        // 2 Knights
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Knight(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Knight(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }
        // 2 Bishops
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Bishop(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Bishop(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }

        // 2 Rooks
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                piecesList.add(new Rook(topPositions.getFirst()));
                topPositions.removeFirst();
            }else {
                piecesList.add(new Rook(bottomPositions.getFirst()));
                bottomPositions.removeFirst();
            }
        }

        return new Chromosome(piecesList.toArray(new ChessPiece[0]));

    }

    public static Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        ChessPiece[] parent1Pieces = parent1.getPieces();
        ChessPiece[] parent2Pieces = parent2.getPieces();
        int size = parent1Pieces.length; // Should be 10 based on your description
        int midpoint = size / 2; // Splitting in half

        // Create new offspring arrays
        ChessPiece[] offspring1Pieces = new ChessPiece[size];
        ChessPiece[] offspring2Pieces = new ChessPiece[size];

        // First offspring: Top half from parent1, bottom half from parent2
        System.arraycopy(parent1Pieces, 0, offspring1Pieces, 0, midpoint);
        System.arraycopy(parent2Pieces, midpoint, offspring1Pieces, midpoint, size - midpoint);

        // Second offspring: Top half from parent2, bottom half from parent1
        System.arraycopy(parent2Pieces, 0, offspring2Pieces, 0, midpoint);
        System.arraycopy(parent1Pieces, midpoint, offspring2Pieces, midpoint, size - midpoint);

        // Create new Chromosome objects with the mixed genes
        return new Chromosome[]{
                new Chromosome(offspring1Pieces),
                new Chromosome(offspring2Pieces)
        };
    }

    public static Chromosome mutate(Chromosome chromosome) {
        Random random = new Random();
        ChessPiece[] pieces = chromosome.getPieces().clone(); // Clone to avoid modifying the original

        // Randomly choose whether to mutate the top half or the bottom half
        boolean mutateTopHalf = random.nextBoolean();

        // Filter pieces based on the chosen half
        List<ChessPiece> relevantPieces = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            int row = piece.getPosition() / 8;
            if ((mutateTopHalf && row < 4) || (!mutateTopHalf && row >= 4)) {
                relevantPieces.add(piece);
            }
        }

        // Ensure there are at least two pieces to swap
        if (relevantPieces.size() < 2) {
            return chromosome; // No mutation possible
        }

        // Select two random pieces from the chosen half
        ChessPiece piece1 = relevantPieces.get(random.nextInt(relevantPieces.size()));
        ChessPiece piece2;
        do {
            piece2 = relevantPieces.get(random.nextInt(relevantPieces.size()));
        } while (piece1 == piece2); // Ensure they are different pieces

        // Swap their positions
        int tempPos = piece1.getPosition();
        piece1.setPosition(piece2.getPosition());
        piece2.setPosition(tempPos);

        // Return the new mutated chromosome
        return new Chromosome(pieces);
    }
    
    // Evaluate fitness by summing conflicts from each piece and then calculating:
    // Fitness F = 1 / (1 + totalConflicts)
    public void evaluateFitness() {
        // Build a 1D board representation (size 64) and place each piece at its position.
        ChessPiece[] board = new ChessPiece[64];
        for (ChessPiece piece : pieces) {
            board[piece.getPosition()] = piece;
        }
        int totalConflicts = 0;
        // Sum up conflicts using each piece's countConflicts() method (double counting as defined)
        for (ChessPiece piece : pieces) {
            totalConflicts += piece.countConflicts(board);
        }
        fitness = 1.0 / (1 + totalConflicts);
    }

    public double getFitness() {
        return fitness;
    }

    public double getProbability(double totalFitness) {
        return (fitness / totalFitness) * 100.0;
    }

    public ChessPiece[] getPieces() {
        return pieces;
    }

    // Print board representation using an 8x8 grid.
    public void printBoard() {
        char[][] displayBoard = new char[8][8];
        // Initialize board with empty dots.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                displayBoard[i][j] = '.';
            }
        }
        // Place each piece on the board; each piece is represented by the first letter of its class name.
        for (ChessPiece piece : pieces) {
            int row = piece.getPosition() / 8;
            int col = piece.getPosition() % 8;
            displayBoard[row][col] = piece.getClass().getSimpleName().charAt(0);
        }
        // Print the board.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(displayBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void swapPieces(ChessPiece p1, ChessPiece p2){
        int tmp = p1.getPosition();
        p1.setPosition(p2.getPosition());
        p2.setPosition(tmp);
    }


}
