public class objMydata {
 
    public static void main(String[] args) {

        clsMydata<String> strData = new clsMydata<>("Dyandra Aurellia");
        clsMydata<Integer> intData = new clsMydata<>(19 );

        System.out.println("Umur : " + intData.getData());
        System.out.println("Nama: " + strData.getData());

        intData.setData(20);
        strData.setData("Updated");

        System.out.println("Updated Umur: " + intData.getData());
        System.out.println("Updated Nama: " + strData.getData());
    }
}