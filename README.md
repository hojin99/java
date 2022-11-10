# java

## 환경
VS Code에서 Build Tool로 프로젝트 생성 (명령 팔레트 -> Create Java Project)  
java 언어 및 sdk에 대한 간단한 테스트이므로 java 파일 단위로 쉽게 테스트 해볼 수 있도록 구성 

## 실행  
VS Code에서 Run Java로 빌드,실행  

## 참조  
https://docs.oracle.com/javase/tutorial/index.html  
http://www.tcpschool.com/java/intro

### java io 테스트 (src/iotest)
250MB 텍스트 파일을 읽어서 다른 파일로 쓰기  
적절히 버퍼링하면 성능 향상
java nio MDA를 통해 성능 향상  
line단위 처리가 필요하다면, reader, writer를 사용하는 게 나을 듯  

* BufferedOutputStream에서 byte 단위로 읽어서 쓰기  
처리시간(ms) : 1534  
* BufferedOutputStream에서 약 1mb 단위 버퍼로 읽어서 쓰기  
처리시간(ms) : 252  
* FileChannel MDA(Memory Direct Access)를 통해서 1mb 단위 버퍼로 읽어서 쓰기  
처리시간(ms) : 128  
* BufferedWriter를 통해서 line단위로 읽어서 쓰기  
처리시간(ms) : 2021  
* FileChannel MDA를 통해서 line단위 가공해서 읽어서 쓰기  
처리시간(ms) : 22047  

참고 
- https://taes-k.github.io/2021/01/06/java-nio/  
- https://bcho.tistory.com/288  

