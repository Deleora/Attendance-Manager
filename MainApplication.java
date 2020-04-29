import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicProgressBarUI;

class MySqlConn {
	static Connection connection;
	static Statement statement;
	static Statement statementTemp;

	public MySqlConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName("com.mysql.cj.jdbc.driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager", "root", "puneet");
			statement = connection.createStatement();
			statementTemp = connection.createStatement();
		} catch (Exception e) {
			System.out.println("MySqlConn exception: " + e.getMessage());

		}
	}
}

class settings1 extends JFrame {

	private static final long serialVersionUID = -756134365810054249L;

	private final JPanel P1;
	private final JPanel P2;
	private final JPanel main;
	private final JScrollPane scrol;

	MySqlConn c = new MySqlConn();

	private JLabel goalLabel;
	private JLabel goalValue;
	private JLabel overallLabel;
	private JLabel overallValue;
	private JLabel usernameValue;
	private JButton signoutButton;
	private JButton addSubjectButton;

	String uname;
	int goalInt;

	class AttendancePanel1 extends JPanel {

		private static final long serialVersionUID = -5237533376617199658L;

		Connection connection;
		Statement statement;
		String currentSubject;

		int present;
		int total;

		JLabel attValue;
		JLabel progressLabel;

		AttendancePanel1 me;

		public AttendancePanel1(ResultSet r/* , settings1 a */) throws SQLException {

			// UIManager.put("progressBar.selectionForeground", new
			// ColorUIResource(Color.BLACK));
			// UIManager.put("progressBar.selectionBackground", new
			// ColorUIResource(Color.BLACK));

			this.setBounds(30, 160, 100, 100);
			this.setLayout(null);
			this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

			// this.setBackground(Color.BLACK);
			this.setMaximumSize(new java.awt.Dimension(700, 200));
			this.setMinimumSize(new java.awt.Dimension(700, 200));
			this.setPreferredSize(new java.awt.Dimension(700, 200));
			// this.setSize(700, 200);

			Border blackline = BorderFactory.createLineBorder(Color.black);

			// ----------------------------------------------------------------------------

			uname = r.getString(1);
			currentSubject = r.getString(2);
			// String currDate = r.getString(3);
			present = r.getInt(3);
			total = r.getInt(4);

			// ResultSet presentButtonResultSet = r;
			// ResultSet absentButtonResultSet = r;

			// ----------------------------------------------------------------------------

			JLabel subLabel = new JLabel("Subject:");
			subLabel.setBounds(12, 13, 60, 30);
			subLabel.setBorder(blackline);
			this.add(subLabel);

			JLabel subName = new JLabel();
			try {
				subName.setText(r.getString(2));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			subName.setBounds(84, 13, 160, 30);
			subName.setBorder(blackline);
			this.add(subName);

			// ----------------------------------------------------------------------------

			JLabel attLabel = new JLabel("Att:");
			attLabel.setBounds(12, 56, 60, 30);
			attLabel.setBorder(blackline);
			this.add(attLabel);

			// ----------------------------------------------------------------------------

			// JLabel attValue = new JLabel();
			attValue = new JLabel();
			// String attRet = "select SUM(Present), SUM(Total_Attendance) from attendance
			// where Subject = 'N'";
			String att;
			att = present + "/" + total;
			attValue.setText(att);
			attValue.setBounds(84, 56, 160, 30);
			attValue.setBorder(blackline);
			this.add(attValue);

			// ----------------------------------------------------------------------------

			JLabel statusLabel = new JLabel("Status:");
			statusLabel.setBounds(12, 99, 60, 30);
			statusLabel.setBorder(blackline);
			this.add(statusLabel);

			// ----------------------------------------------------------------------------

			JLabel statusValue = new JLabel("");
			statusValue.setBorder(blackline);
			statusValue.setBounds(84, 99, 293, 30);
			this.add(statusValue);

			// ----------------------------------------------------------------------------

			progressLabel = new JLabel("");
			progressLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
			progressLabel.setBounds(350, 56, 60, 30);
			this.add(progressLabel);

			// ----------------------------------------------------------------------------

			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(426, 56, 250, 30);
			this.add(progressBar);

			// ----------------------------------------------------------------------------

			JLabel menuLabel = new JLabel("Menu");
			menuLabel.setBounds(620, 13, 56, 16);
			this.add(menuLabel);

			// ----------------------------------------------------------------------------

			final JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
			popupMenu.setBounds(0, 0, 99, 61);

			JMenuItem undo = new JMenuItem("Undo");
			JMenuItem reset = new JMenuItem("Reset");
			JMenuItem edit = new JMenuItem("Edit");
			JMenuItem delete = new JMenuItem("Delete");

			popupMenu.add(undo);
			popupMenu.add(reset);
			popupMenu.add(edit);
			popupMenu.add(delete);

			menuLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// System.out.println("menu clicked");
					// popupMenuFun();
					// addPopup(contentPane, popupMenu);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					// System.out.println("menu released");
				}
			});

			undo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// System.out.println(currentSubject + " undo MenuItem clicked.");

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					String currDate = dateFormat.format(date);
					String lastActionPerformed = "select Last_Action, LastActionDate from attendancesubject where Username = '"
							+ (uname) + "' AND subjects = '" + (currentSubject) + "' AND LastActionDate = '"
							+ (currDate) + "'";
					String lastActionReset = "update attendancesubject set Last_Action = -1, LastActionDate = CURDATE() where Username = '"
							+ (uname) + "' AND subjects = '" + (currentSubject) + "'";

					try {

						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager",
								"root", "puneet");
						statement = connection.createStatement();

						ResultSet lastActionResultSet = statement.executeQuery(lastActionPerformed);
						lastActionResultSet.first();
						if (lastActionResultSet.getInt(1) == 1) {
							String undoPresent = "update attendance set present = present - 1, Total_Attendance = Total_Attendance - 1 where  Username = '"
									+ (uname) + "' && Subject = '" + (currentSubject) + "' &&  Date = '"
									+ (lastActionResultSet.getString(2)) + "'";
							statement.executeUpdate(undoPresent);
							present--;
							total--;
							// System.out.println("Present Undo");

						} else if (lastActionResultSet.getInt(1) == 0) {
							String undoAbsent = "update attendance set Total_Attendance = Total_Attendance - 1 where  Username = '"
									+ (uname) + "' && Subject = '" + (currentSubject) + "' &&  Date = '"
									+ (lastActionResultSet.getString(2)) + "'";
							statement.executeUpdate(undoAbsent);
							total--;
							// System.out.println("Absent Undo");

						}
						undo.setEnabled(false);
						statement.executeUpdate(lastActionReset);

						// System.out.println("Undo reset done");

						statement.close();
						connection.close();
					} catch (Exception e1) {
						System.out.println("undo action exp" + e1.getMessage());
						// JOptionPane.showMessageDialog(null, e1.getMessage());
					}

					progressBar.setStringPainted(true);
					progressBar.setValue(progressbarUpdate());
					getOverallValue();
					setStatus(statusValue);
					progressbarColor(progressBar);
				}
			});

			reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(currentSubject + " reset MenuItem clicked.");

					int resetChoice;
					resetChoice = JOptionPane.showConfirmDialog(null,
							"This will reset your current progress \n(This can't be undone)\nAre you sure?", "WARNING",
							JOptionPane.YES_NO_OPTION);
					System.out.println("reset value = " + resetChoice);
					if (resetChoice == 0) {
						String attendanceResetDelete = "delete from attendance where Username = '" + (uname)
								+ "' and Subject='" + (currentSubject) + "'";
						String attendanceResetInsert = "insert into attendance values ('" + (uname) + "', '"
								+ (currentSubject) + "', CURDATE(), 0, 0)";

						try {

							Class.forName("com.mysql.jdbc.Driver");
							connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager",
									"root", "puneet");
							statement = connection.createStatement();
							statement.executeUpdate(attendanceResetDelete);
							statement.executeUpdate(attendanceResetInsert);
							present = 0;
							total = 0;

							System.out.println(currentSubject + " reset done");

							statement.close();
							connection.close();
						} catch (Exception e1) {
							System.out.println("present action");
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}

						progressBar.setStringPainted(true);
						progressBar.setValue(progressbarUpdate());
						getOverallValue();
						setStatus(statusValue);
						progressbarColor(progressBar);

					}

				}
			});

			edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(currentSubject + " edit MenuItem clicked.");

					JTextField subjectName = new JTextField(5);
					subjectName.setText(currentSubject);
					subjectName.setBounds(140, 19, 100, 22);
					JTextField presentCount = new JTextField(5);
					presentCount.setText(present + "");
					presentCount.setBounds(140, 49, 100, 22);
					JTextField totalCount = new JTextField(5);
					totalCount.setText(total + "");
					totalCount.setBounds(140, 79, 100, 22);

					JPanel myPanel = new JPanel();
					myPanel.setPreferredSize(new Dimension(280, 120));
					myPanel.setLayout(null);

					JLabel subjectLabel = new JLabel("Subject:");
					subjectLabel.setBounds(20, 20, 110, 20);
					myPanel.add(subjectLabel);
					myPanel.add(subjectName);

					JLabel presentLabel = new JLabel("Classes attended:");
					presentLabel.setBounds(20, 50, 110, 20);
					myPanel.add(presentLabel);
					myPanel.add(presentCount);

					JLabel totalLabel = new JLabel("Total classes:");
					totalLabel.setBounds(20, 80, 110, 20);
					myPanel.add(totalLabel);
					myPanel.add(totalCount);

					int result = JOptionPane.OK_OPTION, presentInt, totalInt;
					String subNameNew;

					while (result != JOptionPane.CANCEL_OPTION) {
						result = JOptionPane.showConfirmDialog(null, myPanel, "Edit attendance details",
								JOptionPane.OK_CANCEL_OPTION);
						try {

							System.out.println("presentCount: " + presentCount.getText().isEmpty());
							System.out.println("presentCount: " + presentCount.getText().isBlank());
							System.out.println("totalCount: " + totalCount.getText().isEmpty());
							System.out.println("totalCount: " + totalCount.getText().isBlank());

							subNameNew = subjectName.getText();
							presentInt = Integer.parseInt(presentCount.getText());
							totalInt = Integer.parseInt(totalCount.getText());

							if (result == JOptionPane.OK_OPTION) {
								if (!subNameNew.isBlank()) {
									System.out.println("Valid input");
									System.out.println("subjectName: " + currentSubject);
									System.out.println("subjectName new: " + subNameNew);
									System.out.println("presentCount: " + presentInt);
									System.out.println("totalCount: " + totalInt);

									if (presentInt > totalInt) {
										System.out.println("presentInt > totalInt");
										JOptionPane.showMessageDialog(null, "presentInt > totalInt");
									} else {
										String subEditAttSub = "update attendancesubject set subjects = '"
												+ (subNameNew) + "' where Username = '" + (uname) + "' AND subjects = '"
												+ (currentSubject) + "'";
										String subEditAttDel = "delete from attendance where Username = '" + (uname)
												+ "' AND subject = '" + (currentSubject) + "'";
										String subEditAttIns = "insert into attendance values ('" + (uname) + "', '"
												+ (subNameNew) + "', CURDATE(), " + (presentInt) + ", " + (totalInt)
												+ ")";

										Class.forName("com.mysql.jdbc.Driver");
										connection = DriverManager.getConnection(
												"jdbc:mysql://localhost:3306/attendancemanager", "root", "puneet");
										statement = connection.createStatement();

										statement.executeUpdate(subEditAttSub);
										statement.executeUpdate(subEditAttDel);
										statement.executeUpdate(subEditAttIns);

										statement.close();
										connection.close();

										System.out.println("Setting new values...");
										subName.setText(subNameNew);
										String att;
										att = presentInt + "/" + totalInt;
										attValue.setText(att);
										present = presentInt;
										total = totalInt;
										currentSubject = subNameNew;
										progressBar.setValue(progressbarUpdate());
										setStatus(statusValue);
										getOverallValue();
										progressbarColor(progressBar);
										JOptionPane.showMessageDialog(null, "Edit successful");

										break;
									}
								} else {
									JOptionPane.showMessageDialog(null, "Please enter a valid Subject name");
								}
							}

						} catch (Exception e1) {
							// System.out.println("edit menu exp : " + e1.getMessage());
							if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
								System.out.println("edit menu exp : Form closed");
								break;
							} else {
								JOptionPane.showMessageDialog(null, "Invalid Input!!" + e1.getMessage());
							}
						}
					}

				}
			});

			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(currentSubject + " delete MenuItem clicked.");
					removeAll();
					revalidate();
					repaint();
					removeNotify();

					String subDeleteAttSubject = "delete from attendancesubject where Username = '" + (uname)
							+ "' and subjects = '" + (currentSubject) + "'";
					String subDeleteAtt = "delete from attendance where Username = '" + (uname) + "' and subject = '"
							+ (currentSubject) + "'";

					try {

						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager",
								"root", "puneet");
						statement = connection.createStatement();

						statement.executeUpdate(subDeleteAtt);
						statement.executeUpdate(subDeleteAttSubject);

						statement.close();
						connection.close();

					} catch (Exception e1) {
						System.out.println("present action");
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

					// me.getParent().remove(me);
					P2.removeAll();
					addAttendancePanel1();

					// parent.remove(me);
					System.out.println(currentSubject + " delete Done.");

					// deletePanelFun(this);
				}
			});

			// ----------------------------------------------------------------------------

			JButton presentButton = new JButton("Present");
			presentButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// String uname = new String(r.getString(1));
					// String currentSubject = r.getString(2);

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					String currDate = dateFormat.format(date);
					System.out.println(uname + ":" + currentSubject + ":" + currDate);
					String attInsert = "insert into attendance values ('" + (uname) + "', '" + (currentSubject) + "', '"
							+ (currDate) + "', 1, 1)";
					// insert into attendance values ('pt', 'Java', '20-03-15', 1, 3);
					String attUpdate = "update attendance set present = present + 1, Total_Attendance = Total_Attendance + 1 where  Username = '"
							+ (uname) + "' && Subject = '" + (currentSubject) + "' &&  Date = '" + (currDate) + "'";

					String lastActionUpdate = "update attendancesubject set Last_Action = 1, LastActionDate = CURDATE() where Username = '"
							+ (uname) + "' AND  subjects = '" + (currentSubject) + "'";
					try {

						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager",
								"root", "puneet");
						statement = connection.createStatement();

						if (!statement
								.executeQuery("select * from attendance where Username = '" + (uname)
										+ "' && Subject = '" + (currentSubject) + "' &&  Date = '" + (currDate) + "'")
								.isBeforeFirst()) {

							statement.executeUpdate(attInsert);
						} else {
							System.out.println("4.Present");

							statement.executeUpdate(attUpdate);

							System.out.println("6.Present");
						}

						statement.executeUpdate(lastActionUpdate);

						statement.close();
						connection.close();
					} catch (Exception e1) {
						System.out.println("present action");
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

					present++;
					total++;
					progressBar.setStringPainted(true);
					progressBar.setValue(progressbarUpdate());
					getOverallValue();
					setStatus(statusValue);
					progressbarColor(progressBar);
					undo.setEnabled(true);

				}
			});
			presentButton.setBounds(426, 102, 100, 30);
			this.add(presentButton);

			// ----------------------------------------------------------------------------

			JButton absentButton = new JButton("Absent");
			absentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// String uname = new String(r.getString(1));
					// String currentSubject = r.getString(2);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					String currDate = dateFormat.format(date);
					System.out.println(uname + ":" + currentSubject + ":" + currDate);
					String attInsert = "insert into attendance values ('" + (uname) + "', '" + (currentSubject) + "', '"
							+ (currDate) + "', 0, 1)";
					// insert into attendance values ('pt', 'Java', '20-03-15', 1, 3);
					String attUpdate = "update attendance set Total_Attendance = Total_Attendance + 1 where  Username = '"
							+ (uname) + "' && Subject = '" + (currentSubject) + "' &&  Date = '" + (currDate) + "'";

					String lastActionUpdate = "update attendancesubject set Last_Action = 0, LastActionDate = CURDATE() where Username = '"
							+ (uname) + "' AND  subjects = '" + (currentSubject) + "'";
					try {

						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager",
								"root", "puneet");
						statement = connection.createStatement();

						if (!statement
								.executeQuery("select * from attendance where Username = '" + (uname)
										+ "' && Subject = '" + (currentSubject) + "' &&  Date = '" + (currDate) + "'")
								.isBeforeFirst()) {

							statement.executeUpdate(attInsert);

						} else {
							statement.executeUpdate(attUpdate);

						}

						statement.executeUpdate(lastActionUpdate);

						statement.close();
						connection.close();
					} catch (Exception e1) {
						System.out.println("absent action");
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

					total++;
					progressBar.setStringPainted(true);
					progressBar.setValue(progressbarUpdate());
					getOverallValue();
					setStatus(statusValue);
					progressbarColor(progressBar);
					undo.setEnabled(true);
				}
			});
			absentButton.setBounds(576, 102, 100, 30);
			this.add(absentButton);

			// ----------------------------------------------------------------------------

			progressBar.setStringPainted(true);
			progressBar.setValue(progressbarUpdate());
			undo.setEnabled(undoStateSetter());
			setStatus(statusValue);
			progressbarColor(progressBar);

		}

		// PROGRESS BAR COLOR SETTER
		// ****************************************************************************
		private void progressbarColor(JProgressBar progressBar) {
			// TODO Auto-generated method stub
			System.out.println(progressBar.getString().length());
			if ((int) Math.floor((((double) present) / total) * 100) < goalInt) {
				System.out.println("Red progress bar :" + progressBar.getValue());
				Color customRed = new Color(222, 87, 74);
				progressBar.setForeground(customRed);

			} else {
				System.out.println("Green progress bar :" + progressBar.getValue());
				Color customGreen = new Color(60, 186, 84);
				progressBar.setForeground(customGreen);
			}

			return;

		}

		// UNDO STATE SETTER
		// ****************************************************************************
		private boolean undoStateSetter() {
			// TODO Auto-generated method stub

			// System.out.println("Undo setter");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			dateFormat.format(date);
			String lastActionPerformed = "select Last_Action, LastActionDate from attendancesubject where Username = '"
					+ (uname) + "' AND subjects = '" + (currentSubject) + "' AND LastActionDate = CURDATE()";

			boolean forReturn = false;

			try {

				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager", "root",
						"puneet");
				statement = connection.createStatement();

				ResultSet lastActionResultSet = statement.executeQuery(lastActionPerformed);
				lastActionResultSet.first();
				if (lastActionResultSet.isBeforeFirst()) {
					if (lastActionResultSet.getInt(1) == 1 || lastActionResultSet.getInt(1) == 0) {
						forReturn = true;

					} else if (lastActionResultSet.getInt(1) == -1) {
						forReturn = false;

					} else {
						forReturn = false;

					}
				} else {
					forReturn = false;
				}

				statement.close();
				connection.close();
			} catch (Exception e1) {
				System.out.println("undo action exp");
				// JOptionPane.showMessageDialog(null, e1.getMessage());
				progressLabel.setText("0.0%");
			}
			return forReturn;
		}

		// PROGRESS BAR UPDATER
		// ****************************************************************************
		private int progressbarUpdate() {

			int overallFormattedInt = 0;

			String retOverall = "select SUM(Present), SUM(Total_Attendance) from attendance where username='" + (uname)
					+ "' and subject='" + (currentSubject) + "'";
			try {

				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager", "root",
						"puneet");
				statement = connection.createStatement();

				ResultSet overallResultSet = statement.executeQuery(retOverall);
				overallResultSet.first();
				double present = overallResultSet.getInt(1);
				double total = overallResultSet.getInt(2);
				DecimalFormat df = new DecimalFormat("#.#");
				String overallFormatted = (df.format(((present / total) * 100)));
				float overallFormattedFloat = Float.parseFloat(overallFormatted);
				int overallFormattedIntTemp = Math.round(overallFormattedFloat);
				System.out.println(overallFormatted);
				System.out.println(overallFormattedFloat);
				// System.out.println(overallFormattedInt);

				overallFormattedInt = overallFormattedIntTemp;

				// progressBar.setStringPainted(true);
				// progressBar.setValue(overallFormattedInt);

				String att = overallResultSet.getInt(1) + "/" + overallResultSet.getInt(2);
				present = overallResultSet.getInt(1);
				total = overallResultSet.getInt(2);
				attValue.setText(att);
				if (Double.isNaN((double) overallFormattedFloat)) {
					System.out.println("isNaN");
					progressLabel.setText("0.0%");
				} else {
					progressLabel.setText(overallFormatted + "%");
				}

				statement.close();
				connection.close();

			} catch (Exception e) {

				JOptionPane.showMessageDialog(this, e.getMessage());
			}

			// setStatus();

			return overallFormattedInt;

		}

		// STATUS SETTER
		// ****************************************************************************
		private void setStatus(JLabel statusValue) {
			// TODO add your handling code here:
			// int goalLength = goalValue.getText().length();
			// String uname = uName.getText();
			// String Subject = cb1.getSelectedItem().toString();
			String retrieveStatus = "select SUM(Present), SUM(Total_Attendance) from attendance where username='"
					+ (uname) + "' and subject='" + (currentSubject) + "'";
			// subName.setText(Subject);
			System.out.println("Status Class : " + currentSubject);

			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancemanager", "root",
						"puneet");
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(retrieveStatus);

				rs.first();
				// float goalFloat = (float)goalInt;
				int present = rs.getInt(1);
				int total = rs.getInt(2);
				System.out.println(goalInt + " : " + present + "/" + total);
				if (total > 0) {
					// attValue.setText(present + "/" + total);
					int ctotal = (int) Math.floor((((double) present) / total) * 100);
					// int ctotal = ctotalD;
					System.out.println("ctotal floor: " + ctotal + " : " + goalInt);
					if (ctotal < goalInt) {
						System.out.println("ctotal < goalInt");
						int pcount = 0;
						while (ctotal < goalInt) {
							pcount++;
							ctotal = (int) Math.floor((((double) (present + pcount)) / (total + pcount)) * 100);
							System.out.println("ctotal : " + ctotal);
						}
						System.out.println("Attend " + pcount + " classes");
						statusValue.setText("Attend " + pcount + " classes");
					} else if (ctotal > goalInt) {
						System.out.println("ctotal > goalInt");
						int acount = 0;
						while (ctotal > goalInt) {
							acount++;
							ctotal = (int) Math.floor((((double) (present)) / (total + acount)) * 100);
						}
						acount--;
						if (acount == 0) {
							System.out.println("Can't skip any classes 0");
							statusValue.setText("Can't skip any classes 0");
						} else {
							System.out.println("Bunk " + acount + " classes");
							statusValue.setText("Bunk " + acount + " classes");
						}
					} else if (ctotal == goalInt) {
						System.out.println("Can't skip any classes");
						statusValue.setText("Can't skip any classes");
					}
				} else {
					System.out.println("Can't skip any classes 0/0");
					statusValue.setText("Can't skip any classes 0/0");
				}

				// progressbarUpdate();
				// overallValue();
				System.out.println("Status Class : " + currentSubject + " end");

				rs.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "status update : " + e.getMessage());
			}

		}

	}

	// MAIN APPLICATION CONSTRUCTOR
	// ####################################################################################

	public settings1(String loginUnsername) {

		setResizable(false);
		P1 = new JPanel();
		// this.setOpacity(0.5f);
		// P1.setBackground(Color.DARK_GRAY);
		P1.setMaximumSize(new Dimension(800, 200));
		P1.setMinimumSize(new Dimension(800, 200));
		P1.setPreferredSize(new Dimension(800, 200));
		P1.setSize(new Dimension(800, 200));
		P1.setBounds(0, 0, 200, 400);

		P2 = new JPanel();
		// P2.setBackground(Color.BLACK);
		P2.setMinimumSize(new Dimension(700, 300));
		P2.setLayout(new BoxLayout(P2, BoxLayout.Y_AXIS));

		main = new JPanel();
		// main.setBackground(Color.WHITE);
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		scrol = new JScrollPane(P2);
		// scrol.setBackground(Color.RED);
		scrol.setBounds(491, 0, 800, 350);
		scrol.setSize(800, 300);
		initComponents(loginUnsername);
		getContentPane().add(main);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setSize(900, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setVisible(true);

		fillApplication();

	}

	// INITIALIZE COMPONENTS
	// ****************************************************************************
	private void initComponents(String loginUnsername) {
		main.add(P1);
		main.add(scrol);

		this.setJMenuBar(attMgrMenuBar());

		P1.setLayout(null);

		JLabel headingLabel = new JLabel("Attendance Manger");
		headingLabel.setOpaque(true);
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		headingLabel.setBounds(260, 13, 240, 60);
		P1.add(headingLabel);

		goalLabel = new JLabel("Goal");
		goalLabel.setBounds(12, 89, 56, 16);
		P1.add(goalLabel);

		goalValue = new JLabel("");
		goalValue.setBorder(new LineBorder(new Color(0, 0, 0)));
		goalValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					String uname = usernameValue.getText();
					while (true) {
						String goal = JOptionPane.showInputDialog("Set your Goal!", goalInt);
						goalInt = Integer.parseInt(goal);
						if (goalInt > 100) {
							JOptionPane.showMessageDialog(P1, "Plase enter a valid value!  (<=100)");
						} else {
							goalValue.setText(goalInt + "%");

							String update = "update attendancegoal set Goal = " + (goalInt) + " where username='"
									+ (uname) + "'";
							MySqlConn.statement.executeUpdate(update);

							// MySqlConn1.statement.close();
							P2.removeAll();
							addAttendancePanel1();

							break;

						}

					}

				} catch (Exception e1) {
					System.out.println("Goal value exp : " + e1.getMessage());
				}
				P2.removeAll();
				addAttendancePanel1();
			}
		});
		goalValue.setBounds(90, 89, 56, 16);
		P1.add(goalValue);

		overallLabel = new JLabel("Overall");
		overallLabel.setBounds(12, 127, 56, 16);
		P1.add(overallLabel);

		overallValue = new JLabel("");
		overallValue.setBorder(new LineBorder(new Color(0, 0, 0)));
		overallValue.setBounds(90, 127, 56, 16);
		P1.add(overallValue);

		usernameValue = new JLabel(loginUnsername);
		uname = loginUnsername;
		usernameValue.setBounds(547, 57, 56, 16);
		P1.add(usernameValue);

		signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MySqlConn.connection.close();
					// System.out.println("Signing out...");
					// System.out.println("Sign out success");

					JOptionPane.showMessageDialog(main, "You have successfully logged out!");
					setVisible(false);
					System.exit(0);
					// new Home().setVisible(true);
				} catch (Exception e1) {
					System.out.println("Error in signing out");
				}
			}
		});

		signoutButton.setBounds(691, 53, 97, 25);
		P1.add(signoutButton);

		addSubjectButton = new JButton("Add Subject");
		addSubjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					while (true) {
						String subName = JOptionPane.showInputDialog(main, "Enter Subject name: ");
						String subCheck = "select * from attendancesubject  where subjects = '" + (subName)
								+ "' and username='" + (uname) + "'";
						ResultSet rs = MySqlConn.statement.executeQuery(subCheck);
						// rs.first();
						// System.out.println("1------");
						System.out.println("Add sub executed : SubName = " + subName.isBlank());
						// System.out.println("2------");
						if (!subName.isBlank()) {
							if (!rs.isBeforeFirst()) {
								// Subject does not exists
								System.out.println("Subject does not exists : Subject Added");
								DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								Date date = new Date();
								String currDate = dateFormat.format(date);
								String subInsertAttSub = "insert into attendancesubject values ('" + (uname) + "', '"
										+ (subName) + "', -1, '" + (currDate) + "')";
								String subInsertAtt = "insert into attendance values ('" + (uname) + "', '" + (subName)
										+ "', CURDATE(), 0, 0)";
								MySqlConn.statement.executeUpdate(subInsertAttSub);
								MySqlConn.statement.executeUpdate(subInsertAtt);
								JOptionPane.showMessageDialog(main, "Subject added successfully!");

								P2.removeAll();
								addAttendancePanel1();

								break;
							} else {
								String msg = "Subject " + (subName) + " already exists.";
								JOptionPane.showMessageDialog(main, msg, "Add Subject Error",
										JOptionPane.ERROR_MESSAGE);
							}
						} else if (subName.isBlank()) {
							JOptionPane.showMessageDialog(main, "Subject blank");
							System.out.println("Subject blank/empty");
							// break;
						} else {
							break;
						}
						System.out.println("After break");
					}
					System.out.println("After while");

				} catch (Exception e1) {
					System.out.println("add sub exp : " + e1.getMessage());
				}
			}
		});
		addSubjectButton.setBounds(672, 141, 116, 25);
		P1.add(addSubjectButton);

	}

	// CREATE MENU BAR
	// ****************************************************************************
	JMenuBar attMgrMenuBar() {
		// JFrame f = new JFrame("Menu and MenuItem Example");
		JMenu fileMenu;
		JMenuItem exitMenu;
		JMenu accountMenu;
		JMenuItem subMenu;
		JMenuItem editCriteriaMenu;
		JMenuItem timetableMenu;
		JMenuItem signoutMenu;
		JMenuBar menubar = new JMenuBar();
		fileMenu = new JMenu("File");

		/*
		 * subMenu = new JMenuItem("Subjects"); subMenu.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * 
		 * String retrieve =
		 * "select Username,Subject,SUM(Present), SUM(Total_Attendance) from attendance where Username = '"
		 * + (usernameValue.getText()) + "' group by Subject"; try { ResultSet rs =
		 * MySqlConn.statement.executeQuery(retrieve);
		 * 
		 * JDialog jdSub = new JDialog(); System.out.println("BEFORE"); jdSub.add(new
		 * subjectMenuPanel(rs)); jdSub.pack(); // jd.setLocation(null);
		 * jdSub.setLocationRelativeTo(null); jdSub.setTitle("TIME Table");
		 * jdSub.setModal(true); jdSub.setVisible(true); System.out.println("AFTER");
		 * 
		 * } catch (SQLException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * } });
		 */
		editCriteriaMenu = new JMenuItem("Edit Attendance Criteria");
		exitMenu = new JMenuItem("Exit");
		// fileMenu.add(subMenu);
		fileMenu.add(editCriteriaMenu);
		fileMenu.add(exitMenu);
		menubar.add(fileMenu);

		accountMenu = new JMenu("Account");
		timetableMenu = new JMenuItem("Timetable");
		timetableMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("timetableMenu");
				String allSubjects = "select subjects from attendancesubject where Username = '" + (uname) + "'";

				ArrayList<String> subArray = new ArrayList<String>();

				try {
					ResultSet rs = MySqlConn.statement.executeQuery(allSubjects);

					while (rs.next()) {
						subArray.add(rs.getString(1));
					}
					System.out.println(subArray);

				} catch (Exception e1) {
					System.out.println("Error in signing out");
				}

				// int result = JOptionPane.showConfirmDialog(null, new testFunction(c,
				// subArray, uname), "TimeTable", JOptionPane.OK_CANCEL_OPTION,
				// JOptionPane.PLAIN_MESSAGE);

				JDialog jd = new JDialog();
				System.out.println("BEFORE");
				jd.add(new timetablePanel(c, subArray, uname));
				jd.pack();
				// jd.setLocation(null);
				jd.setLocationRelativeTo(null);
				jd.setTitle("TIME Table");
				jd.setModal(true);
				jd.setVisible(true);
				System.out.println("AFTER");

				/*
				 * System.out.println("Option : " + result); if (result ==
				 * JOptionPane.OK_OPTION) { System.out.println("INSERT"); } else if (result ==
				 * JOptionPane.CANCEL_OPTION) { System.out.println("CANCELLED"); } else {
				 * System.out.println("CROSSED"); }
				 */
			}
		});
		signoutMenu = new JMenuItem("Sign-Out");
		signoutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MySqlConn.connection.close();
					JOptionPane.showMessageDialog(main, "You have successfully logged out!");
					setVisible(false);
					new Home().setVisible(true);
					// System.exit(0);
					// new Home().setVisible(true);
				} catch (Exception e1) {
					System.out.println("Error in signing out");
				}
			}
		});
		accountMenu.add(timetableMenu);
		accountMenu.add(signoutMenu);
		menubar.add(accountMenu);

		return menubar;
	}

	// RETRIEVE OVERALL VALUE
	// ****************************************************************************
	private void getOverallValue() {
		String uname = usernameValue.getText();
		String retOverall = "select SUM(Present), SUM(Total_Attendance) from attendance where username='" + (uname)
				+ "'";
		try {

			ResultSet rs = MySqlConn.statement.executeQuery(retOverall);
			rs.first();
			double present = rs.getInt(1);
			double total = rs.getInt(2);
			DecimalFormat df = new DecimalFormat("#.#");
			String overallFormatted = (df.format(((present / total) * 100)));
			overallValue.setText(overallFormatted + "%");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

	// RETRIEVE GOAL VALUE
	// ****************************************************************************
	private void getGoalValue() {
		String uname = usernameValue.getText();
		String retGoal = "select Goal from attendancegoal where username='" + (uname) + "'";
		try {

			ResultSet rs = MySqlConn.statement.executeQuery(retGoal);
			rs.first();

			goalInt = rs.getInt(1);
			goalValue.setText(goalInt + "%");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

	// ADD ATTENDANCE PANEL TO SCROLL PANE
	// ****************************************************************************
	public void addAttendancePanel1(settings1 this) {
		// System.out.println("Add Pressed");
		String retrieve = "select Username,Subject,SUM(Present), SUM(Total_Attendance) from attendance where Username = '"
				+ (usernameValue.getText()) + "' group by Subject";
		try {
			ResultSet rs = MySqlConn.statement.executeQuery(retrieve);
			// rs.next();

			P2.add(Box.createVerticalStrut(10));
			while (rs.next()) {

				String sub = rs.getString(2);
				System.out.println(sub);

				P2.add(new AttendancePanel1(rs));
				P2.add(Box.createVerticalStrut(10));

			}

			revalidate();
			repaint();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// FILL VALUES IN THE APPLICATION DURING FIRST START
	// ****************************************************************************
	private void fillApplication() {
		getGoalValue();
		getOverallValue();
		addAttendancePanel1();

	}

}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

public class MainApplication {

	public MainApplication(String loginUnsername) {

		try {
			new settings1(loginUnsername);
			System.out.println("username passed");
		} catch (Exception e) {
			System.out.println("void main() exception: " + e.getMessage());
		}

	}

}