package Piece;

public class Rook extends Piece {
    public Rook(String color, int x, int y){
        super(color, "R", x, y);
    }
    public void calMoves(Piece[][] board){
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();
        int[] direction = {1, -1};
        // 이동경로 계산
        for(int i = 0; i < direction.length; i++){
            // 상, 하
            int pos = y+direction[i];
            while(pos>=0 && pos<8){
                if(board[pos][x]==null){
                    super.addMoves(x, pos);
                }
                else if(!(board[pos][x].getColor().equals(this.getColor()))){
                    super.addMoves(x, pos);
                    break;
                }
                else{
                    break;
                }
                pos+=direction[i];
            }
            // 좌, 우
            pos = x+direction[i];
            while(pos>=0 && pos<8){
                if(board[y][pos]==null){
                    super.addMoves(pos, y);
                }
                else if(!(board[y][pos].getColor().equals(this.getColor()))){
                    super.addMoves(pos, y);
                    break;
                }
                else{
                    break;
                }
                pos+=direction[i];
            }
        }
    }
}
