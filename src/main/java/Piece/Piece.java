package Piece;

import java.util.ArrayList;

public class Piece {
    private final String color;
    private String name;
    private Pair position;
    private ArrayList<Pair> moves;

    public Piece(String color, int x, int y) {
        this.moves = new ArrayList<>();
        this.color = color;
        this.position = new Pair(x, y);
    }
    public Piece(String color, String name, int x, int y) {
        this.moves = new ArrayList<>();
        this.color = color;
        this.name = name;
        this.position = new Pair(x, y);
    }
    public String getColor() {
        return this.color;
    }
    public String getName(){
        return this.name;
    }
    public Pair getPosition(){
        return this.position;
    }
    public void setPosition(int x, int y){
        this.position.setXY(x, y);
    }
    public void addMoves(int x, int y){
        this.moves.add(new Pair(x, y));
    }
    public void resetMoves(){
        this.moves.clear();
    }
    public void calMoves(Piece[][] board){
        return;
    }
    public ArrayList<Pair> getMoves(){
        if(this.moves==null){
            return null;
        }
        return this.moves;
    }
    public boolean canPromotion(){
        return false;
    }
    public Piece promotion(String str){
        return null;
    }

}
