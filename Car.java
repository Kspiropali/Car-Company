/**
 * Superclass Car designed to be the mother of classes CarToRent && CarTo Buy
 * Stores description && names of customers
 * solely done by @Kristian Spiropali 
 */
public class Car
{
    // instance variables of Customer Name and  Car description, protected so children inherit them
    protected String custName;
	protected String carDesc;
    private static int numberGenerator = 0;//proper way of a number generator
    protected int carNumber = numberGenerator++;

    //constructor
    public Car(String theCarDesc)
    {
        // initializing our variables
        setCustName("");
        carDesc = theCarDesc;     
    }

    public String getCarDesc()
    {
        //get the car's description
        return carDesc;
    }

    public String getCustName()
    {
        //get our customer's name
        return custName;
    }

    public void changeCustName(String cName)
    {
            setCustName(cName);       
    }

    public void displayInfo()
    {
        //cleaning the console and presenting name(if applicable) and description  
        if(custName != " ")
        {
            System.out.println("\nCustomer's Name:   " +custName);
        }
        System.out.println("Car Description:   " +carDesc);
    }

	public void setCustName(String custName) {
		this.custName = custName;
	}
}