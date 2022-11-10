# java

## 실행  
VS Code에서 No Build Tool로 프로젝트 생성 (명령 팔레트 -> Create Java Project)  
VS Code에서 Run Java로 빌드,실행  

## 참조  
https://docs.oracle.com/javase/tutorial/index.html  
http://www.tcpschool.com/java/intro

### java io 테스트 (src/iotest)
250MB 텍스트 파일을 읽어서 다른 파일로 쓰기  

* BufferedOutputStream에서 byte 단위로 읽어서 쓰기  
시간차이(ms) : 1534  
* BufferedOutputStream에서 약 1mb 단위 버퍼로 읽어서 쓰기  
시간차이(ms) : 252  
* FileChannel MDA(Memory Direct Access)를 통해서 1mb 단위 버퍼로 읽어서 쓰기  
시간차이(ms) : 128  
* BufferedWriter를 통해서 line단위로 읽어서 쓰기  
시간차이(ms) : 2021  
* FileChannel MDA를 통해서 line단위 가공해서 읽어서 쓰기  
시간차이(ms) : 22047  
