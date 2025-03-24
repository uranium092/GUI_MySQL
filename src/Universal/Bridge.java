package Universal;

import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Bridge {
	private JComboBox<String>menu;
	private Interface interfaceComponent;
	private JPanel tableContainer;
	private Connection connection;
	private Statement statamentOperations;
	
	public Bridge(Interface i){
		interfaceComponent=i;
		tableContainer=interfaceComponent.getTableContainer();
		menu=interfaceComponent.getMenu();
		connection=new ConnectionDB().getConnection();
		try {
			statamentOperations=connection.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fillItemsMenu();
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayTable();
			}
		});
		
		
		interfaceComponent.getDeleteButton().addActionListener(new ActionListener() {
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
	private void fillItemsMenu(){
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
	private String keyField;
	private void displayTable(){
		try {
			String sql="SELECT * FROM "+(String)menu.getSelectedItem();
			Statement st=connection.createStatement();
			ResultSet records=st.executeQuery(sql);
			ResultSetMetaData metaData=records.getMetaData();
			String[][] matrixRecords=new String[getRecordsCount(sql)][metaData.getColumnCount()];
			int row=0;
			ArrayList< ArrayList<String> > recordsStorage=new ArrayList< ArrayList<String> >();
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
			table=new JTable(new DefaultTableModel(matrixRecords, colm) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column != 0;
			    }
			});
			table.getModel().addTableModelListener(new TableModelListener() {
				
				@Override
				public void tableChanged(TableModelEvent e) {
					// TODO Auto-generated method stub
					String sql="UPDATE "+menu.getSelectedItem()+" SET "+colm[e.getColumn()]+" = '"+table.getValueAt(e.getFirstRow(),e.getColumn())+"' WHERE "+keyField+"='"+table.getValueAt(e.getFirstRow(), 0)+"'";
					try {
						statamentOperations.execute(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			tableContainer.removeAll();
			
			tableContainer.add(new JScrollPane(table));
			tableContainer.repaint();
			tableContainer.revalidate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getRecordsCount(String sql) throws SQLException{
		Statement st=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=st.executeQuery(sql);
		rs.last();
		int current=rs.getRow();
		rs.close();
		st.close();
		return current; 
	}
	
	private void deleteRecordsDB() throws SQLException{
		if(table!=null) {
			int[] indexSelectedRows=table.getSelectedRows();
			if(indexSelectedRows.length>0) {
				String sql="DELETE FROM "+menu.getSelectedItem()+" WHERE";
				for(int x=0;x<indexSelectedRows.length;x++) {
					sql+=" "+keyField+"='"+table.getValueAt(indexSelectedRows[x], 0)+"' OR";
				}
				sql=sql.substring(0, sql.length()-2);
				statamentOperations.execute(sql);
				displayTable();
			}
		}
	}
	
}