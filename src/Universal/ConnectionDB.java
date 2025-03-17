package Universal;

import java.io.*;
import java.sql.*;

import javax.swing.*;

public class ConnectionDB {
	public ConnectionDB(){
		try {
			String[]args=new String[3];
			BufferedReader reader=new BufferedReader(new FileReader("C:/Users/garci/Downloads/DatosConexion.txt"));
			for(int x=0;x<3;x++) {
				args[x]=reader.readLine();
			}
			reader.close();
			connection=DriverManager.getConnection(args[0],args[1],args[2]);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error de parametros en la conexión","Error de conexión",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private Connection connection;
	public Connection getConnection() {
		return connection;
	}
}