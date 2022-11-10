package iotest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class IoTest {

    private static final String SOURCE_FILE_PATH = "C:\\work\\java\\src\\iotest\\input.txt";
    private static final String RESULT_FILE_PATH = "C:\\work\\java\\src\\iotest\\output.txt";
    private static final int TEST_CNT = 5;

    public static void main(String[] args) {
        new IoTest().test();
    }

    public IoTest() {

    }

    /*
     * test 실행
     */
    public void test() {
        File file = new File(RESULT_FILE_PATH);

        for(int i=0; i<TEST_CNT; i++) {
            if(file.exists()) file.delete();

            long beforeTime = System.currentTimeMillis(); 

            Test test = getTest(i+1);

            test.run();

            long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
            System.out.println("시간차이(ms) : "+secDiffTime);
        }
    }

    public Test getTest(int cnt)
    {
        Test testFunc = null;

        if(cnt == 1) testFunc = getTest1();
        else if(cnt == 2) testFunc = getTest2();
        else if(cnt == 3) testFunc = getTest3();
        else if(cnt == 4) testFunc = getTest4();
        else if(cnt == 5) testFunc = getTest5();

        return testFunc;
    }

    Test getTest1() {
        return () -> {
            System.out.println("# BufferedOutputStream int");

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(SOURCE_FILE_PATH));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(RESULT_FILE_PATH, true));
            )
            {
                int c;
                while ((c = bis.read()) != -1)
                {
                    bos.write(c);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }

    Test getTest2() {
        return () -> {
            System.out.println("# BufferedOutputStream buffer");

            final int bufferMaxSize = 1024 * 1024;
            byte[] byteBuff = new byte[bufferMaxSize];
            
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(SOURCE_FILE_PATH));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(RESULT_FILE_PATH, true));
            )
            {
                while ((bis.read(byteBuff)) != -1)
                {
                    bos.write(byteBuff);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }

    Test getTest3() {
        return () -> {
            System.out.println("# FileChannel MDA");

            final int bufferMaxSize = 1024 * 1024;
 
            Path sourceFilePath = Paths.get(SOURCE_FILE_PATH);
            Path resultFilePath = Paths.get(RESULT_FILE_PATH);
         
            try (FileChannel sourceChannel = FileChannel.open(sourceFilePath, StandardOpenOption.READ);
                 FileChannel resultChannel = FileChannel.open(resultFilePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            )
            {
                ByteBuffer buffer = ByteBuffer.allocateDirect(bufferMaxSize);
         
                while (sourceChannel.read(buffer) != -1)
                {
                    buffer.flip();
                    resultChannel.write(buffer);
                    buffer.clear();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }

    Test getTest4() {
        return () -> {
            System.out.println("# BufferedWriter");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(SOURCE_FILE_PATH)));
            BufferedWriter bw = new BufferedWriter(new FileWriter(RESULT_FILE_PATH, true));
       )
       {
           String line;
           while ((line = br.readLine()) != null)
           {
               bw.write(line);
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        };
    }
    
    Test getTest5() {
        return () -> {
            System.out.println("# FileChannel MDA line처리");

            final int bufferMaxSize = 1024 * 1024;
            final byte lf = 0x0A;
         
            Path sourceFilePath = Paths.get(SOURCE_FILE_PATH);
            Path resultFilePath = Paths.get(RESULT_FILE_PATH);
         
            try (FileChannel sourceChannel = FileChannel.open(sourceFilePath, StandardOpenOption.READ);
                 FileChannel resultChannel = FileChannel.open(resultFilePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            )
            {
                long channelCursor = 0;
                ByteBuffer buffer = ByteBuffer.allocateDirect(bufferMaxSize);
                ByteBuffer writeBuffer = ByteBuffer.allocateDirect(bufferMaxSize);
         
                int bufferReadSize;
                while ((bufferReadSize = sourceChannel.read(buffer)) >= 0)
                {
                    int bufferCursor = 0;
         
                    buffer.flip();
         
                    byte b;
                    try
                    {
                        while ((b = buffer.get()) != -1)
                        {
                            writeBuffer.put(b);
         
                            if (b == lf || (bufferReadSize < bufferMaxSize && buffer.position() + 1 == bufferReadSize))
                            {
                                writeBuffer.flip();
                                resultChannel.write(writeBuffer);
         
                                writeBuffer.clear();
                                bufferCursor = buffer.position();
                            }
         
                        }
                    }
                    catch (BufferUnderflowException e)
                    {
                        //skip
                    }
         
                    channelCursor += bufferCursor;
         
                    buffer.clear();
                    sourceChannel.position(channelCursor);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }

    @FunctionalInterface
    interface Test {
        void run();
    }

}
