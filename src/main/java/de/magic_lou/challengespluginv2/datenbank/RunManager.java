package de.magic_lou.challengespluginv2.datenbank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RunManager {

    public static int NextID() {
        try {
            PreparedStatement statment = DataBaseConnection.con.prepareStatement("SELECT max(ID) FROM runs");
            ResultSet rs = statment.executeQuery();
            rs.next();
            Integer nextID = rs.getInt(1) + 1;
            if (rs.wasNull()) nextID = 0;
            rs.close();
            statment.close();
            return nextID;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    public static void newRun(Run run) {
        //"INSERT INTO "main"."runs"("ID","Start","End","Timer") VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = DataBaseConnection.con.prepareStatement("INSERT INTO \"main\".\"runs\"(\"ID\",\"Start\",\"End\",\"Timer\") VALUES (?,?,NULL,NULL)");
            statement.setInt(1, run.id);
            statement.setLong(2, run.start);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void endRun(Run run) {
        //"INSERT INTO "main"."runs"("ID","Start","End","Timer") VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = DataBaseConnection.con.prepareStatement("UPDATE \"main\".\"runs\" SET \"End\"=?,\"Timer\"=? WHERE \"ID\"=?");
            statement.setLong(1, run.end);
            statement.setInt(2, run.timer);
            statement.setInt(3, run.id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static class Run {
        public int id;
        public long start;
        public Long end;
        public Integer timer;

        public Run(int id, long start, Long end, Integer timer) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.timer = timer;
        }
    }

}
