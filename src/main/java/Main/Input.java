package Main;

import Piece.Pair;

import java.util.Scanner;

public class Input {
    private Scanner scanner;
    private boolean selected;
    private boolean flag;
    public Input() {
        scanner = new Scanner(System.in);
        this.selected = false;
        this.flag = false;
    }
    // 사용자 입력 받는 함수
    public Pair getCoord(){
        while(true){
            System.out.print("좌표를 입력하세요 (e.g., e2 a4, 종료=exit, 취소=cancel): ");
            String move = scanner.nextLine();
            if(move.equalsIgnoreCase("exit")){
                this.setFlag(true);
                return null;
            }
            else if(move.equalsIgnoreCase("cancel")){
                this.setSelected(false);
                return null;
            }
            else if(move.length()!=2){
                System.out.println("정확한 좌표를 입력해주세요. (e.g., e2, a4");
            }
            else if(((move.charAt(0)>='a') && (move.charAt(0)<='h')) &&
            ((move.charAt(1)>='1') && (move.charAt(1)<='8'))){
                return calCoord(move);
            }
            else{
                System.out.println("정확한 좌표를 입력해주세요. (e.g., e2, a4");
            }
        }
    }
    // e2, f2같은 문자열을 Pair좌표로 치환
    private Pair calCoord(String str){
        int x = str.charAt(0)-'a';
        int y = str.charAt(1)-'1';
        return new Pair(x,y);
    }
    public boolean getFlag(){
        return this.flag;
    }
    public void setFlag(boolean value){
        this.flag = value;
    }
    public boolean getSelected(){
        return this.selected;
    }
    public void setSelected(){
        this.selected=!this.selected;
    }
    public void setSelected(boolean value){
        this.selected=value;
    }
}
