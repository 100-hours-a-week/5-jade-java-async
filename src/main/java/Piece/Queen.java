package Piece;

public class Queen extends Piece {
    public Queen(String color, int x, int y) {
        super(color, "Q", x, y);
    }
    public void calMoves(Piece[][] board){
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();

        // 십자
        int[] direction = {1, -1};
        // 대각
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, -1, -1, 1};

        // 이동경로 계산
        // 십자
        for(int i = 0; i < direction.length; i++){
            // 상, 하
            int pos = y+direction[i];
            while(pos>=0 && pos<8){
                if(board[pos][x]==null){
                    super.addMoves(x, pos);
                }
                else if(!(board[pos][x].getColor().equals(super.getColor()))){
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
                else if(!(board[y][pos].getColor().equals(super.getColor()))){
                    super.addMoves(pos, y);
                    break;
                }
                else{
                    break;
                }
                pos+=direction[i];
            }
        }
        // 대각
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
