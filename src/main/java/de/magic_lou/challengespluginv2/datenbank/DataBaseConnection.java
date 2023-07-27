package de.magic_lou.challengespluginv2.datenbank;

import de.magic_lou.challengespluginv2.Main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    public static Connection con;

    public void connect() throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:sqlite:data.db");
        try (Statement statement = con.createStatement()) {
            InputStream stream = Main.instance.getResource("init.sql");
            assert stream != null;
            byte[] buf = new byte[stream.available()];
            stream.read(buf);
            String sql = new String(buf);
            for (String s : sql.split(";")) {
                statement.execute(s + ";");
            }
        }
    }

    public void disconnect() throws SQLException {
        con.close();
    }
}