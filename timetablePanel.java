import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class timetablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8430384426482259252L;
	private JTable timetable;
	int maxPeriod;
	int rowIndex;
	int columnIndex;
	int columnNumber;
	ArrayList<String> periodArray = new ArrayList<String>();
	ArrayList<String> addPeriodArray = new ArrayList<String>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public timetablePanel(MySqlConn c, ArrayList<String> subArray, String uname) {
		// JPanel f = new JPanel();
		this.setMinimumSize(new Dimension(750, 400));
		this.setPreferredSize(new Dimension(750, 400));
		// this.this.setPreferredSize(new Dimension(750, 600));
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(null);

		// ------------------------------------------------------

		Object[] subDropDown = subArray.toArray();

		JComboBox subNameTT = new JComboBox();
		subNameTT.setModel(new DefaultComboBoxModel(subDropDown));
		subNameTT.setBounds(260, 12, 90, 22);
		// this.this.add(subNameTT);
		this.add(subNameTT);

		JLabel subLabel = new JLabel("Subject");
		subLabel.setBounds(200, 13, 50, 20);
		this.add(subLabel);

		JLabel dayLabel = new JLabel("Day");
		dayLabel.setBounds(20, 13, 50, 20);
		this.add(dayLabel);

		JComboBox dayNameTT = new JComboBox();
		String[] dayDropDown = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		dayNameTT.setModel(new DefaultComboBoxModel(dayDropDown));

		dayNameTT.setBounds(80, 12, 90, 22);
		this.add(dayNameTT);

		JLabel periodLabel = new JLabel("Period");
		periodLabel.setBounds(380, 13, 50, 20);
		this.add(periodLabel);

		JComboBox periodTT = new JComboBox();
		try {

			ResultSet rs = MySqlConn.statement.executeQuery("select MAX(Period) from timetable");
			rs.first();
			maxPeriod = rs.getInt(1);
			int j = 0;
			for (int i = 0; i < maxPeriod; i++) {
				periodArray.add(i + 1 + "");
				j = i + 1;
				addPeriodArray.add("Period " + j);
			}

			System.out.println(periodArray);
			System.out.println(addPeriodArray);

		} catch (Exception e1) {
			System.out.println("periodTTCombo: " + e1.getMessage());
			// JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		Object[] periodDropDown = periodArray.toArray();
		// String[] periodDropDown = new String[] { "1", "2", "3", "4", "5", "6", "7" };
		final DefaultComboBoxModel periodComboModel = new DefaultComboBoxModel(periodDropDown);
		periodTT.setModel(periodComboModel);
		periodTT.setBounds(440, 12, 90, 22);
		this.add(periodTT);

		timetable = new JTable();/*
									 * {
									 * 
									 * 
									 * private static final long serialVersionUID = -7503852963135969349L;
									 * 
									 * @Override public Component prepareRenderer(TableCellRenderer renderer, int
									 * row, int column) { Component component = super.prepareRenderer(renderer, row,
									 * column); int rendererWidth = component.getPreferredSize().width; TableColumn
									 * tableColumn = getColumnModel().getColumn(column);
									 * System.out.println("prepareRenderer:" + rendererWidth +
									 * getIntercellSpacing().width); tableColumn.setPreferredWidth(
									 * Math.max(rendererWidth + getIntercellSpacing().width,
									 * tableColumn.getPreferredWidth())); return component; }
									 * 
									 * };
									 */
		timetable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		DefaultTableModel daDefaultTableModel = new DefaultTableModel(0, 0);
		Object[] columnNames = addPeriodArray.toArray();
		// String[] columnNames = new String[] { "Period 1", "Period 2", "Period 3",
		// "Period 4", "Period 5", "Period 6", "Period 7" };
		daDefaultTableModel.setColumnIdentifiers(columnNames);

		timetable.setEnabled(false);

		timetable.setModel(daDefaultTableModel);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		timetable.setDefaultRenderer(Object.class, centerRenderer);

		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });
		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });
		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });
		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });
		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });
		daDefaultTableModel.addRow(new Object[] { null, null, null, null, null, null, null });

		timetable.setRowHeight(30);
		for (int i = 0; i < timetable.getColumnCount(); i++) {
			// timetable.getColumnModel().getColumn(i).setResizable(false);
			timetable.getColumnModel().getColumn(i).setPreferredWidth(86);
			timetable.getColumnModel().getColumn(i).setMinWidth(85);
			timetable.getColumnModel().getColumn(i).setMaxWidth(150);
		}
		timetable.setBounds(12, 145, 710, 260);

		JScrollPane scrollPane = new JScrollPane(timetable);
		scrollPane.setPreferredSize(new Dimension(600, 203));
		scrollPane.setBounds(105, 100, 604, 220);
		this.add(scrollPane);

		JButton addSubTT = new JButton("Add Class");
		addSubTT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				columnIndex = Integer.parseInt((String) periodTT.getSelectedItem()) - 1;

				switch (dayNameTT.getSelectedItem().toString()) {
				case "Monday":
					System.out.println("Monday" + columnIndex);
					rowIndex = 0;
					break;
				case "Tuesday":
					System.out.println("Tuesday" + columnIndex);
					rowIndex = 1;
					break;
				case "Wednesday":
					System.out.println("Wednesday" + columnIndex);
					rowIndex = 2;
					break;
				case "Thursday":
					System.out.println("Thursday" + columnIndex);
					rowIndex = 3;
					break;
				case "Friday":
					System.out.println("Friday" + columnIndex);
					rowIndex = 4;
					break;
				case "Saturday":
					System.out.println("Saturday" + columnIndex);
					rowIndex = 5;
					break;

				}
				timetable.setValueAt(subNameTT.getSelectedItem(), rowIndex, columnIndex);

				/*
				 * for (int i = 0; i < timetable.getColumnCount(); i++) { //
				 * timetable.getColumnModel().getColumn(i).setResizable(false);
				 * System.out.println(timetable.getColumnModel().getColumn(i).getMaxWidth());
				 * System.out.println(timetable.getColumnModel().getColumn(i).getPreferredWidth(
				 * )); System.out.println(timetable.getColumnModel().getColumn(i).getMinWidth()
				 * + "x"); }
				 */
				// timetable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				// resizeColumnWidth(timetable);
				// TableColumnModel columnModel = timetable.getColumnModel();
				// columnModel.getColumn(0).setMinWidth(100);
				// timetable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			}
		});
		addSubTT.setBounds(570, 11, 100, 25);
		this.add(addSubTT);

		JButton addPeriodTT = new JButton("Add Period");
		columnNumber = maxPeriod;
		addPeriodTT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt((String) periodTT.getSelectedItem()) <= columnNumber) {
					System.out.println(periodTT.getSelectedItem());
					columnNumber++;
					daDefaultTableModel.addColumn("Period " + columnNumber);
					periodComboModel.addElement(columnNumber + "");

				}
			}
		});
		addPeriodTT.setBounds(570, 62, 100, 25);
		this.add(addPeriodTT);

		JLabel satLabel = new JLabel("Saturday");
		satLabel.setBounds(26, 273, 51, 29);
		this.add(satLabel);

		JLabel friLabel = new JLabel("Friday");
		friLabel.setBounds(26, 243, 35, 29);
		this.add(friLabel);

		JLabel thursLabel = new JLabel("Thursday");
		thursLabel.setBounds(26, 213, 53, 29);
		this.add(thursLabel);

		JLabel wedLabel = new JLabel("Wednesday");
		wedLabel.setBounds(26, 183, 66, 29);
		this.add(wedLabel);

		JLabel tueLabel = new JLabel("Tuesday");
		tueLabel.setBounds(26, 153, 48, 29);
		this.add(tueLabel);

		JLabel monLabel = new JLabel("Monday");
		monLabel.setBounds(26, 123, 44, 29);
		this.add(monLabel);
		// this.setLocationRelativeTo(null);
		// this.setVisible(true);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < timetable.getRowCount(); i++) {
					for (int j = 0; j < timetable.getColumnCount(); j++) {
						if (timetable.getValueAt(i, j) != null) {
							String day, period, subject;
							day = dayDropDown[i];
							period = (String) periodComboModel.getElementAt(j);
							subject = (String) timetable.getValueAt(i, j);
							System.out.println("day:period:Sub = " + day + ":" + period + ":" + subject);

							String tTableInsert = "insert into timetable values ('" + (uname) + "', '" + (day) + "', '"
									+ (period) + "', '" + (subject) + "')";
							// insert into attendance values ('pt', 'Java', '20-03-15', 1, 3);
							String tTableUpdate = "update timetable set Subject = '" + (subject)
									+ "' where  Username = '" + (uname) + "' && Day = '" + (day) + "' &&  Period = '"
									+ (period) + "'";

							try {

								if (!MySqlConn.statement
										.executeQuery("select * from timetable where Username = '" + (uname)
												+ "' && Day = '" + (day) + "' &&  Period = '" + (period) + "'")
										.isBeforeFirst()) {

									MySqlConn.statement.executeUpdate(tTableInsert);
								} else {
									MySqlConn.statement.executeUpdate(tTableUpdate);
								}
							} catch (Exception e1) {
								System.out.println("SAVE TT: " + e1.getMessage());
								// JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						}
					}
				}
			}
		});
		saveButton.setBounds(260, 326, 97, 25);
		this.add(saveButton);

		JButton discardButton = new JButton("Discard");
		discardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int discard = JOptionPane.showConfirmDialog(null, "Are you sure?", "Discard Changes",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				System.out.println("Discard: " + discard);
				if (discard == 0) {
					Window win = SwingUtilities.getWindowAncestor(getParent());
					win.dispose();
				}

			}
		});
		discardButton.setBounds(392, 326, 97, 25);
		this.add(discardButton);

		JSeparator separator = new JSeparator();
		separator.setBounds(560, 49, 120, 2);
		this.add(separator);
		// this.setVisible(true);

		// FUNCTIONALITIES
		// ----------------------------------------------------------------------------

		try {
			String createTT = "CREATE TABLE IF NOT EXISTS timeTable (Username varchar(20), Day varchar(20), Period int(2), Subject varchar(20), "
					+ "primary key(Username, Day, Period), "
					+ "foreign key(Username, Subject) references attendancesubject(Username, subjects) ON DELETE CASCADE ON UPDATE CASCADE)";

			String retrieveTT = "select Day, Period, Subject from timetable where Username = '" + (uname) + "'";

			MySqlConn.statement.executeUpdate(createTT);
			ResultSet rs = MySqlConn.statement.executeQuery(retrieveTT);
			if (!rs.isBeforeFirst()) {
				System.out.println("Data doesn't exists");
			} else {
				System.out.println("Data exists");
				String day, subject;
				int period;
				while (rs.next()) {
					day = rs.getString(1);
					period = rs.getInt(2);
					subject = rs.getString(3);

					System.out.println(day + ":" + period + ":" + subject);

					rowIndex = Arrays.asList(dayDropDown).indexOf(day);
					// int indexOfTwo = ArrayUtils.indexOf(dayDropDown, 2);
					columnIndex = period - 1;

					// System.out.println();

					System.out.println(rowIndex + ":" + columnIndex + ":" + subject);

					timetable.setValueAt(subject, rowIndex, columnIndex);

				}
				System.out.println("Data retrieved\n");
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 300)
				width = 300;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	/*
	 * public static void main(String[] args) { JFrame f = new JFrame(); f.add(new
	 * testFunction()); f.setMinimumSize(new Dimension(750, 400));
	 * f.setPreferredSize(new Dimension(750, 400)); f.setVisible(true); }
	 */
}
