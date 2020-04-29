import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Puneet
 */

class MySqlInitConn {
	static Connection connection;
	static Statement statement;
	static Statement statementTemp;

	public MySqlInitConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Class.forName("com.mysql.cj.jdbc.driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager", "root", "puneet");
			statement = connection.createStatement();
			statementTemp = connection.createStatement();
		} catch (Exception e) {
			System.out.println("MySqlConn exception: " + e.getMessage());

		}
	}
}

public class Home extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9023679975574915572L;
	
	MySqlInitConn c = new MySqlInitConn();

	/**
	 * Creates new form Home
	 * 
	 * @throws SQLException
	 */
	public Home() throws SQLException {

		createtables();
		initComponents();
	}

	private void createtables() throws SQLException {
		// TODO Auto-generated method stub

		String createDB = "CREATE DATABASE IF NOT EXISTS attendancemanager";

		String useDB = "use attendancemanager";

		String regUserTable = "CREATE TABLE IF NOT EXISTS `registered_users` ( `FullName` varchar(20) DEFAULT NULL, `UserName` varchar(20) NOT NULL, `Password` varchar(20) DEFAULT NULL, PRIMARY KEY (`UserName`))";

		String attendanceTable = "CREATE TABLE IF NOT EXISTS `attendance` ( `Username` varchar(20) NOT NULL, `Subject` varchar(20) NOT NULL, `Date` date NOT NULL, `Present` int(5) DEFAULT NULL, `Total_Attendance` int(6) DEFAULT NULL, PRIMARY KEY (`Username`,`Subject`,`Date`), CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `registered_users` (`UserName`))";

		String attendancegoalTable = "CREATE TABLE IF NOT EXISTS `attendancegoal` ( `Username` varchar(20) NOT NULL, `Goal` int(4) DEFAULT NULL, PRIMARY KEY (`Username`), CONSTRAINT `attendancegoal_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `registered_users` (`UserName`))";

		String attendancesubjectTable = "CREATE TABLE IF NOT EXISTS `attendancesubject` ( `Username` varchar(20) NOT NULL, `subjects` varchar(20) NOT NULL, `Last_Action` int(11) DEFAULT '-1', `LastActionDate` date DEFAULT NULL, PRIMARY KEY (`Username`,`subjects`), CONSTRAINT `attendancesubject_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `registered_users` (`UserName`))";

		String timetableTable = "CREATE TABLE IF NOT EXISTS `timetable` ( `Username` varchar(20) NOT NULL, `Day` varchar(20) NOT NULL, `Period` int(2) NOT NULL, `Subject` varchar(20) DEFAULT NULL, PRIMARY KEY (`Username`,`Day`,`Period`), KEY `Username` (`Username`,`Subject`), CONSTRAINT `timetable_ibfk_1` FOREIGN KEY (`Username`, `Subject`) REFERENCES `attendancesubject` (`Username`, `subjects`) ON DELETE CASCADE ON UPDATE CASCADE)";

		MySqlInitConn.statement.executeUpdate(createDB);
		MySqlInitConn.statement.executeUpdate(useDB);
		MySqlInitConn.statement.executeUpdate(regUserTable);
		MySqlInitConn.statement.executeUpdate(attendanceTable);
		MySqlInitConn.statement.executeUpdate(attendancegoalTable);
		MySqlInitConn.statement.executeUpdate(attendancesubjectTable);
		MySqlInitConn.statement.executeUpdate(timetableTable);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		register = new javax.swing.JButton();
		exit = new javax.swing.JButton();
		login = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Home");

		jLabel1.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
		jLabel1.setText("Attendance Manager");

		register.setText("Register");
		register.setMaximumSize(new java.awt.Dimension(100, 20));
		register.setMinimumSize(new java.awt.Dimension(100, 20));
		register.setPreferredSize(new java.awt.Dimension(100, 20));
		register.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerActionPerformed(evt);
			}
		});

		exit.setText("Quit");
		exit.setMaximumSize(new java.awt.Dimension(100, 20));
		exit.setMinimumSize(new java.awt.Dimension(100, 20));
		exit.setPreferredSize(new java.awt.Dimension(100, 20));
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});

		login.setText("Login");
		login.setMaximumSize(new java.awt.Dimension(100, 20));
		login.setMinimumSize(new java.awt.Dimension(100, 20));
		login.setPreferredSize(new java.awt.Dimension(100, 20));
		login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginActionPerformed(evt);
			}
		});

		jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/A24-512.png"))); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
						.addGroup(layout.createSequentialGroup().addGap(45, 45, 45).addComponent(jLabel1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(45, 45, 45)
								.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(exit, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(login, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(register, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 100,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addGap(45, 45, 45)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addGap(20, 20, 20)
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(20, 20, 20)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE,
																20, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(30, 30, 30)
														.addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(30, 30, 30).addComponent(exit,
																javax.swing.GroupLayout.PREFERRED_SIZE, 20,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(25, 25, 25)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void exitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitActionPerformed
		// TODO add your handling code here:
		System.exit(0);
	}// GEN-LAST:event_exitActionPerformed

	private void registerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_registerActionPerformed
		// TODO add your handling code here:
		this.dispose();
		new Register().setVisible(true);
	}// GEN-LAST:event_registerActionPerformed

	private void loginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loginActionPerformed
		// TODO add your handling code here:
		this.dispose();
		new Login().setVisible(true);
	}// GEN-LAST:event_loginActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Home().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton exit;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JButton login;
	private javax.swing.JButton register;
	// End of variables declaration//GEN-END:variables
}
