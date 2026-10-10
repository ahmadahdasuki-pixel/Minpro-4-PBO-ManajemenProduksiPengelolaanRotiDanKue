/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemmanajemenproduksi.database;
import java.sql.Connection;
import java.sql.SQLException;
import sistemmanajemenproduksi.database.Koneksi;

public class TesKoneksi {

    public static void main(String[] args) {
        try (Connection conn = Koneksi.getConnection()) {
            System.out.println("Koneksi database berhasil!");
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal.");
            System.out.println(e.getMessage());
        }
    }
}
