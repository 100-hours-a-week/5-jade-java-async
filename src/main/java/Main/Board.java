package Main;

import Piece.*;

import java.util.ArrayList;

public class Board {
    private final Piece[][] board;
    // ArrayList 추가작업 필요
    private ArrayList<Piece> whiteKilled;
    private boolean whiteKingKilled;
    private int whiteKillCount;
    private int whiteCount;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackKilled;
    private boolean blackKingKilled;
    private int blackKillCount;
    private int blackCount;
    private ArrayList<Piece> blackPieces;
    private int time = 0;
    // false : 흑
    // true  : 백
    private boolean turn = true;

    public Board() {
        board = new Piece[8][8];
        this.time = 0;
        this.whiteKingKilled=false;
        this.blackKingKilled=false;
        this.whiteKillCount = 0;
        this.whiteKilled = new ArrayList<>();
        this.blackKillCount = 0;
        this.blackKilled = new ArrayList<>();
        initBoard();
    }
    private void initBoard() {
        // 폰 init
        // \u001B[37m: white
        // \u001B[33m: black (yellow)
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("white", i, 1);
            board[6][i] = new Pawn("black", i, 6);
        }
        // 다른 말들 init
        board[0][0] = new Rook("white", 0, 0);
        board[0][1] = new Knight("white", 1, 0);
        board[0][2] = new Bishop("white", 2, 0);
        board[0][3] = new Queen("white", 3, 0);
        board[0][4] = new King("white", 4, 0);
        board[0][5] = new Bishop("white", 5, 0);
        board[0][6] = new Knight("white", 6, 0);
        board[0][7] = new Rook("white", 7, 0);

        board[7][0] = new Rook("black", 0, 7);
        board[7][1] = new Knight("black", 1, 7);
        board[7][2] = new Bishop("black", 2, 7);
        board[7][3] = new Queen("black", 3, 7);
        board[7][4] = new King("black", 4, 7);
        board[7][5] = new Bishop("black", 5, 7);
        board[7][6] = new Knight("black", 6, 7);
        board[7][7] = new Rook("black", 7, 7);
    }
    private void addTime(){
        this.time+=1;
    }
    private void subTime(){
        this.time-=1;
    }
    public int getTime(){
        return this.time;
    }
    public boolean getTurn(){
        return this.turn;
    }
    public Piece[][] getBoard(){
        return this.board;
    }
    public void toggleTurn(){
        this.turn = !this.turn;
    }
    public int getWhiteKC(){
        return this.whiteKillCount;
    }
    public void addWhiteKC(){
        this.whiteKillCount++;
    }
    public int getBlackKC(){
        return this.blackKillCount;
    }
    public void addBlackKC(){
        this.blackKillCount++;
    }
    public void addWhiteKilled(Piece piece){
        this.whiteKilled.add(piece);
        addWhiteKC();
    }
    public void addBlackKilled(Piece piece){
        this.blackKilled.add(piece);
        addBlackKC();
    }
    public boolean getWhiteKingKilled(){
        return this.whiteKingKilled;
    }
    public boolean getBlackKingKilled(){
        return this.blackKingKilled;
    }
    public void getWhiteKilled(){
        System.out.println("White killed");
        if(getWhiteKC()==0){
            System.out.println();
            return;
        }
        this.whiteKilled.forEach(p->
                System.out.print(" ["+p.getName()+"]"));
    }
    public void getBlackKilled(){
        System.out.println("Black killed");
        if(getBlackKC()==0){
            System.out.println();
            return;
        }
        this.blackKilled.forEach(p->
                System.out.print(" ["+p.getName()+"]"));
    }
    public Piece getPiece(int x, int y) {
        if(this.board[y][x]==null){
            return null;
        }
        return this.board[y][x];
    }
    public void setPiece(int x, int y, Piece piece) {
        if(piece!=null){
            piece.setPosition(x, y);
        }
        this.board[y][x] = piece;
    }
    // 왕 체크, 말 제거
    public boolean removePiece(int x, int y){
        if(this.board[y][x].getName().equals("K")){
            if(this.board[y][x].getColor().equals("white")){
                this.whiteKingKilled=true;
            }
            else{
                this.blackKingKilled=true;
            }
        }
        this.board[y][x] = null;
        return true;
    }

    public boolean movePiece(Pair pos, Piece p) {
        // 나중에 없앨수도 있는 문장
        // p.getMoves()로 움직일 수 있는 장소인지 판단 필요함
        // 아래 로직을 대부분 변경해야함
        // 1. 말 null처리
        ArrayList<Pair> moves;
        int px;
        int py;
        int x = pos.getX();
        int y = pos.getY();
        if(p!=null){
            px = p.getPosition().getX();
            py = p.getPosition().getY();
            moves = p.getMoves();
        }
        else{
            return false;
        }
        // 2. pos와 moves 대조해서 맞는거있으면 진행
        if(moves.contains(pos)){
            // 3. 적 잡았는지 출력
            if(getPiece(x, y) != null){
                if(!(getPiece(x, y).getColor().equals(p.getColor()))){
                    // 흑인지 백인지
                    if(p.getColor().equals("white")){
                        if(removePiece(x, y)){
                            addWhiteKilled(getPiece(x, y));
                            setPiece(x, y, p);
                        }
                        else{
                            System.out.println("말을 제거하지 못했습니다.");
                            return false;
                        }
                    }
                    else{
                        if(removePiece(x, y)){
                            addBlackKilled(getPiece(x, y));
                            setPiece(x, y, p);
                        }
                        else{
                            System.out.println("말을 제거하지 못했습니다.");
                            return false;
                        }
                    }
                }
            }
            // 움직이는 위치에 피스가 없으면 그냥 이동
            else{
                setPiece(x, y, p);
            }
            // 턴 종료
            addTime();
            toggleTurn();
            setPiece(px, py, null);
            return true;
        }
        else{
            return false;
        }
    }

    public void printBoard(){
        System.out.println("     a     b     c     d     e     f     g     h ");
        System.out.println();
        for(int row=7; row>=0; row--){
            System.out.println("  +----++----++----++----++----++----++----++----+");
            System.out.print(row+1);
            System.out.print(" ");
            for(int col=0; col<8; col++){
                Piece p = getPiece(col, row);
                if(p!=null){
                    System.out.print("|  ");
                    if(p.getColor().equals("white")){
                        System.out.print("\u001B[37m"+p.getName());
                    }
                    else{
                        System.out.print("\u001B[33m"+p.getName());
                        System.out.print("\u001B[37m");
                    }
                    System.out.print(" |");
                }
                else{
                    System.out.print("|    |");
                }
            }
            System.out.println();
        }
        System.out.println("  +----++----++----++----++----++----++----++----+");
    }
    // 입력받는거 만들고 시작하자
    public void printBoard(Piece selected){
        ArrayList<Pair> moves;
        if(selected!=null){
            selected.calMoves(this.getBoard());
            moves = selected.getMoves();
        }
        else{
            return;
        }
        System.out.println("     a     b     c     d     e     f     g     h ");
        System.out.println();
        for(int row=7; row>=0; row--){
            System.out.println("  +----++----++----++----++----++----++----++----+");
            System.out.print(row+1);
            System.out.print(" ");
            for(int col=0; col<8; col++){
                Piece p = getPiece(col, row);
                Pair pos = new Pair(col, row);
                // piece null 처리
                if(p!=null){
                    System.out.print("| ");
                    // 이동할 수 있는곳이 있는지-null 처리
                    if(!moves.isEmpty()){
                        // 현재 위치가 포함되어 있다면 실행
                        if(moves.contains(pos)){
                            // 이동할 수 있는 위치는 * 로 표기
                            if(p.getColor().equals("white")){
                                System.out.print("\u001B[37m"+"*");
                            }
                            else{
                                System.out.print("\u001B[33m"+"*");
                                System.out.print("\u001B[37m");
                            }
                        }
                        // 이동할 수 없는 위치는 공백으로 표기
                        else{
                            System.out.print(" ");
                        }
                    }
                    else{
                        System.out.print(" ");
                    }
                    // 말이 있다면 말 표기
                    if(p.getColor().equals("white")){
                        System.out.print("\u001B[37m"+p.getName());
                    }
                    else{
                        System.out.print("\u001B[33m"+p.getName());
                        System.out.print("\u001B[37m");
                    }
                    System.out.print(" |");
                }
                else{
                    // 말이 없는 빈 칸이면 이렇게 출력할것
                    if(moves.contains(pos)){
                        System.out.print("| *  |");
                    }
                    else{
                        System.out.print("|    |");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("  +----++----++----++----++----++----++----++----+");
    }
}
