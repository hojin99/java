package lamda;

// 람다식 - 익명함수
// Syntax : 매개변수 -> 단일실행문, 매개변수 -> {함수몸체}
// 함수는 파라메터로도 사용하며, 실행도 할 수 있다.
// 코드가 간결해 짐 (객체 기반에 비해)
// 잘못 사용하면 오히려 가독성이 떨어질 수 있음
public class Lamda {

    public static void main(String[] args) throws Exception {

        System.out.println("square(5) : " + square(5));
        
        MyLamda sqr = x -> x*x;
        System.out.println("lamda : " + sqr.mySquare(5));

        // 람다 함수를 리턴해서 이어서 함수 호출이 가능
        returnLamda().showSomething("Hello! ");

    }

    static int square(int x) {
        return x*x;
    }

    static MyPrint returnLamda() {
        return str -> System.out.println(str + "plus Something");
    }
    
}

interface MyLamda {
    int mySquare(int x);
}

interface MyPrint{
    void showSomething(String str);
}

