package me.pgk1216.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;
	private JTextField inputField;
	private JTextField convertedField;
	private JComboBox startBox;
	private JComboBox toBox;
	private JButton resetButton;
	private JButton convertButton;
	private JLabel converted;
	
	private String[] conversionIndex = { "Error", "USD", "EURO", "YEN", "WON" };
	private static HashMap<String, Double> conversions = new HashMap<String, Double>();
	
	double result = 0.0;
	double input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		loadHashMap();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	private static void loadHashMap() {
		conversions.put("EUR", 0.830897);
		conversions.put("GBP", 0.717356);
		conversions.put("YEN", 107.969169);
		conversions.put("WON", 1117.712009);
		conversions.put("YUAN", 6.492864);
		conversions.put("RUPEES", 75.434882);
	}
	
	private void reset() {
		startBox.setSelectedIndex(0);
		toBox.setSelectedIndex(0);
		inputField.setText(null);
		convertedField.setText(null);
	}
	
	private double toUSD(double input, String start) {
		if(!start.equals("USD")) return (input / conversions.get(start));
		
		return input;
	}
	
	private double fromUSD(double input, String to) {
		if(!to.equals("USD")) return (input * conversions.get(to));
		
		return input;
	}
	
	public double convertCurrency(double input, String start, String to) {
		return fromUSD(toUSD(input, start), to);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Amount:");
		lblNewLabel.setBounds(10, 65, 63, 25);
		frame.getContentPane().add(lblNewLabel);
		
		// Here is where the input goes
		inputField = new JTextField();
		inputField.setBounds(93, 67, 96, 20);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
		
		JLabel titleLabel = new JLabel("Currencyverter");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		titleLabel.setBounds(145, 11, 144, 27);
		frame.getContentPane().add(titleLabel);
		
		converted = new JLabel("Converted:");
		converted.setBounds(10, 120, 73, 14);
		frame.getContentPane().add(converted);
		
		convertedField = new JTextField();
		convertedField.setBounds(93, 117, 96, 20);
		frame.getContentPane().add(convertedField);
		convertedField.setColumns(10);
		
		startBox = new JComboBox();
		startBox.setModel(new DefaultComboBoxModel(new String[] {"Select Initial Currency", "USD", "EURO", "YEN", "WON"}));
		startBox.setBounds(217, 66, 181, 22);
		frame.getContentPane().add(startBox);
		
		toBox = new JComboBox();
		toBox.setModel(new DefaultComboBoxModel(new String[] {"Select Converted Currency", "USD", "EURO", "YEN", "WON"}));
		toBox.setBounds(217, 116, 181, 22);
		frame.getContentPane().add(toBox);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		resetButton.setBounds(173, 212, 96, 23);
		frame.getContentPane().add(resetButton);
		
		convertButton = new JButton("Convert");
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Button pressed now convert
				try {
					// Converts input string to double
					input = Double.parseDouble(inputField.getText());
					
					// UserError: if the startBox or toBox is still on the default index aka index 0
					if(startBox.getSelectedIndex() == 0 || toBox.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(null, "Invalid input/units!");
						reset();
					}
					
					// Cover all possible combinations
					// Convert the current currency to USD as base, then calculate to converted value
					double convertedDouble = convertCurrency(input, conversionIndex[startBox.getSelectedIndex()], conversionIndex[toBox.getSelectedIndex()]);
					DecimalFormat df = new DecimalFormat("###.##");
					convertedField.setText(df.format(convertedDouble) + "");
					
				} catch(Exception error) {
					// This will warn the user if the input is invalid
					JOptionPane.showMessageDialog(frame, error.getMessage());
				}
			}
		});
		convertButton.setBounds(173, 164, 96, 23);
		frame.getContentPane().add(convertButton);
	}
}
