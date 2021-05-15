//exactly all the imports I will need for the Gui to run
//i read that by not importing the whole java libraries 
//compilation time might differ but again, i did not notice a difference
import java.awt.Color;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gui implements ActionListener{
	private final ImageIcon imageIcon = new ImageIcon();//image handler
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menuBarFile;
	private JTextPane price, mileAge, description, adminFee, dailyRate, customerName, carNumber;
	private JMenuItem menuItemSave, menuItemAbout, menuItemExit;
	private JLabel priceText, yearText, descriptionText, mileAgeText, adminFeeText, dailyRateText, customerNameText, rentalDateText, returnDateText, numberOfDaysText, carNumberText; 
	private JSpinner year, numberOfDays;
	private JButton addCarToBuy, addCarToRent, buyCar, rentCar, returnCar, displayAll, clearFields;
	private JFormattedTextField rentalDate, returnDate;
	ArrayList<Car> cars = new ArrayList<Car>();
		
	
	public Gui() {
		imageIcon.setImage(Toolkit.getDefaultToolkit().getImage("image.jpg"));//setting image up
		
		//setting up the frame, decorations,  etc
		frame = new JFrame("Car Company");
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setAlwaysOnTop(true);//optional, with my 20 programs opened I usually lose the actual coursework program
		frame.setBounds(100, 100, 561, 428);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);//absolute layout, guess im olde school
		frame.setResizable(false);//optional, resizing would make it dull
		
		//big block of functions, convenient at least for me else the constructor would be 400 lines of code
		drawMenuBar();
		drawPriceTextNField();
		drawYearTextNField();
		drawMileAgeTextNField();
		drawAddCarToBuyButton();
		drawDescriptionTextNField();
		drawAdminFeeTextNField();
		drawDailyRateTextNField();
		drawAddCarToRentButton();
		drawCustomerNameTextNField();
		drawRentalDateTextNField();
		drawReturnDateTextNField();
		drawNumberOfDaysTextNField();
		drawCarNumberTextNField();
		drawBuyCarButton();
		drawRentCarButton();
		drawReturnCarTextNField();
		drawDisplayAllButton();
		drawIconNRepaint();

		//time for action :)
		frame.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {

		switch(event.getActionCommand())
		{
		case "Add Car To Buy" : addCarToBuyFunction();  break;
		case "Add Car To Rent": addCarToRentFunction(); break;
		case "Buy Car"        : buyCarFunction();		break;
		case "Rent Car"       : rentCarFunction();		break;
		case "Return Car"     : returnCarFunction();	break;
		case "Display All"    : displayAllFunction();	break;
		case "Clear Fields"   : clearFieldsFunction();	break;
		case "Save"           : try {
										saveFunction();		
									}catch (Exception e) {
										e.printStackTrace();		
									}					break;
		case "About"          : aboutFunction();		break;
		case "Exit"           : exitFunction();			break;
		}
	}
	
	public void addCarToBuyFunction(){
		
		//doing a second round of checks because if an invalid number is entered and the button is pressed without losing focus it crashes...
		try {
			Integer.parseUnsignedInt(price.getText());
			Integer.parseUnsignedInt(mileAge.getText());
			description.getText();
		}catch (NumberFormatException ex){
				clearFieldsFunction();	
				return;
        }
		
		JOptionPane.showInternalMessageDialog(null, "Successfully entered a Car to Buy in the Database!",
				"Success", JOptionPane.INFORMATION_MESSAGE);
		
		clearFieldsFunction();//defaulting the values in the fields

		//creating the object(CarToBuy) and adding it in the Arraylist
		Car newcar = new CarToBuy(description.getText(), Integer.parseUnsignedInt(price.getText()), (Integer)year.getValue(), Integer.parseUnsignedInt(mileAge.getText()));
		cars.add(newcar);
	}
	
	public void addCarToRentFunction(){
		//doing a second round of checks because if an invalid number is entered and the button is pressed without losing focus it crashes...
		try {
			Integer.parseUnsignedInt(adminFee.getText());
			Integer.parseUnsignedInt(dailyRate.getText());
			description.getText();
		}catch (NumberFormatException ex){
				clearFieldsFunction();	
				return;
        }
		
		JOptionPane.showInternalMessageDialog(null, "Successfully entered a Car to Rent in the Database!",
				"Success", JOptionPane.INFORMATION_MESSAGE);
		
		clearFieldsFunction();//defaulting the values in the fields
		
		//creating the object(CarToRent) and adding it in the Arraylist
		Car newcar = new CarToRent(description.getText(), Integer.parseUnsignedInt(dailyRate.getText()), Integer.parseUnsignedInt(adminFee.getText()));
		cars.add(newcar);
	}
	
	public void buyCarFunction(){
		//doing a second round of checks because if an invalid number is entered and the button is pressed without losing focus it crashes...
		try {
			Integer.parseUnsignedInt(carNumber.getText());//parsing as unsigned cuz we dont want value to be -1 nor <0 cuz of arraylist
		}catch (NumberFormatException ex){
			clearFieldsFunction();	
			return;
        }
		
		if(Integer.parseUnsignedInt(carNumber.getText()) != -1)
		{
			CarToBuy broughtCar;
			int check;

			//checking if we can actually buy the car, is it CarToBuy or CarToRent object, if it exists as well
			try {
				broughtCar = (CarToBuy) cars.get(Integer.parseInt(carNumber.getText()));
				check = broughtCar.buyCar(customerName.getText());
			}catch(IndexOutOfBoundsException e)
			{
				JOptionPane.showInternalMessageDialog(null, "This Car does not exist in our database BUY",
						"WARNING!", JOptionPane.WARNING_MESSAGE);
				clearFieldsFunction();
				return;
			}catch(ClassCastException c)
			{
				JOptionPane.showInternalMessageDialog(null, "You cant buy a Car to be rented",
						"WARNING!", JOptionPane.WARNING_MESSAGE);
				clearFieldsFunction();
				return;
			}catch(NumberFormatException x)
			{
				clearFieldsFunction();
				return;
			}
			
			//preventing this message from displaying if the car is already brought and the user attempts to buy it again
			if(check != 1) {
				JOptionPane.showInternalMessageDialog(null, "Car has been successfully brought by: "+ customerName.getText(),
						"Car added", JOptionPane.INFORMATION_MESSAGE);
			}
					
		}
		
	}
		
	public void rentCarFunction(){
		CarToRent rentCar;
		//doing a second round of checks because if an invalid number is entered and the button is pressed without losing focus it crashes...
		try {
			Integer.parseUnsignedInt(adminFee.getText());//usually i would run 3 ifs for
			Integer.parseUnsignedInt(dailyRate.getText());//these but also the other variables
			Integer.parseUnsignedInt(carNumber.getText());//but since we can parse it, its better
		}catch (NumberFormatException ex){
			clearFieldsFunction();
			return;
        }
		//also checking the object type, or if a drunk user entered the letter F for the price for example		
		try {
			rentCar = (CarToRent) cars.get(Integer.parseInt(carNumber.getText()));
			rentCar.rentCar(customerName.getText(), rentalDate.getText(), returnDate.getText(), (Integer)numberOfDays.getValue());
		}catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showInternalMessageDialog(null, "This Car does not exist in our database RENT",
					"WARNING!", JOptionPane.WARNING_MESSAGE);
			clearFieldsFunction();
			return;
		}catch(ClassCastException c)
		{
			JOptionPane.showInternalMessageDialog(null, "You cant buy a Car to be sold",
					"WARNING!", JOptionPane.WARNING_MESSAGE);
			clearFieldsFunction();
			return;
		}
		catch (NumberFormatException ex){
			clearFieldsFunction();	
			return;
        }
	}
	
	public void returnCarFunction(){
		CarToRent returnCar;
		
		try {
			//parsing as unsigned, we dont want -1 neither <0 so must be positive = Unsigned, also more room for new cars
			Integer.parseUnsignedInt(carNumber.getText());//not that we have 2^32-1 but oh well
		}catch (NumberFormatException ex){
			clearFieldsFunction();
			return;
        }
		
		try {
			returnCar = (CarToRent) cars.get(Integer.parseInt(carNumber.getText()));
			returnCar.carReturn();
		}catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showInternalMessageDialog(null, "This Car does not exist in our database RETURN",
					"WARNING!", JOptionPane.WARNING_MESSAGE);
			clearFieldsFunction();
			return;
		}catch(ClassCastException x)
		{
			JOptionPane.showInternalMessageDialog(null, "This is not a rental car, please try again",
					"WARNING!", JOptionPane.WARNING_MESSAGE);
			clearFieldsFunction();
			return;
		}
	}
	
	public void displayAllFunction(){
		if (cars.size() == 0)
		{
			JOptionPane.showInternalMessageDialog(null, "There is nothing is the database(yet)",
					"WARNING!", JOptionPane.WARNING_MESSAGE);
					return;
		}

		for(Car car: cars)
		{
			System.out.println(car.toString());
		}
	}
	
	public void clearFieldsFunction(){
		//reinitializing everything
		price.setText("0");
		year.setValue(2021);
		mileAge.setText("0");
		description.setText("");
		adminFee.setText("0");
		dailyRate.setText("0");
		customerName.setText("");
		rentalDate.setText("dd/MM/yyyy");
		returnDate.setText("dd/MM/yyyy");
		numberOfDays.setValue(1);
		carNumber.setText("0");
	}
	
	public void saveFunction() throws Exception{
		//saving the objects as string in a file
        File file = new File(new File("information.txt").getAbsolutePath());
        
        try
        {
        	if (file.createNewFile()) 
        	{
        		JOptionPane.showInternalMessageDialog(null, "File created successfully at: "+ new File("information.txt").getAbsolutePath(),
    					"Success!", JOptionPane.INFORMATION_MESSAGE);
        	} 
        	else {
        		int option = JOptionPane.showConfirmDialog(null, "File already exists. Do you wish to delete the existing one? ",
    					"Success!", JOptionPane.YES_NO_OPTION);
        		if(option == JOptionPane.YES_OPTION)
        		{
        			file.delete();
        			file.createNewFile();
        			JOptionPane.showInternalMessageDialog(null, "File created successfully at: "+ new File("information.txt").getAbsolutePath(),
        					"Success!", JOptionPane.INFORMATION_MESSAGE);
        		}
				else
				{
					JOptionPane.showInternalMessageDialog(null, "File not saved ",
        					"WARNING!", JOptionPane.WARNING_MESSAGE);
					return;
				}
        	}
		}catch (IOException e) {
			System.out.println("An error occurred.");
        	e.printStackTrace();
		}

		//will be executed only if file is created or user wants to overwrite previous one
        FileWriter myWriter = new FileWriter(new File("information.txt").getAbsolutePath());
        
        for(Car car: cars)
		{
			if(car.getClass() == CarToBuy.class)
			{
				myWriter.write("\n"+car.toString()+"\n");
			}
			else
			{
				myWriter.write("\n"+car.toString()+"\n");
			}
		}
        
        myWriter.close();//properly closing it
	}
	
	public void aboutFunction(){
		//cosmetic
		JOptionPane.showInternalMessageDialog(null, "This is a handy car program used to lists cars to be brought/rent and sells them/rents them to customers",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void exitFunction() {
		//cosmetic, the X top right of the window would suffice lol
		int option;
		
		option = JOptionPane.showConfirmDialog(null, "Do you really wish to exit?", null, JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION)
		{
			System.exit(0);			
		}
	}
	
	
	public static void main(String[] args) {	
		//start of program

		//trying to set default lookandfeel(metal for windows, gtk for linux or for Mac depends on the vendor)
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			System.out.println("Warning, default look in place of os");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Warning, default look in place of os");
      	}
		catch (InstantiationException e) {
			System.out.println("Warning, default look in place of os");
		}
		catch (IllegalAccessException e) {
			System.out.println("Warning, default look in place of os");
		}
       
		new Gui();//Gui test = new Gui() alternative, since test not needed...
   }
	
	/////////////////////////////////////////////////////////////////////////////////////
	///////////Here starts the compartmentalization of the constructor///////////////////
	//P.S. I could find better names but by then the deadline would be over so...///////
	////////////////////////////////////////////////////////////////////////////////////
	public void drawMenuBar()
	{
	//menubar and all of its children
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 561, 20);
		frame.getContentPane().add(menuBar);
		menuBar.setBackground(Color.DARK_GRAY);
		menuBarFile = new JMenu("File");
		menuBarFile.setForeground(Color.WHITE);
		menuBar.add(menuBarFile);
		menuItemSave = new JMenuItem("Save");
		menuBarFile.add(menuItemSave);
		menuItemSave.addActionListener(this);
		menuItemAbout = new JMenuItem("About");
		menuBarFile.add(menuItemAbout);
		menuItemAbout.addActionListener(this);
		menuItemExit = new JMenuItem("Exit");
		menuBarFile.add(menuItemExit);
		menuItemExit.addActionListener(this);
	}
	   
	public void drawPriceTextNField() {
		//price and price box field
		priceText = new JLabel("Price:");
		priceText.setForeground(Color.WHITE);
		priceText.setBounds(12, 25, 114, 21);
		frame.getContentPane().add(priceText);
		price = new JTextPane();
		price.setText("0");
		price.setToolTipText("Enter a whole number:");
		price.setBounds(12, 42, 114, 21);
		frame.getContentPane().add(price);
		price.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				try {
					Integer.parseUnsignedInt(price.getText());//unsigned because value should be >=0
				}catch (NumberFormatException ex){
					JOptionPane.showInternalMessageDialog(null, "Invalid price, please try again",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
					price.setText("0");		
		        }
			}
		});
	}

	public void drawYearTextNField() {
		//year and year box field
		yearText = new JLabel("Year:");
		yearText.setForeground(Color.WHITE);
		yearText.setBounds(138, 25, 117, 21);
		frame.getContentPane().add(yearText);
		year = new JSpinner();
		year.setModel(new SpinnerNumberModel(2021, 1920, 2021, 1));
		year.setBounds(138, 42, 117, 21);
		frame.getContentPane().add(year);
	}
	
	public void drawMileAgeTextNField() {
		//mileage and mileage box field
		mileAgeText = new JLabel("Mileage");
		mileAgeText.setForeground(Color.WHITE);
		mileAgeText.setBounds(267, 25, 112, 15);
		frame.getContentPane().add(mileAgeText);		
		mileAge = new JTextPane();
		mileAge.setText("0");
		mileAge.setBounds(267, 42, 119, 21);
		frame.getContentPane().add(mileAge);
		mileAge.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {				
				try {
					Integer.parseUnsignedInt(mileAge.getText());//unsigned because value should be >=0
				}catch (NumberFormatException ex){
					JOptionPane.showInternalMessageDialog(null, "Invalid mileage, please try again",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
					mileAge.setText("0");		
		        }
			}
		});
	}
	
	public void drawDescriptionTextNField() {
		//description and its box field
		descriptionText = new JLabel("Description:");
		descriptionText.setForeground(Color.WHITE);
		descriptionText.setBounds(12, 112, 114, 15);
		frame.getContentPane().add(descriptionText);
		description = new JTextPane();
		description.setText("");
		description.setBounds(12, 129, 114, 21);
		frame.getContentPane().add(description);
	}
	
	public void drawAddCarToBuyButton() {
		//addcartobuy button and its configurations
		addCarToBuy = new JButton("Add Car To Buy");
		addCarToBuy.setToolTipText("Red = Mandatory");
		addCarToBuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				descriptionText.setForeground(Color.red);
				priceText.setForeground(Color.red);
				yearText.setForeground(Color.red);
				mileAgeText.setForeground(Color.red);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				descriptionText.setForeground(Color.white);
				priceText.setForeground(Color.white);
				yearText.setForeground(Color.white);
				mileAgeText.setForeground(Color.white);
			}
		});
		addCarToBuy.setFocusPainted(false);
		addCarToBuy.setBorderPainted(false);
		addCarToBuy.setForeground(Color.WHITE);
		addCarToBuy.setBackground(Color.DARK_GRAY);
		addCarToBuy.setBounds(398, 25, 147, 53);
		frame.getContentPane().add(addCarToBuy);
		addCarToBuy.addActionListener(this);
	}

	public void drawAdminFeeTextNField(){
			//admin fee and its field box
			adminFeeText = new JLabel("Admin Fee:");
			adminFeeText.setForeground(Color.WHITE);
			adminFeeText.setBounds(138, 112, 117, 15);
			frame.getContentPane().add(adminFeeText);
			adminFee = new JTextPane();
			adminFee.setText("0");
			adminFee.setBounds(138, 129, 117, 21);
			frame.getContentPane().add(adminFee);
			adminFee.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {				
					try {
						Integer.parseUnsignedInt(adminFee.getText());//unsigned because value should be >=0
					}catch (NumberFormatException ex){
						JOptionPane.showInternalMessageDialog(null, "Invalid Admin Fee, please try again",
								"WARNING!", JOptionPane.WARNING_MESSAGE);
						adminFee.setText("0");		
			        }
				}
			});
	}
	
	public void drawDailyRateTextNField(){
			//daily rate and its field box
			dailyRateText = new JLabel("Daily Rate:");
			dailyRateText.setForeground(Color.WHITE);
			dailyRateText.setBounds(267, 112, 119, 15);
			frame.getContentPane().add(dailyRateText);
			dailyRate = new JTextPane();
			dailyRate.setText("0");
			dailyRate.setBounds(267, 129, 119, 21);
			frame.getContentPane().add(dailyRate);
			dailyRate.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {				
					try {
						Integer.parseUnsignedInt(dailyRate.getText());//unsigned because value should be >=0
					}catch (NumberFormatException ex){
						JOptionPane.showInternalMessageDialog(null, "Invalid Daily Rate, please try again",
								"WARNING!", JOptionPane.WARNING_MESSAGE);
						dailyRate.setText("0");		
			        }
				}
			});
	}
	
	public void drawAddCarToRentButton(){
			//addcartorent button and its configurations
			addCarToRent = new JButton("Add Car To Rent");
			addCarToRent.setFocusPainted(false);
			addCarToRent.setBorderPainted(false);
			addCarToRent.setForeground(Color.WHITE);
			addCarToRent.setBackground(Color.DARK_GRAY);
			addCarToRent.setBounds(398, 112, 147, 53);
			addCarToRent.addActionListener(this);
			frame.getContentPane().add(addCarToRent);
			addCarToRent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					descriptionText.setForeground(Color.red);
					adminFeeText.setForeground(Color.red);
					dailyRateText.setForeground(Color.red);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					descriptionText.setForeground(Color.white);
					adminFeeText.setForeground(Color.white);
					dailyRateText.setForeground(Color.white);
				}
			});
	}
	
	public void drawCustomerNameTextNField(){
			//customer's name and its field box
			customerNameText = new JLabel("Customer Name:");
			customerNameText.setForeground(Color.WHITE);
			customerNameText.setBounds(12, 191, 117, 15);
			frame.getContentPane().add(customerNameText);
			customerName = new JTextPane();
			customerName.setBounds(12, 208, 124, 21);
			frame.getContentPane().add(customerName);
	}
	
	public void drawRentalDateTextNField(){
			//rental date and its field box
			rentalDateText = new JLabel("Rental Date:");
			rentalDateText.setForeground(Color.WHITE);
			rentalDateText.setBounds(159, 191, 111, 15);
			frame.getContentPane().add(rentalDateText);
			rentalDate = new JFormattedTextField("dd/MM/yyyy");
			rentalDate.setBounds(159, 208, 107, 21);
			frame.getContentPane().add(rentalDate);
	}
	
	public void drawReturnDateTextNField(){
			//return date and its field box
			returnDateText = new JLabel("Return Date:");
			returnDateText.setForeground(Color.WHITE);
			returnDateText.setBounds(282, 191, 114, 15);
			frame.getContentPane().add(returnDateText);
			returnDate = new JFormattedTextField("dd/MM/yyyy");
			returnDate.setBounds(282, 208, 124, 21);
			frame.getContentPane().add(returnDate);
	}
	
	public void drawNumberOfDaysTextNField(){
			//numberofdays and its field box
			numberOfDaysText = new JLabel("N. of Days:");
			numberOfDaysText.setForeground(Color.WHITE);
			numberOfDaysText.setBounds(423, 191, 112, 15);
			frame.getContentPane().add(numberOfDaysText);
			numberOfDays = new JSpinner();
			numberOfDays.setModel(new SpinnerNumberModel(1, 1, null, 1));
			numberOfDays.setBounds(423, 208, 117, 21);
			frame.getContentPane().add(numberOfDays);
	}
	
	public void drawCarNumberTextNField(){
			//carnumber and its field box
			carNumberText = new JLabel("Car Number:");
			carNumberText.setForeground(Color.WHITE);
			carNumberText.setBounds(12, 249, 117, 15);
			frame.getContentPane().add(carNumberText);
			carNumber = new JTextPane();
			carNumber.setText("0");
			carNumber.setBackground(Color.WHITE);
			carNumber.setBounds(12, 266, 124, 21);
			frame.getContentPane().add(carNumber);
			carNumber.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {				
					try {
						Integer.parseUnsignedInt(carNumber.getText());//parsing as unsigned cuz we dont want value to be -1 nor <0 cuz of arraylist
					}catch (NumberFormatException ex){
						JOptionPane.showInternalMessageDialog(null, "Invalid Car Number, please try again",
								"WARNING!", JOptionPane.WARNING_MESSAGE);
						carNumber.setText("0");	
						return;
			        }
				}
			});
	}
	
	public void drawBuyCarButton(){
			//buycar button with its configurations
			buyCar = new JButton("Buy Car");
			buyCar.setFocusPainted(false);
			buyCar.setBorderPainted(false);
			buyCar.setBorder(null);
			buyCar.setForeground(Color.WHITE);
			buyCar.setBackground(Color.DARK_GRAY);
			buyCar.setBounds(159, 241, 117, 46);
			buyCar.addActionListener(this);
			frame.getContentPane().add(buyCar);
			buyCar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					customerNameText.setForeground(Color.red);
					carNumberText.setForeground(Color.red);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					customerNameText.setForeground(Color.white);
					carNumberText.setForeground(Color.white);
				}
			});
	}
	
	public void drawRentCarButton(){
			//rentcar button with its configurations
			rentCar = new JButton("Rent Car");
			rentCar.setFocusPainted(false);
			rentCar.setBorderPainted(false);
			rentCar.setForeground(Color.WHITE);
			rentCar.setBackground(Color.DARK_GRAY);
			rentCar.setBounds(295, 241, 117, 46);
			frame.getContentPane().add(rentCar);
			rentCar.addActionListener(this);
			rentCar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					customerNameText.setForeground(Color.red);
					rentalDateText.setForeground(Color.red);
					returnDateText.setForeground(Color.red);
					numberOfDaysText.setForeground(Color.red);
					carNumberText.setForeground(Color.red);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					customerNameText.setForeground(Color.white);
					rentalDateText.setForeground(Color.white);
					returnDateText.setForeground(Color.white);
					numberOfDaysText.setForeground(Color.white);
					carNumberText.setForeground(Color.white);
				}
			});
	}
	
	public void drawReturnCarTextNField(){
			//returncar button with its configurations
			returnCar = new JButton("Return Car");
			returnCar.setFocusPainted(false);
			returnCar.setBorderPainted(false);
			returnCar.setForeground(Color.WHITE);
			returnCar.setBackground(Color.DARK_GRAY);
			returnCar.setBounds(423, 241, 117, 46);
			frame.getContentPane().add(returnCar);
			returnCar.addActionListener(this);
			returnCar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					carNumberText.setForeground(Color.red);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					carNumberText.setForeground(Color.white);
				}
			});
	}
	
	public void drawDisplayAllButton(){
			//displayall button with its configurations
			displayAll = new JButton("Display All");
			displayAll.setFocusPainted(false);
			displayAll.setBorderPainted(false);
			displayAll.setBackground(Color.GREEN);
			displayAll.setBounds(60, 319, 169, 41);
			frame.getContentPane().add(displayAll);
			displayAll.addActionListener(this);

			//clearfields button with its configurations
			clearFields = new JButton("Clear Fields");
			clearFields.setFocusPainted(false);
			clearFields.setBorderPainted(false);
			clearFields.setBackground(Color.GREEN);
			clearFields.setBounds(294, 319, 169, 41);
			frame.getContentPane().add(clearFields);
			clearFields.addActionListener(this);
	}

	public void drawIconNRepaint() {
		//label used for icon paint
		JLabel iconLabel = new JLabel("", imageIcon, SwingConstants.CENTER);
		iconLabel.setForeground(Color.WHITE);
		iconLabel.setBackground(Color.LIGHT_GRAY);
		iconLabel.setBounds(0, 0, 561, 399);
		frame.getContentPane().add(iconLabel);
		
		//since the icon was downscaled, greyscaled, downbrighted by a lot, some areas were left white and thus not that good(to my eyes, I prefer dark mode)
		Label bottomRepaintLabel = new Label();
		bottomRepaintLabel.setBackground(Color.BLACK);
		bottomRepaintLabel.setBounds(0, 396, 597, 9);
		frame.getContentPane().add(bottomRepaintLabel);
		Label leftRepaintLabel = new Label();
		leftRepaintLabel.setBackground(Color.BLACK);
		leftRepaintLabel.setBounds(554, 0, 7, 399);
		frame.getContentPane().add(leftRepaintLabel);
		Label rightRepaintLabel = new Label();
		rightRepaintLabel.setBackground(Color.BLACK);
		rightRepaintLabel.setBounds(0, 10, 6, 389);
		frame.getContentPane().add(rightRepaintLabel);
	}
}