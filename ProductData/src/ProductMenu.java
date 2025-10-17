import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // koneksi ke database
    private final Database database;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JRadioButton baruRadioButton;
    private JLabel kondisiLabel;
    private JRadioButton bekasRadioButton;
    private JRadioButton bekasBagusRadioButton;
    private JRadioButton lamaRadioButton;
    private ButtonGroup kondisiButtonGroup;

    // constructor
    public ProductMenu() {
        // buat objek database
        database = new Database();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = {"????", "Elektronik", "Makanan", "Pakaian", "Alat Tulis", "Minuman"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Inisialisasi dan pengelompokan JRadioButton
        kondisiButtonGroup = new ButtonGroup();
        kondisiButtonGroup.add(baruRadioButton);
        kondisiButtonGroup.add(bekasRadioButton);
        kondisiButtonGroup.add(bekasBagusRadioButton);
        kondisiButtonGroup.add(lamaRadioButton);

        // Atur pilihan default, misalnya "Baru"
        baruRadioButton.setSelected(true);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(ProductMenu.this,
                        "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value dari tabel
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curKondisi = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield, combo box, dan radio button
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                switch (curKondisi) {
                    case "Baru":
                        baruRadioButton.setSelected(true);
                        break;
                    case "Bekas":
                        bekasRadioButton.setSelected(true);
                        break;
                    case "Bekas Bagus":
                        bekasBagusRadioButton.setSelected(true);
                        break;
                    case "Lama":
                        lamaRadioButton.setSelected(true);
                        break;
                }

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Kondisi"};
        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");

            int i = 1;
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = i;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("kondisi");
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tmp;
    }

    private boolean isInputValid() {
        if (idField.getText().isEmpty() || namaField.getText().isEmpty() || hargaField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom input harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (kategoriComboBox.getSelectedItem().toString().equals("????")) {
            JOptionPane.showMessageDialog(this, "Silakan pilih kategori produk.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void insertData() {
        // Validasi input
        if (!isInputValid()) {
            return;
        }

        try {
            String id = idField.getText();

            // Cek duplikasi ID
            String checkSql = "SELECT * FROM product WHERE id = '" + id + "'";
            ResultSet rs = database.selectQuery(checkSql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "ID Produk sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ambil value dari form
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = "";
            if (baruRadioButton.isSelected()) kondisi = "Baru";
            else if (bekasRadioButton.isSelected()) kondisi = "Bekas";
            else if (bekasBagusRadioButton.isSelected()) kondisi = "Bekas Bagus";
            else if (lamaRadioButton.isSelected()) kondisi = "Lama";

            // Tambahkan data ke dalam database
            String sql = "INSERT INTO product (id, nama, harga, kategori, kondisi) VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + kondisi + "')";
            database.insertUpdateDeleteQuery(sql);

            // Update tabel
            productTable.setModel(setTable());
            // Bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData() {
        // Validasi input
        if (!isInputValid()) {
            return;
        }

        try {
            // Ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = "";
            if (baruRadioButton.isSelected()) kondisi = "Baru";
            else if (bekasRadioButton.isSelected()) kondisi = "Bekas";
            else if (bekasBagusRadioButton.isSelected()) kondisi = "Bekas Bagus";
            else if (lamaRadioButton.isSelected()) kondisi = "Lama";

            // Ambil ID original dari baris yang dipilih
            String originalId = productTable.getValueAt(selectedIndex, 1).toString();

            // Update data di database
            String sql = "UPDATE product SET id = '" + id + "', nama = '" + nama + "', harga = " + harga + ", kategori = '" + kategori + "', kondisi = '" + kondisi + "' WHERE id = '" + originalId + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            productTable.setModel(setTable());
            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // Ambil ID dari baris yang dipilih
        String idToDelete = productTable.getValueAt(selectedIndex, 1).toString();

        // Hapus data dari database
        String sql = "DELETE FROM product WHERE id = '" + idToDelete + "'";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        productTable.setModel(setTable());
        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        baruRadioButton.setSelected(true);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}