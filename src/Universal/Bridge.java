package Universal;

import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

public class Bridge {
	private JComboBox<String>menu;
	Interface interFace;
	JPanel tableContainer;
	Connection connection;
	PreparedStatement statementToDelete;
	
	public Bridge(Interface i){
		
		interFace=i;
		tableContainer=interFace.getTableContainer();
		menu=interFace.getMenu();
		connection=new ConnectionDB().getConnection();
		try {
			statementToDelete=connection.prepareStatement("SELECT * FROM test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fillItemsMenu();
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				displayTable();
			}
		});
		
		
		interFace.getDeleteButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					
					deleteRecordsDB();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
				
	}
	public void fillItemsMenu(){
		ResultSet tables=null;
		try {
			tables=connection.getMetaData().getTables(connection.getCatalog(), null, null, null);
			while(tables.next()) {
				menu.addItem(tables.getString("TABLE_NAME"));
			}
			tables.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private JTable table;
	private ArrayList< ArrayList<String> > recordsStorage=new ArrayList< ArrayList<String> >();
	private String keyField;
	public void displayTable(){
		try {
			recordsStorage.clear();
			String sql="SELECT * FROM "+(String)menu.getSelectedItem();
			Statement st=connection.createStatement();
			ResultSet records=st.executeQuery(sql);
			ResultSetMetaData metaData=records.getMetaData(); // ResultSet metadatos de la salida de la consulta
			String[][] matrixRecords=new String[getRecordsCount(sql)][metaData.getColumnCount()];
			int row=0;
			while(records.next()) {
				ArrayList<String> temporalArrayList=new ArrayList<String>();
				for(int col=0;col<metaData.getColumnCount();col++) {
					String data=records.getString(col+1);
					matrixRecords[row][col]=data;
					temporalArrayList.add(data);
				}
				recordsStorage.add(temporalArrayList);
				row++;
			} 
			records.close();
			st.close();
			String[] colm= new String[metaData.getColumnCount()];
			for(int col=0;col<metaData.getColumnCount();col++) {
				colm[col]=metaData.getColumnLabel(col+1);
				if(col==0) keyField=colm[0];
			}
			table=new JTable(matrixRecords,colm);
			
			tableContainer.removeAll();
			
			tableContainer.add(new JScrollPane(table));
			tableContainer.repaint();
			tableContainer.revalidate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getRecordsCount(String sql) throws SQLException{
		Statement st=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=st.executeQuery(sql);
		rs.last();
		int current=rs.getRow();
		rs.close();
		st.close();
		return current; 
	}
	
	public void deleteRecordsDB() throws SQLException{
		if(table!=null) {
			int[] indexSelectedRows=table.getSelectedRows();
			if(indexSelectedRows.length>0) {
				String sql="DELETE FROM "+menu.getSelectedItem()+" WHERE";
				for(int x=0;x<indexSelectedRows.length;x++) {
					sql+=" "+keyField+"='"+recordsStorage.get(indexSelectedRows[x]).get(0)+"' OR";
				}
				sql=sql.substring(0, sql.length()-2);
				statementToDelete.execute(sql);
				displayTable();
			}
		}
	}
	
}