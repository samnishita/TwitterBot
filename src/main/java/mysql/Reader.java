/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import controllers.AddWordController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.scene.control.Label;
import org.atteo.evo.inflector.English;

/**
 *
 * @author samnishita
 */
public class Reader {

    static String jdbc = "jdbc:mysql://localhost:3306/plurals?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String username = "root";
    static String password = "BoneAppleTea2020";
    static Connection conn = null;
    private static boolean duplicates = false;
    public static Label label;

    //String filename = "INSERT FILEPATH HERE";
    public static Connection getConnection(String url) throws SQLException {
        try {
            //Java Database Connectivity (JDBC)

            //Class.forName: load the driver’s class file into memory at the runtime
            Class.forName("com.mysql.jdbc.Driver");
            //DriverManager.getConnection: establish connections
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void readFile(String filename, boolean plurals, Label label) {
        label.setText("");
        label.setVisible(true);
        duplicates = false;
        String line = "";
        try {
            String sqlInsert = "INSERT INTO misc_nouns (word) VALUES (?) ";

            //IMPORTANT: PUT DRIVER INTO DEPENDENCIES
            conn = DriverManager.getConnection(jdbc, username, password);
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

                while ((line = br.readLine()) != null) {
                    if (!line.equals("")) {

                        String goodWord = line.substring(0, 1).toUpperCase() + line.substring(1);
                        if (!plurals) {
//                            goodWord = goodWord + "s";
                            goodWord=English.plural(goodWord);
                            goodWord = line.substring(0, 1).toUpperCase() + line.substring(1);
                        }
                        stmt.setString(1, goodWord);
                        try {
                            stmt.execute();
                        } catch (SQLIntegrityConstraintViolationException ex) {
                            duplicates = true;
                        }
                    }
                }
                if (!duplicates) {
                    label.setText("Successfully added " + filename + " with all words added to database.");
                } else {
                    label.setText("Successfully added " + filename + " but there were some duplicate words.");
                }
            } catch (IOException e) {
                label.setText("Error: File Not Found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            label.setText("Error");
        }

    }

    public static String getJDBC() {
        return jdbc;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static int getDBSize() throws SQLException {
        Connection conn = DriverManager.getConnection(Reader.getJDBC(), Reader.getUsername(), Reader.getPassword());
        String sql = "SELECT * FROM misc_nouns";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        rs.last();
        int dbSize = rs.getRow();
        return dbSize;
    }

}
