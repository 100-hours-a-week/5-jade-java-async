package Main;

import Piece.Pair;
import Piece.Piece;

import java.util.Scanner;

public class Main {
    private static void printTurn(boolean turn){
        if(turn){
            System.out.println("백의 차례입니다.");
        }
        else{
            System.out.println("흑의 차례입니다.");
        }
    }
    private static void startPromotion(Board board, Piece current){
        Scanner scanner = new Scanner(System.in);
        String prom;
        Piece p = null;
        while(p==null){
            System.out.println("프로모션이 가능합니다. (입력: Queen, Rook, Knight, Bishop): ");
            prom = scanner.nextLine();
            p = current.promotion(prom);
        }
        board.setPiece(current.getPosition().getX(), current.getPosition().getY(), p);
    }
    // 1. 게임 도중 cancel을 입력했을때 발생하는 오류가 있음 - 해결
    // 2. 폰 이동방향 계산 버그 - 해결
    public static void main(String[] args) {
        Board board = new Board();
        Input input = new Input();
        Piece current = null;
        String color;
        Pair pos = null;
        while(true){
            // 게임 종료
            if(input.getFlag()){
                break;
            }
            // 턴 확인용
            if(board.getTurn()){
                color="white";
            }
            else{
                color="black";
            }
            // 말이 선택되었을때
            if(input.getSelected()){
                while(true){
                    current = board.getPiece(pos.getX(), pos.getY());
                    // 해당위치에 말이 없으면(혹시모를 오류처리)
                    if(current == null){
                        input.setSelected(false);
                        System.out.println("말이 없습니다.");
                        break;
                    }
                    // 자기꺼 아니면
                    else if(!current.getColor().equals(color)){
                        input.setSelected(false);
                        System.out.println(color+"의 말이 아닙니다. 다시 선택해주세요.");
                        break;
                    }
                    // 체스판 출력(말의 이동범위) 후 입력(말이 이동할곳)
                    printTurn(board.getTurn());
                    board.printBoard(current);
                    pos = input.getCoord();
                    if(pos != null){
                        // 말을 옮기는데 성공하면 break, 실패하면 continue
                        if(!board.movePiece(pos, current)){
                            System.out.println("그곳으로 이동할 수 없습니다.");
                        }
                        else{
                            // 이동하면 오는 블럭
                            input.setSelected(false);
                            // 폰 프로모션 체크
                            if(current.getName().equals("P")&&current.canPromotion()){
                                startPromotion(board, current);
                            }
                            // 승리조건 체크
                            if(board.getWhiteKC()==16||board.getWhiteKingKilled()){
                                System.out.println("흑 승리");
                                input.setFlag(true);
                            }
                            else if(board.getBlackKC()==16||board.getBlackKingKilled()){
                                System.out.println("백 승리");
                                input.setFlag(true);
                            }
                            break;
                        }
                    }
                    // pos가 null이면 선택 취소
                    else{
                        input.setSelected(false);
                        break;
                    }
                }
            }
            // 말이 선택되지 않았을 때
            else{
                printTurn(board.getTurn());
                board.printBoard();
                System.out.println("말을 선택해주세요.");
                pos = input.getCoord();
                if(pos != null){
                    input.setSelected(true);
                }
            }
        }
        System.out.println("게임을 종료합니다.");
    }
}
