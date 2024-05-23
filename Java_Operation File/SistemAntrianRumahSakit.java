package LATIHAN;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

class Pasien implements Comparable<Pasien> {
    private String nama;
    private int prioritas;
    private LocalDate tanggalDaftar;

    public Pasien(String nama, int prioritas, LocalDate tanggalDaftar) {
        this.nama = nama;
        this.prioritas = prioritas;
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getNama() {
        return nama;
    }

    public int getPrioritas() {
        return prioritas;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    @Override
    public int compareTo(Pasien other) {
        int comparePrioritas = Integer.compare(this.prioritas, other.prioritas);
        if (comparePrioritas != 0) {
            return comparePrioritas;
        } else {
            return this.tanggalDaftar.compareTo(other.tanggalDaftar);
        }
    }
}

public class SistemAntrianRumahSakit {
    private static final String FILE_NAME = "data_antrian.txt"; // Ubah nama file sementara untuk debugging

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Pasien> antrian = new LinkedList<>();

        // Load existing data from file
        antrian.addAll(loadAntrianFromFile());

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
                                break; 
                            } else {
                                System.out.println("Prioritas tidak valid. Harap masukkan angka antara 1 dan 5.");
                            }
                        }
                        Pasien pasien = new Pasien(nama, prioritas, LocalDate.now());
                        antrian.add(pasien);
                        antrian.sort(null); // Sort based on priority and date
                        System.out.println("Pasien telah ditambahkan.");

                        // Save the updated queue to the file
                        simpanData(antrian);
                    } catch (NumberFormatException e) {
                        System.out.println("Prioritas tidak valid. Harap masukkan angka antara 1 dan 5.");
                        scanner.nextLine(); 
                    }
                } else if (pilihan == 2) {
                    if (antrian.isEmpty()) {
                        System.out.println("Antrian kosong.");
                    } else {
                        System.out.println("Antrian:");
                        for (Pasien pasien : antrian) {
                            System.out.println("Nama: " + pasien.getNama() + ", Prioritas: " + pasien.getPrioritas() + ", Tanggal Daftar: " + pasien.getTanggalDaftar());
                        }
                    }
                } else if (pilihan == 3) {
                    System.out.println("Terima kasih telah menggunakan sistem antrian rumah sakit.");
                    break;
                } else {
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka antara 1 dan 3.");
                scanner.nextLine(); 
            }
        }

        scanner.close();
    }

    private static List<Pasien> loadAntrianFromFile() {
        List<Pasien> antrian = new LinkedList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                int prioritas = Integer.parseInt(data[1]);
                LocalDate tanggalDaftar = LocalDate.parse(data[2]);

                Pasien pasien = new Pasien(nama, prioritas, tanggalDaftar);
                antrian.add(pasien);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat data antrian dari file: " + e.getMessage());
        }

        return antrian;
    }

    private static void simpanData(List<Pasien> antrian) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Pasien pasien : antrian) {
                String data = String.format("%s,%d,%s\n", pasien.getNama(), pasien.getPrioritas(), pasien.getTanggalDaftar());
                bufferedWriter.write(data);
            }
            System.out.println("Data antrian telah disimpan.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data antrian ke file: " + e.getMessage());
        }
    }
}

