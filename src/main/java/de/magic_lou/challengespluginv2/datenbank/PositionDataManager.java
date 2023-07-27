package de.magic_lou.challengespluginv2.datenbank;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PositionDataManager {

    public static void addNewPosition(SavedPosition position) {
        //INSERT INTO "main"."savedPositions"("Name","X","Y","Z","Dm") VALUES (?,?,?,?,?);
        PreparedStatement statemant = null;
        try {
            statemant = DataBaseConnection.con.prepareStatement("INSERT INTO \"main\".\"savedPositions\"(\"Name\",\"X\",\"Y\",\"Z\",\"Dm\",\"RunID\") VALUES (?,?,?,?,?,?)");
            statemant.setString(1, position.name);
            statemant.setInt(2, position.x);
            statemant.setInt(3, position.y);
            statemant.setInt(4, position.z);
            statemant.setString(5, position.world);
            statemant.setInt(6, position.runID);
            statemant.executeUpdate();
            statemant.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static class SavedPosition {
        public String name;
        public int x;
        public int y;
        public int z;
        public String world;
        public int runID;

        public SavedPosition(String name, int x, int y, int z, String world, int runID) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.world = world;
            this.runID = runID;
        }
    }


}
