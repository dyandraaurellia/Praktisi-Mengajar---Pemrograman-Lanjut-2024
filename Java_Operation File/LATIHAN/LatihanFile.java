package LATIHAN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LatihanFile {
    public static void main(String[] args) {
        try{
            File sample = new File("C:\\Users\\dyan\\Documents\\KULIAH!\\SEMESTER 2\\PEMROGRAMAN LANJUT\\PRAKTISI MENGAJAR\\PERTEMUAN_4\\LATIHAN JAVA OF\\LATIHAN" , "sample.txt");
            System.out.println(sample.getAbsolutePath());
    
            FileReader reader = new FileReader(sample);
            BufferedReader buffer = new BufferedReader(reader);
    
            String output = buffer.readLine();
            while (output != null) {
                System.out.println(output);
                output = buffer.readLine();
            }
    
            buffer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("File tidak ada");
        }
    }
}