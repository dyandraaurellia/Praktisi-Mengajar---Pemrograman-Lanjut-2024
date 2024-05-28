package JAVA_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class LatihanGUI {

    static class Pasien implements Comparable<Pasien> {
        private String nama;
        private int prioritas;
        private LocalDateTime tanggalDaftar;

        public Pasien(String nama, int prioritas, LocalDateTime tanggalDaftar) {
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

        public LocalDateTime getTanggalDaftar() {
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

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return String.format("Nama: %s, Prioritas: %d, Tanggal Daftar: %s", nama, prioritas, tanggalDaftar.format(formatter));
        }
    }

    static class SistemAntrianRumahSakit {
        private static final String FILE_NAME = "data_antrian.txt";
        private final LinkedList<Pasien> antrian = new LinkedList<>();
        private final LinkedList<Pasien> antrianSelesai = new LinkedList<>();
        private Pasien sedangDiproses = null;

        public SistemAntrianRumahSakit() {
            antrian.addAll(loadAntrianFromFile());
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            JFrame frame = new JFrame("Sistem Antrian Rumah Sakit");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel mainPanel = new JPanel(new BorderLayout());
            frame.add(mainPanel);

            JPanel topPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel namaLabel = new JLabel("Nama Pasien:");
            topPanel.add(namaLabel, gbc);

            gbc.gridx++;
            JTextField namaTextField = new JTextField(20);
            topPanel.add(namaTextField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JLabel prioritasLabel = new JLabel("Prioritas (1-5):");
            topPanel.add(prioritasLabel, gbc);

            gbc.gridx++;
            JTextField prioritasTextField = new JTextField(20);
            topPanel.add(prioritasTextField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            JButton tambahButton = new JButton("Tambah Pasien");
            topPanel.add(tambahButton, gbc);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

            JTextArea antrianArea = new JTextArea(20, 50);
            antrianArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(antrianArea);
            centerPanel.add(scrollPane);

            JPanel bottomPanel = new JPanel(new FlowLayout());

            JButton prosesButton = new JButton("Proses Antrian");
            bottomPanel.add(prosesButton);

            JButton selesaiButton = new JButton("Antrian Selesai");
            bottomPanel.add(selesaiButton);

            JButton resetButton = new JButton("Reset Antrian");
            bottomPanel.add(resetButton);

            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            tambahButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nama = namaTextField.getText();
                    int prioritas;
                    try {
                        prioritas = Integer.parseInt(prioritasTextField.getText());
                        if (prioritas < 1 || prioritas > 5) {
                            throw new NumberFormatException();
                        }
                        Pasien pasien = new Pasien(nama, prioritas, LocalDateTime.now());
                        antrian.add(pasien);
                        antrian.sort(null); 
                        simpanData(antrian);
                        namaTextField.setText("");
                        prioritasTextField.setText("");
                        JOptionPane.showMessageDialog(frame, "Pasien telah ditambahkan.");
                        refreshAntrianArea(antrianArea);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Prioritas tidak valid. Harap masukkan angka antara 1 dan 5.");
                    }
                }
            });

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    antrian.clear();
                    antrianSelesai.clear();
                    sedangDiproses = null;
                    simpanData(antrian);
                    antrianArea.setText("");
                    JOptionPane.showMessageDialog(frame, "Antrian telah direset.");
                }
            });

            prosesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (antrian.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Tidak ada antrian untuk diproses.");
                    } else {
                        sedangDiproses = antrian.poll();
                        JOptionPane.showMessageDialog(frame, "Pasien " + sedangDiproses.getNama() + " sedang diproses.");
                        refreshAntrianArea(antrianArea);
                    }
                }
            });

            selesaiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (sedangDiproses == null) {
                        JOptionPane.showMessageDialog(frame, "Tidak ada pasien yang sedang diproses.");
                    } else {
                        antrianSelesai.add(sedangDiproses);
                        sedangDiproses = null;
                        JOptionPane.showMessageDialog(frame, "Pasien selesai diproses.");
                        refreshAntrianArea(antrianArea);
                    }
                }
            });

            frame.setVisible(true);
        }

        private void refreshAntrianArea(JTextArea antrianArea) {
            StringBuilder sb = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            sb.append("Daftar Antrian:\n");
            for (Pasien pasien : antrian) {
                sb.append("Nama: ").append(pasien.getNama()).append(", Prioritas: ").append(pasien.getPrioritas())
                  .append(", Tanggal Daftar: ").append(pasien.getTanggalDaftar().format(formatter)).append("\n");
            }
            if (sedangDiproses != null) {
                sb.append("\nAntrian yang sedang diproses:\n");
                sb.append(sedangDiproses.toString()).append("\n");
            }
            sb.append("\nAntrian yang sudah selesai:\n");
            for (Pasien pasien : antrianSelesai) {
                sb.append(pasien.toString()).append("\n");
            }
            antrianArea.setText(sb.toString());
        }

        private List<Pasien> loadAntrianFromFile() {
            List<Pasien> antrian = new LinkedList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(",");
                    String nama = data[0];
                    int prioritas = Integer.parseInt(data[1]);
                    LocalDateTime tanggalDaftar = LocalDateTime.parse(data[2]);
                    Pasien pasien = new Pasien(nama, prioritas, tanggalDaftar);
                    antrian.add(pasien);
                }
            } catch (IOException e) {
                System.out.println("Gagal memuat data antrian dari file: " + e.getMessage());
            }
            return antrian;
        }

        private void simpanData(List<Pasien> antrian) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Pasien pasien : antrian) {
                    String data = String.format("%s,%d,%s\n", pasien.getNama(), pasien.getPrioritas(), pasien.getTanggalDaftar());
                    bufferedWriter.write(data);
                }
            } catch (IOException e) {
                System.out.println("Gagal menyimpan data antrian ke file: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            new SistemAntrianRumahSakit();
        }
    }
}
