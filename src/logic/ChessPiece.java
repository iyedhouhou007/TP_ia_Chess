package logic;

public abstract class ChessPiece {
    private int position;

    public ChessPiece(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public abstract int countConflicts(ChessPiece[] chrom);

    public abstract int countConflicts(ChessPiece[][] chrom);
}
