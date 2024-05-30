package Piece;

public class Pawn extends Piece{
    public Pawn(String color, int x, int y){
        super(color, "P", x, y);
    }
    public boolean canPromotion(){
        int endRow = super.getColor().equals("white") ? 7 : 0;
        return super.getPosition().getY() == endRow;
    }
    public void calMoves(Piece[][] board){
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();
        int direction = super.getColor().equals("white") ? 1 : -1;
        int startRow = super.getColor().equals("white") ? 1 : 6;
        // 1. 처음 움직일때 최대 2칸 전진가능
        if(current.getY()==startRow){
            for(int i=1; i<3; i++){
                // 앞에 장애물 있으면 이동불가
                if(((y+(i*direction))>=0 && (y+(i*direction))<8) &&
                        (board[y+(i*direction)][x] == null)
                ){
                    super.addMoves(x, y+(i*direction));
                }
                else break;
            }
        }
        else {
            if ((((y + direction) >= 0) && ((y + direction) < 8)) &&
                    (board[y + direction][x] == null)
            ){
                System.out.println("x: "+x+"y: "+(y+direction));
                super.addMoves(x, y + direction);
            }
        }
        // 2. 전방 대각선에 적이 있을때
        int[] dx= {-1, 1};
        for(int i=0; i<2; i++){
            if((((y+direction>=0) && (y+direction<8)) && ((x+dx[i]>=0) && (x+dx[i]<8))) &&
                    ((board[y+direction][x+dx[i]]!=null) &&
                    !(board[y+direction][x+dx[i]].getColor().equals(super.getColor())))
            ){
                System.out.println("x: "+(x+dx[i])+"y: "+(y+direction));
                super.addMoves(x+dx[i], y+direction);
            }
        }
    }
    // 프로모션
    // Bishop, Knight, Queen, Rook
    public Piece promotion(String input){
        Piece p=null;
        switch(input) {
            case "Bishop":
                p = new Bishop(super.getColor(), super.getPosition().getX(), super.getPosition().getY());
                break;
            case "Knight":
                p = new Knight(super.getColor(), super.getPosition().getX(), super.getPosition().getY());
                break;
            case "Queen":
                p = new Queen(super.getColor(), super.getPosition().getX(), super.getPosition().getY());
                break;
            case "Rook":
                p = new Rook(super.getColor(), super.getPosition().getX(), super.getPosition().getY());
                break;
            default:
                System.out.println("there's no such Piece named"+input);
        }
        if(p!=null){
            System.out.println("Pawn promotioned to "+input);
        }
        return p;
    }
}
