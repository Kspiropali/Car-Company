import javax.swing.JOptionPane;

/**
 * class CarToRent,subclass of Car
 * it is about renting cars
 */
public class CarToRent extends Car
{
    //variables rental date, return date, admin fee, number of days, daily rate, total accumulated and on loan
    private String rentalDate, returnDate;
    private int adminFee, nOfDays, dailyRate, totalAccum;
    private boolean onLoan;

    public CarToRent(String theCarDesc, int fee, int rate)
    {
        //initializing and fetching car's description from superclass Car
        super(theCarDesc);
        adminFee = fee;
        dailyRate = rate;
        rentalDate = "";
        returnDate = "";
        nOfDays = 0;
        totalAccum = 0;
    }

    public String getRentalDate()
    {
        //accessor method for the car's Rental Date
        return rentalDate;
    }

    public String getReturnDate()
    {
        //accessor method for the car's Return Date
        return returnDate;
    }

    public int getAdminFee()
    {
        //accessor method for the Admin Fees
        return adminFee;
    }

    public int getNumberOfDays()
    {
        //accessor method for the Number of days the car will be rented
        return nOfDays;
    }

    public int getDailyRate()
    {
        //accessor method for the Daily Rate
        return dailyRate;
    }

    public int getTotalAccumulated()
    {
        //accessor method for Total Money Accumulated
        return totalAccum;
    }

    public boolean getLoanStatus()
    {
        //accessor method for Loan Status
        return onLoan;
    }

    public void setDailyRate(int dailyRate)
    {
        ////mutator method for chaning the Daily Rates on demand
        System.out.println("Daily Rate successfully changed!");
    }

    public void setAdminFees(int adminFee)
    {
        //mutator method for chaning the Admin fees on demand
        System.out.println("Admin fees successfully changed!");
    }

    public void rentCar(String name, String rentDate, String retuDate, int days)
    {
        //if car is on load this method will exit fast without making any other checks or changes
        if(onLoan == true){
        	JOptionPane.showInternalMessageDialog(null, "This car is already on loan, unavailable until " +returnDate,
					"WARNING!", JOptionPane.WARNING_MESSAGE);          
            return;
        }

        //calling changeCustName method from superclass Car
        changeCustName(name);

        //taken from user input
        rentalDate = rentDate;
        returnDate = retuDate;
        nOfDays = days;

        //changing loan status
        onLoan = true;

        //multiplying the daily rate by the number of days and then adding the admin fee
        totalAccum = dailyRate * nOfDays + adminFee;
        JOptionPane.showInternalMessageDialog(null, "Car is Rent to: " +custName,
				"Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void carReturn()
    {
        //checking now the on loan status since if its not needed the method will exit instead of doing if/\else...
        if(onLoan == false){
        	JOptionPane.showInternalMessageDialog(null, "This Car is not on loan",
					"WARNING!", JOptionPane.WARNING_MESSAGE); 
            return;
        }
        //reinitializing the parameters since car is not on loan
        changeCustName("");
        nOfDays = 0;
        rentalDate = "dd/MM/yyyy";
        returnDate = "dd/MM/yyyy";
        onLoan = false;
    }

    public void getDescNTotal()
    {
        //displays the car description and money accumulated, also cleaning terminal
        System.out.println("\f");
        System.out.println("Car's Description: \t  " +getCarDesc());
        System.out.println("Total Accumulated Money:  " +totalAccum+ "£");
    }

    public void displayCarDetails()
    {
        //cleaning the console for readability and showing everything organised
        displayInfo();
        System.out.println("The Daily Rate is:                        " +dailyRate+ "£");
        System.out.println("The Admin Fee is :                        " +adminFee+ "£");
        if(onLoan == true){
            System.out.println("The Rental Date started from:             " +rentalDate);
            System.out.println("The Return date is due on:                " +returnDate);
            System.out.println("The number of days this car was rented is:" +nOfDays+ "days");  
        }
    }
    
    //giving the display option proper format for the CarToRent Objects
    @Override
  	public String toString() {	
  		if(custName == "" || rentalDate == "dd/MM/yyyy" || returnDate == "dd/MM/yyyy")
  		{
  			return ("\nCar Number: "+ this.carNumber+
  	  				"\nDescription: "+this.carDesc+
  	  				"\nFee: "+ this.adminFee +
  	  				"\nDaily rate: "+ this.dailyRate);
  		}
  		else
  		{
  	  		return ("\nCar Number: "+ this.carNumber+
  	  				"\nDescription: "+this.carDesc+
  	  				"\nCustomer's Name:" +this.custName+
  	  				"\nFee: "+ this.adminFee +
  	  				"\nDaily rate: "+ this.dailyRate +
  	  				"\nRental Date: "+ this.rentalDate+
  	  				"\nReturn Date: "+ this.returnDate+
  	  				"\nNumber of Days: "+this.nOfDays); 			
  		}
  	}
}