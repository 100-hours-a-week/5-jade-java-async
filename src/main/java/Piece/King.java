package Piece;

public class King extends Piece {

    public King(String color, int x, int y) {
        super(color, "K", x, y);
    }
    /*
    // 캐슬링
    private boolean canCastling(Piece[][] board, Piece rook){
        // 조건 3개
        // 1. 킹과 룩이 한번도 이동한 적 없는가
        if(rook.isMoved() || super.isMoved()){
            return false;
        }
        // 2. 킹과 룩 사이가 비었는가
        int kPos = super.getPosition().getX();
        int rPos = super.getPosition().getX();
        int yPos = super.getPosition().getY();
        if((kPos-rPos)>0){
            for(int i=kPos+1; i<rPos;i++){
                if(board[yPos][i]!=null){
                    return false;
                }
            }
        }
        else{
            for(int i=kPos-1; i>rPos;i--){
                if(board[yPos][i]!=null){
                    return false;
                }
            }
        }
        // 3. 킹이 위치한 칸과 이동할 칸이 공격받지 않는 상황이어야 한다.
        // Board측에 현재 기물 리스트 추가, 파라미터로 전달 필요
        // 복잡해서 드랍
    }
    */

    public void calMoves(Piece[][] board) {
        super.resetMoves();
        Pair current = super.getPosition();
        int x = current.getX();
        int y = current.getY();
        int[] dx = {1, -1, 1, -1, 0, 0, 1, -1};
        int[] dy = {1, -1, -1, 1, 1, -1, 0, 0};
        for(int i=0; i<dx.length; i++){
            int posX = x+dx[i];
            int posY = y+dy[i];
            if(posX >= 0 && posX < 8 && posY >= 0 && posY < 8){
                if(board[posY][posX] == null){
                    super.addMoves(posX, posY);
                }
                else if(!(board[posY][posX].getColor().equals(this.getColor()))){
                    super.addMoves(posX, posY);
                }
            }
        }
        /*
        if(!super.isMoved()){
            if(board[y][0].getName().equals("R")){
                if(canCastling(board, board[y][0])){
                    // addMoves 작업 필요
                    super.addMoves(2,y);
                }
            }
            else if(board[y][7].getName().equals("R")){
                if(canCastling(board, board[y][7])){
                    // addMoves 작업 필요
                    super.addMoves(6,y);
                }
            }
        }
        */
    }

}
