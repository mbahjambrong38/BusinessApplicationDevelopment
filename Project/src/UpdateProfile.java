import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UpdateProfile extends JInternalFrame implements ActionListener{
	String userId;
	
	Vector<Integer>day;
	Vector<Integer>month;
	Vector<Integer>year;
	
	JLabel labeljudul = new JLabel("Update my Profile");
	JLabel labelEmail = new JLabel("Email");
	JLabel labelPassword = new JLabel("Password");
	JLabel labelBirth = new JLabel("Birthdate");
	JLabel labelGender = new JLabel("Gender");
	JLabel labelAddress = new JLabel("Address");
	
	JTextField txtEmail = new JTextField();
	JTextArea txtAddress = new JTextArea();
	
	JPasswordField txtPassword = new JPasswordField();
	
	JComboBox comboDay;
	JComboBox comboMonth;
	JComboBox comboYear;
	
	JRadioButton radioAgender = new JRadioButton("Agender");
	JRadioButton radioFemale = new JRadioButton("Female");
	JRadioButton radioMale = new JRadioButton("Male");
	
	ButtonGroup genderGroup;
	
	JButton buttonDone = new JButton("Done");
	
	JPanel panelGenderGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel panelComboBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(0, 2, 5, 5));
	JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
	
	Connect c = new Connect();
	
	public void generateComboBox() {
		day = new Vector<>();
		year = new Vector<>();
		month = new Vector<>();
		
		for (int i = 1; i <= 31; i++) {
			day.add(i);
		}
		for (int i = 1910; i <= 2018; i++) {
			year.add(i);
		}
		for (int i = 1; i <= 12; i++) {
			month.add(i);
		}
		comboDay = new JComboBox<>(day);
		comboYear = new JComboBox<>(year);
		comboMonth = new JComboBox<>(month);
		
		panelComboBox.add(comboDay);
		panelComboBox.add(comboMonth);
		panelComboBox.add(comboYear);
	}
	
	public void generateGenderGroup() {
		genderGroup = new ButtonGroup();
		genderGroup.add(radioAgender);
		genderGroup.add(radioFemale);
		genderGroup.add(radioMale);
		
		panelGenderGroup.add(radioAgender);
		panelGenderGroup.add(radioFemale);
		panelGenderGroup.add(radioMale);
	}
	
	public void generateForm() {
		labeljudul.setFont(new Font("TimesRoman", Font.BOLD, 20));
		txtEmail.setEditable(false);
		panelNorth.add(labeljudul);
		
		panelCenter.add(labelEmail);
		panelCenter.add(txtEmail);
		panelCenter.add(labelPassword);
		panelCenter.add(txtPassword);
		panelCenter.add(labelBirth);
		panelCenter.add(panelComboBox);
		panelCenter.add(labelGender);
		panelCenter.add(panelGenderGroup);
		panelCenter.add(labelAddress);
		panelCenter.add(txtAddress);
		
		panelSouth.add(buttonDone);
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
		
		buttonDone.addActionListener(this);

	}
	
	public boolean validationPassword(String checkPassword) {
		int validationAlphabet = 0;
		int validationNumeric = 0;
		for (int i = 0; i < checkPassword.length(); i++) {
			if (Character.isLetter(checkPassword.charAt(i))) {
				validationAlphabet++;
			}else if (Character.isDigit(checkPassword.charAt(i))) {
				validationNumeric++;
			}
		}
		if (validationAlphabet == 0 || validationNumeric == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public String validationGender() {
		if (radioAgender.isSelected()) {
			return "Agender";
		}else if (radioFemale.isSelected()) {
			return "Female";
		}else if (radioMale.isSelected()) {
			return "Male";
		}else {
			return "-";
		}
	}
	
	public void setId(String userId) {
		generateComboBox();
		generateGenderGroup();
		generateForm();
		this.userId = userId;
	}

	public UpdateProfile() {
		
		this.setLayout(new BorderLayout(0,20));
		
		
		this.setTitle("REGISTER FORM");	
		this.setResizable(true);
		this.setClosable(true);	
		this.setSize(500, 325);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonDone) {
			String checkPassword = txtPassword.getText();
			String address = txtAddress.getText();
			String dob = comboYear.getSelectedItem().toString() + "-" +  comboMonth.getSelectedItem().toString() + "-" + comboDay.getSelectedItem().toString();
			String gender = validationGender();
			String password = txtPassword.getText();
			if (validationPassword(checkPassword) == false) 
				JOptionPane.showMessageDialog(null, "Password must Alphanumeric", "Warning", JOptionPane.WARNING_MESSAGE);
			else if (validationGender() == "-") 
				JOptionPane.showMessageDialog(null, "Gender must be checked", "Warning", JOptionPane.WARNING_MESSAGE);
			else if (txtAddress.getText().length() < 10 || txtAddress.getText().length() > 30) 
				JOptionPane.showMessageDialog(null, "Address must be between 10 - 30 characters", "Warning", JOptionPane.WARNING_MESSAGE);
			else {
				JOptionPane.showMessageDialog(null, "Done", "Complete", JOptionPane.INFORMATION_MESSAGE);
				c.updateProfile(password, dob, gender, address, userId);
				this.dispose();
			}
		}
		
	}

}
