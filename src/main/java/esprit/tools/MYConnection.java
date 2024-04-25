package esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYConnection {
    public String url="jdbc:mysql://localhost:3306/experiencetunisia2";
    public String login="root";
    public String pwd="";
    private Connection cnx;
    static MYConnection instance;
    public MYConnection(){
        try {
            cnx=DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexon etablie!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public Connection getCnx(){
        return cnx;
    }

    public static MYConnection getInstance() {
        if(instance == null)
            instance = new MYConnection();
        return instance;

    }
}
