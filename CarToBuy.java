import javax.swing.JOptionPane;

/**
 * class CarToBuy, subclass of Car
 * it is about buying cars
 */
public class CarToBuy extends Car
{
    // variables price, registration year, mileage, sold
    private int price, regYear, mileage;
    private boolean sold;
		
    public CarToBuy(String theCarDesc, int cost, int year, int miles)
    {
        // initialising the instance values above,calling description from 
        // class Car...
        //also setting up/cleaning the console
        super(theCarDesc);
        price = cost;
        regYear = year;
        mileage = miles;
        sold = false;
        //System.out.println("\f");
    }

    public int getPrice()
    {
        //accessor method for price
        return price;
    }

    public int getYear()
    {
        //accessor method for Registration year
        return regYear;
    }

    public int getMiles()
    {
        //accessor method for the Mileage
        return mileage;
    }

    public boolean getSold()
    {
        //accessor method if the car is sold
        return sold;
    }

    public void setPrice(int carPrice)
    {
        //checking if car is sold
        if(sold == false){
            price = carPrice;
        }
        else{
            System.out.println("The Car is already sold; Price change aborted");
            return;
        }
    }

    public int buyCar(String name)
    {
        //checking if car is sold
        if(sold == true){
        	JOptionPane.showInternalMessageDialog(null, "This Car is already brought",
					"WARNING", JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        else{
            setCustName(name);
            sold = true;
            return 0;
        }
    }

    public void displayDetails()
    {
        //clearing the console for better readability and displaying the info organised
        super.displayInfo();
        if(sold == false){
            System.out.println("Price:\t\t   " +price+ "£");
            System.out.println("Registration Year: " +regYear);
            System.out.println("Miles:\t\t   " +mileage+ "km");
        }
    }
    
	//@Override
	public String toString() {	
		if(sold == false)
		{
		return ("\nCar Number: "+ this.carNumber+
				" \nDescription: "+this.carDesc+
				" \nPrice: "+ this.price +
				" \nRegistration Year: "+ this.regYear +
				" \nMileage : " + this.mileage);
		}
		else {
			if(custName == "")
	        {
	            System.out.println();
	    		return ("\nCar Number: "+ this.carNumber+
	    				" \nDescription: "+this.carDesc+
	    				" \nPrice: "+ this.price +
	    				" \nRegistration Year: "+ this.regYear +
	    				" \nMileage : " + this.mileage);
	            
	        }
			else
			{
				return ("\nCar Number: "+ this.carNumber+
	    				" \nDescription: "+this.carDesc+
	    				" \nCustomer's Name:   " +custName+
	    				" \nPrice: "+ this.price +
	    				" \nRegistration Year: "+ this.regYear +
	    				" \nMileage : " + this.mileage);
			}
		}
	}
}
