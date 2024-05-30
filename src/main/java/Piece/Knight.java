package Piece;

public class Knight extends Piece{
    public Knight(String color, int x, int y){
        super(color,"N", x, y);
    }

    public void calMoves(Piece[][] board){
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();
        int[] direction = {1, -1};
        int[] dx={1, -1};
        int[] dy={1, -1};
        for(int i=0; i<2; i++){
            int posX;
            int posY;
            // x-1, x+1 / y+1 / y-1
            // 상, 하
            for(int j=0; j<2; j++){
                posY = y+direction[i];
                if(posY>=0 && posY<8){
                    int newPosX = x+dx[j];
                    int newPosY = posY+direction[i];
                    if((newPosX>=0&&newPosX<8) &&
                            (newPosY>=0&&newPosY<8)){
                        if(board[newPosY][newPosX]==null){
                            super.addMoves(newPosX, newPosY);
                        }
                        else if(!(board[newPosY][newPosX].getColor().equals(super.getColor()))){
                            super.addMoves(newPosX, newPosY);
                        }
                    }
                }
                else{
                    break;
                }
            }
            // y-1, y+1 / x+1 / x-1
            // 좌, 우
            for(int j=0; j<2; j++){
                posX = x+direction[i];
                if(posX>=0 && posX<8){
                    int newPosX = posX+direction[i];
                    int newPosY = y+dy[j];
                    if((newPosX>=0&&newPosX<8) &&
                            (newPosY>=0&&newPosY<8)) {
                        if (board[newPosY][newPosX] == null) {
                            super.addMoves(newPosX, newPosY);
                        }
                        else if(!(board[newPosY][newPosX].getColor().equals(super.getColor()))){
                            super.addMoves(newPosX, newPosY);
                        }
                    }
                }
                else{
                    break;
                }
            }

        }

    }
}
