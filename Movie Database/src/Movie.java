import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.sqlite.SQLiteDataSource;

public class Movie {
	private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:movies.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	public void insert(String NAME,String ACTOR,String ACTRESS,String DIRECTOR,int YEAR) {
        String sql = "INSERT INTO movie(NAME,ACTOR,ACTRESS,DIRECTOR,YEAR) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, NAME);
            pstmt.setString(2, ACTOR);
            pstmt.setString(3, ACTRESS);
            pstmt.setString(4, DIRECTOR);
            pstmt.setInt(5, YEAR);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        SQLiteDataSource ds = null;
        Movie m = new Movie();

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:movies.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println( "Opened database successfully" );
        String query = "CREATE TABLE IF NOT EXISTS movie ( " +
                "NAME TEXT PRIMARY KEY, " +
                "ACTOR TEXT NOT NULL,"+"ACTRESS TEXT NOT NULL,"+
                "DIRECTOR TEXT NOT NULL,"+"YEAR INTEGER NOT NULL )";
        
        String query1 = "SELECT * FROM movie";
        String query2 = "SELECT NAME FROM movie WHERE ACTOR='YASH'";
       try ( Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement(); ) {
   int rv = stmt.executeUpdate( query );
   m.insert("KGF","YASH","RADHIKA","PRASHANT NEEL",2018);
   m.insert("SANTHU","YASH","RADHIKA","PRASHANT NEEL",2022);
   m.insert("KURUKSHETRA","DARSHAN","RACHITA RAM","YOGRAJ BHAT",2019);
   m.insert("JAMES","PUNEETH RAJKUMAR","RAMYA","SUNI",2022);
   m.insert("GOOGLY","YASH","SAANVI","SURI",2017);
   
   ResultSet rs = stmt.executeQuery(query1);
   while (rs.next()) {  
       System.out.println(rs.getString("NAME") +  "\t" +   
                          rs.getString("ACTOR") + "\t" +  
                          rs.getString("ACTRESS")+ "\t" +
                          rs.getString("DIRECTOR")+ "\t" +
                          rs.getInt("YEAR"));  
   }
   System.out.println("---------------------------------");
   ResultSet rd= stmt.executeQuery(query2);
   while (rd.next()) {  
       System.out.println(rd.getString("NAME"));  
   }
  
       }catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }

        
    }

}