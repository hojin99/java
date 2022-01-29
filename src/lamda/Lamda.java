package lamda;

// 람다식 - 익명함수
// Syntax : 매개변수 -> 단일실행문, 매개변수 -> {함수몸체}
// 함수는 파라메터로도 사용하며, 실행도 할 수 있다.
// 코드가 간결해 짐 (객체 기반에 비해)
// 잘못 사용하면 오히려 가독성이 떨어질 수 있음
public class Lamda {

    public static void main(String[] args) throws Exception {

        // 람다식 사용
        MyLamda sqr = x -> x*x;
        System.out.println("lamda : " + sqr.mySquare(5));

        // 람다 함수를 리턴해서 이어서 함수 호출이 가능
        returnLamda().showSomething("Hello! ");

        // 기존 스레드 실행
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread run");
                }
            }
        ).start();
        
        // 람다식으로 스레드 실행
        new Thread(() -> System.out.println("Thread run ramda")).start();

    }

    static MyPrint returnLamda() {
        return str -> System.out.println(str + "plus Something");
    }
    
}

// 함수형 인터페이스 (단일 메소드를 가짐, 람다식은 단일 메소드 인터페이스여야 함)
// 어노테이션은 함수형 인터페이스를 확실하기 위함 (컴파일 에러), 없어도 단일 메소드라면 동작은 함
@FunctionalInterface
interface MyLamda {
    int mySquare(int x);
}

interface MyPrint{
    void showSomething(String str);
}

