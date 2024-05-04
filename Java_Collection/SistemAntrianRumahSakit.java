import java.util.*;

class Pasien implements Comparable<Pasien> {
    private String nama;
    private int prioritas;

    public Pasien(String nama, int prioritas) {
        this.nama = nama;
        this.prioritas = prioritas;
    }

    public String getNama() {
        return nama;
    }

    public int getPrioritas() {
        return prioritas;
    }

    @Override
    public int compareTo(Pasien other) {
        return Integer.compare(this.prioritas, other.prioritas);
    }
}


public class SistemAntrianRumahSakit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Pasien> antrian = new PriorityQueue<>();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Pasien");
            System.out.println("2. Lihat Antrian");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();

            if (pilihan == 1) {
                System.out.print("Masukkan nama pasien: ");
                String nama = scanner.next();
                System.out.print("Masukkan prioritas pasien (1-5): ");
                int prioritas = scanner.nextInt();
                Pasien pasien = new Pasien(nama, prioritas);
                antrian.add(pasien);
                System.out.println("Pasien telah ditambahkan.");
            }
            
            else if (pilihan == 2) {
                System.out.println("Antrian:");
                while (!antrian.isEmpty()) {
                    Pasien pasien = antrian.poll();
                    System.out.println("Nama: " + pasien.getNama() + ", Prioritas: " + pasien.getPrioritas());
                }
            }
            
            else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan sistem antrian rumah sakit.");
                break;
            }
            else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        
        scanner.close();
    }
}

