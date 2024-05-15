package LATIHAN;

public class checkException {
    public static void main(String arg[]){
        try{
            int []arr = new int[1];
            System.out.println(arr[1]);
            System.out.println("Baris ini tidak pernah dieksekusi");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Terjadi eksepsi karena index melebihi kapasitas");
                } 
            System.out.println("Setelah blok try-catch");
          }
        }
