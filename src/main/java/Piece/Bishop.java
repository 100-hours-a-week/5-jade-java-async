package Piece;

public class Bishop extends Piece{

    public Bishop(String color, int x, int y){
        super(color, "B", x, y);
    }
    public void calMoves(Piece[][] board){
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, -1, -1, 1};
        // 이동경로 계산
        for(int i=0; i<dx.length; i++){
            int posX = x+dx[i];
            int posY = y+dy[i];
            while(posX >= 0 && posX < 8 && posY >= 0 && posY < 8){
                if(board[posY][posX]==null){
                    super.addMoves(posX, posY);
                }
                else if(!(board[posY][posX].getColor().equals(super.getColor()))){
                    super.addMoves(posX, posY);
                    break;
                }
                else{
                    break;
                }
                posX += dx[i];
                posY += dy[i];
            }
        }
    }
}
