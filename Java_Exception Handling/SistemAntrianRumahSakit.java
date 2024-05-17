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

            try {
                int pilihan = scanner.nextInt();

            if (pilihan == 1) {
                    try {
                        System.out.print("Masukkan nama pasien: ");
                        String nama = scanner.next();

                        int prioritas;
                        while (true) {
                            System.out.print("Masukkan prioritas pasien (1-5): ");
                            prioritas = scanner.nextInt();
                
                            if (prioritas >= 1 && prioritas <= 5) {
                                break; // Keluar dari loop jika input valid
                            } else {
                                System.out.println("Prioritas tidak valid. Harap masukkan angka antara 1 dan 5.");
                            }
                        }
                        
                        Pasien pasien = new Pasien(nama, prioritas);
                        antrian.add(pasien);
                        System.out.println("Pasien telah ditambahkan.");
                    } catch (NumberFormatException e) {
                        System.out.println("Prioritas tidak valid. Harap masukkan angka antara 1 dan 5.");
                        scanner.nextLine(); 
                    }
                } 

            else if (pilihan == 2) {
                if (antrian.isEmpty()) {
                    System.out.println("Antrian kosong.");
                } else {
                    System.out.println("Antrian:");
                    while (!antrian.isEmpty()) {
                        Pasien pasien = antrian.poll();
                        System.out.println("Nama: " + pasien.getNama() + ", Prioritas: " + pasien.getPrioritas());
                    }
                }
            }
            
            
                
            else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan sistem antrian rumah sakit.");
                break;
            }
            else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Harap masukkan angka antara 1 dan 3.");
            scanner.nextLine(); // Consume the invalid input
            }
        }
        
        scanner.close();
    }
}

