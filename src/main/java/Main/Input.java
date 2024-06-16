package Main;

import Piece.Pair;
import java.io.*;
import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

// Timer와 Executor, Callable, Future로 타이머에 연동된 비동기 입력 구현
// 해당 문제를 해결하느라 코드가 많이 복잡합니다..
public class Input {
    private boolean selected;
    private boolean flag;
    private int timeout;
    private final TimeUnit unit;
    private boolean timedOut;
    private String move;

    public Input() {
        this.selected = false;
        this.flag = false;
        this.unit = TimeUnit.SECONDS;
        this.timedOut = false;
        this.move = "";
    }
    // 스트림 읽어오는 ConsoleInput 클래스
    // https://www.javaspecialists.eu/archive/Issue153-Timeout-on-Console-Input.html
    // https://stackoverflow.com/questions/4983065/how-to-interrupt-java-util-scanner-nextline-call
    // Scanner없이 BufferedReader를 사용해서 입력 받아오기
    // 바이트코드만 받아오니까 예외처리를 제대로 해야함
    private static class ConsoleInput implements Callable<String>{
        public String input = "";
        public String call(){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\n좌표를 입력하세요 (e.g., e2 a4, 종료=exit, 취소=cancel): ");
            try{
                while(!br.ready()){
                    // 버퍼 입력될때까지(엔터키) 대기
                    // 빈 while문 경고가 발생하는데, 무시
                }
                this.input = br.readLine();
            } catch(IOException e){
                System.out.println("예외 발생: "+e);
                return "";
            }
            return this.input;
        }
    }
    // 15초 타이머 실행하고 String을 읽어오고 Pair를 리턴하는 함수
    // Future.get()으로 String을 읽어옴
    // 이후 String 입력값에 따라 처리
    public Pair getCoord(){
        ExecutorService ex = Executors.newSingleThreadExecutor();
        Timer m_timer = new Timer();
        // Timer와 연동되었던 예전의 timeout을 초기화 해줘야함
        this.timeout = 15;
        // 타이머 실행
        m_timer.schedule(new TimerTask() {
            private int time=15;
            @Override
            public void run() {
                if(time>=0){
                    System.out.println("남은 시간: "+time--);
                    // timeout을 여기서 변경하면 timer와 연동된 timeout이 가능할까?
                    timeout = time;
                }
                else{
                    m_timer.cancel();
                }
            }
        }, 0, 1000);
        try{
            do{
                Future<String> result = ex.submit(new ConsoleInput());
                try{
                    move = result.get(timeout, unit);
                    // --------예외 처리 구문----------
                } catch(ExecutionException e){
                    result.cancel(true);
                    m_timer.cancel();
                    System.out.println("ExecutionException: "+e);
                } catch(TimeoutException e){
                    result.cancel(true);
                    m_timer.cancel();
                    this.timedOut = true;
                    System.out.println("Timeout, input is null");
                    return null;
                } catch(InterruptedException e){
                    result.cancel(true);
                    m_timer.cancel();
                    this.timedOut = true;
                    System.out.println("Thread interrupted, input is null");
                    return null;
                }
                // --------------------------------
                // 입력: exit, 동작: 프로그램 종료
                if (move.equalsIgnoreCase("exit")) {
                    this.setFlag(true);
                    result.cancel(true);
                    ex.shutdown();
                    // 타이머 종료되는시점에 입력해서 cancel이 되지 않는 오류 방지
                    if(timeout>=0){
                        m_timer.cancel();
                    }
                    return null;

                // 입력: cancel, 동작: 기물 선택 취소
                } else if (move.equalsIgnoreCase("cancel")) {
                    this.setSelected(false);
                    result.cancel(true);
                    ex.shutdown();
                    if(timeout>=0){
                        m_timer.cancel();
                    }
                    return null;

                // 예외 처리
                } else if (move.length() != 2) {
                    System.out.println("정확한 좌표를 입력해주세요. (e.g., e2, a4");
                    move="";

                } else if (((move.charAt(0) >= 'a') && (move.charAt(0) <= 'h')) &&
                        ((move.charAt(1) >= '1') && (move.charAt(1) <= '8'))) {
                    break;

                } else {
                    System.out.println("정확한 좌표를 입력해주세요. (e.g., e2, a4");
                    move="";
                }
                // Condition move.isEmpty() is always 'true'
                // 해당 경고는 무시해도됨
            }while(move.isEmpty());
        } finally {
            ex.shutdown();
        }
        if(timeout>=0){
            m_timer.cancel();
        }

        return calCoord(move);
    }

    // e2, f2같은 문자열을 Pair좌표로 치환
    private Pair calCoord(String str) {
        int x = str.charAt(0) - 'a';
        int y = str.charAt(1) - '1';
        return new Pair(x, y);
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean value) {
        this.flag = value;
    }

    public boolean getSelected() {
        return this.selected;
    }
    public boolean isTimedOut(){
        return this.timedOut;
    }

    public void setSelected(boolean value) {
        this.selected = value;
    }
}
