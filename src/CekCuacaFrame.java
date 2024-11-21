import javax.swing.JOptionPane; // Untuk menampilkan pesan dialog
import javax.swing.table.DefaultTableModel; // Untuk pengaturan model tabel jika menggunakan DefaultTableModel
import java.io.FileWriter; // Untuk menulis data ke file
import java.io.IOException; // Untuk menangani exception saat menulis ke file
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.io.FileReader;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;


public class CekCuacaFrame extends javax.swing.JFrame {


    private HashMap<String, Integer> kotaCheckCount = new HashMap<>();
    public CekCuacaFrame() {
        initComponents();
        loadWeatherDataFromFile();
    }
    
    private String getWeatherData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        
        in.close();
        conn.disconnect();
        return content.toString();
    }
    
    private void addCityToFavoritesIfFrequent(String kota) {
    // Perbarui hitungan pengecekan kota
        int count = kotaCheckCount.getOrDefault(kota, 0) + 1;
    kotaCheckCount.put(kota, count);

    // Tambahkan kota ke favorit jika sudah dicek lebih dari 3 kali dan belum ada di ComboBox
    if (count >= 3 && !isCityInComboBox(kota)) {
        locCmb.addItem(kota);
    }
}

    // Method untuk mengecek apakah kota sudah ada di ComboBox
    private boolean isCityInComboBox(String kota) {
    for (int i = 0; i < locCmb.getItemCount(); i++) {
        if (locCmb.getItemAt(i).equalsIgnoreCase(kota)) {
            return true;
        }
    }
    return false;
}
    private void loadWeatherDataFromFile() {
    DefaultTableModel model = (DefaultTableModel) dataTbl.getModel();
    
    // Set header kolom jika belum ada
    if (model.getColumnCount() == 0) {
        model.addColumn("Kota");
        model.addColumn("Cuaca");
        model.addColumn("Suhu (°C)");
    }

    model.setRowCount(0);
    
    try (BufferedReader br = new BufferedReader(new FileReader("weatherData.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 3) { // Pastikan data ada 3 kolom
                // Terjemahkan deskripsi cuaca ke Bahasa Indonesia
                String kondisiCuaca = translateWeatherDescription(data[1]);
                model.addRow(new Object[]{data[0], kondisiCuaca, data[2]});
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat data dari file CSV.");
    }
}
    
    private String translateWeatherDescription(String description) {
    switch (description.toLowerCase()) {
        case "clear sky":
            return "cerah";
        case "few clouds":
            return "berawan sebagian";
        case "scattered clouds":
            return "awan tersebar";
        case "broken clouds":
            return "awan terputus";
        case "shower rain":
            return "hujan rintik-rintik";
        case "rain":
            return "hujan";
        case "thunderstorm":
            return "badai petir";
        case "snow":
            return "salju";
        case "mist":
            return "kabut";
        default:
            return description; // Kembalikan deskripsi asli jika tidak cocok
    }
    }
    private void setWeatherIcon (String kondisiCuaca) {
    String iconPath = "";
    switch (kondisiCuaca.toLowerCase()) {
        case "cerah":
            iconPath = "/icon.cerah.jpg";
            break;
        case "langit cerah":
        case "berawan sebagian":
            iconPath = "/icon.cerahberawan.jpg";
            break;
        case "awan tersebar":
        case "awan terputus":
        case "sedikit berawan":
        case "awan mendung":
        case "awan pecah":
            iconPath = "/icon/berawan.jpg";
            break;
        case "hujan":
            iconPath = "/icon/hujan.jpg";
            break;
        case "hujan rintik-rintik":
        case "badai petir":
            iconPath = "/icon/hujanpetir.jpg";
            break;
        case "kabut":
        case "kabut asap":
        default:
            iconPath = "/icon/default.jpg"; // Ikon default jika kondisi tidak dikenali
    }
    
    // Set ikon cuaca ke iconLbl jika file gambar ditemukan
    java.net.URL imgURL = getClass().getResource(iconPath);
    if (imgURL != null) {
        iconLbl.setIcon(new javax.swing.ImageIcon(imgURL));
    } else {
        System.err.println("Gambar ikon cuaca tidak ditemukan: " + iconPath);
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        isiKotaTxt = new javax.swing.JTextField();
        locCmb = new javax.swing.JComboBox<>();
        checkButton = new javax.swing.JButton();
        resultLbl = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        iconLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTbl = new javax.swing.JTable();
        closeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(160, 164, 216));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aplikasi Cek Cuaca", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 20), new java.awt.Color(106, 30, 85))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(106, 30, 85));
        jLabel1.setText("Nama kota: ");

        locCmb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                locCmbItemStateChanged(evt);
            }
        });

        checkButton.setText("Check");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        resultLbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        resultLbl.setForeground(new java.awt.Color(106, 30, 85));
        resultLbl.setText("Result");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        dataTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane1.setViewportView(dataTbl);

        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(locCmb, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isiKotaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(checkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                        .addGap(110, 110, 110)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(205, 205, 205))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(resultLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(iconLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(252, 252, 252))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(isiKotaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(locCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(checkButton)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resultLbl)
                        .addGap(221, 221, 221))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(saveButton)
                                .addGap(36, 36, 36)
                                .addComponent(closeBtn)))
                        .addGap(32, 32, 32)
                        .addComponent(iconLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                        .addGap(26, 26, 26))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_closeBtnActionPerformed

    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
    String kota = isiKotaTxt.getText();
    String apiKey = "3494d25460dfc354c77d9433f01443f5";
    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + apiKey + "&units=metric&lang=id";
    
    try {
        // Mengambil data dari API
        String response = getWeatherData(url);
        JSONObject jsonResponse = new JSONObject(response);
        
        // Parsing data dari JSON
        String kondisiCuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        kondisiCuaca = translateWeatherDescription(kondisiCuaca); // Terjemahkan jika perlu
        double suhu = jsonResponse.getJSONObject("main").getDouble("temp");
        
        // Menampilkan hasil dalam Bahasa Indonesia
        resultLbl.setText("Cuaca: " + kondisiCuaca + ", Suhu: " + suhu + "°C");
        
        // Atur ikon cuaca
        setWeatherIcon(kondisiCuaca);
        
        // Tambahkan kota ke favorit jika sudah dicek beberapa kali
        addCityToFavoritesIfFrequent(kota);
        
    } catch (Exception e){
        resultLbl.setText("Kota tidak ditemukan atau error API.");
    }
    }//GEN-LAST:event_checkButtonActionPerformed

    private void locCmbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_locCmbItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) locCmb.getSelectedItem();
            isiKotaTxt.setText(selectedCity);
        }
    }//GEN-LAST:event_locCmbItemStateChanged

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
    String kota = isiKotaTxt.getText();
    String apiKey = "3494d25460dfc354c77d9433f01443f5"; // Ganti dengan API key Anda
    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + apiKey + "&units=metric";
    
    try {
        // Mengambil data dari API
        String response = getWeatherData(url);
        JSONObject jsonResponse = new JSONObject(response);
        
        // Parsing data dari JSON
        String kondisiCuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        double suhu = jsonResponse.getJSONObject("main").getDouble("temp");
        
        // Menampilkan hasil
        resultLbl.setText("Cuaca: " + kondisiCuaca + ", Suhu: " + suhu + "°C");

        // Tambahkan kota ke daftar favorit jika belum ada
        boolean kotaSudahAda = false;
        for (int i = 0; i < locCmb.getItemCount(); i++) {
            if (locCmb.getItemAt(i).equalsIgnoreCase(kota)) {
                kotaSudahAda = true;
                break;
            }
        }
        if (!kotaSudahAda) {
            locCmb.addItem(kota); // Tambahkan kota ke JComboBox
        }

        // Menyimpan data ke file CSV
        try (FileWriter writer = new FileWriter("weatherData.csv", true)) {
            writer.append(kota).append(",").append(kondisiCuaca).append(",").append(String.valueOf(suhu)).append("\n");
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke weatherData.csv.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke file CSV.");
        }
        
    } catch (Exception e) {
        resultLbl.setText("Kota tidak ditemukan atau error API.");
    }
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CekCuacaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkButton;
    private javax.swing.JButton closeBtn;
    private javax.swing.JTable dataTbl;
    private javax.swing.JLabel iconLbl;
    private javax.swing.JTextField isiKotaTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> locCmb;
    private javax.swing.JLabel resultLbl;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
