package app;
/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 * Secion: 2
 */

public class Cash extends Payment {
	
	// TODO 13: Complete class `Cash` that inherits Payment class.
	// 		 `Cash`:
	//			Subclass "private" instance variables:
	//			- cash 
	//			Extra public interfaces
	//        	- public double getChange() 
	//				calculate and return change if cash tendered is more than the paid amount.
	//				Since the smallest coin in Thailand is 0.25 Baht, for the decimal point, the change must be rounded-down to 
	//				0.25, or 0.50, or 0.75 Baht. 
	//				For example, if the amount is 199.45 and the cash is 200.00, the change must be 0.50 Baht (not 0.55 Baht).
	//				In addition, if cash tendered is less than the required amount, return 0.00.
	//		   Overrided behaviors
	//			- public boolean isValid() if the cash tendered is more than the paid amount, return true. Otherwise, return false
	//				For example: 
	//					paid amount is 3600 and cash tendered is 4000, so this method returns true
	//					paid amount is 4000 and cash tendered is 3600, so this method returns false
	// 			- public String log() return cash payment information -> amount, cash tendered, change
	//				For example: 
	//					"[VALID] CASH::3600.25::4001.00::400.75" 
	//			
	
	
	// ============ Instance Variables ============
	private double cash;	// Cash tendered is a sum of money given in payment. It may not be equal to the exact amount owed.		
	// ============================================
	
	
	// =============== Constructors ===============
	/**
	 * Constructor initializes the payment method's name as "CASH", paid amount, and cash tendered.
	 * @param paid amount 
	 * @param cash tendered
	 */
	public Cash(double amount, double cash) {
		super("CASH", amount);
		this.cash = cash;
	}
	// ============================================


	
	// YOUR CODE GOES HERE
	
	public double getChange() {
//		calculate and return change if cash tendered is more than the paid amount.
//				Since the smallest coin in Thailand is 0.25 Baht, for the decimal point, the change must be rounded-down to 
//				0.25, or 0.50, or 0.75 Baht. 
//				For example, if the amount is 199.45 and the cash is 200.00, the change must be 0.50 Baht (not 0.55 Baht).
//				In addition, if cash tendered is less than the required amount, return 0.00.	
	      if (cash < amount) {
	        return 0.0;
	      }
	      double change = cash - amount;
	        change = Math.floor(change / 0.25) * 0.25; // Round down
	        return change;
	}
	
	
	
	@Override
	public boolean isValid() {
		//if the cash tendered is more than the paid amount, return true. Otherwise, return false
		if (cash >= amount) {
		  return true;
		} else {
		  return false;
		}
	}
	
	public String log() {
		//"[VALID] CASH::3600.25::4001.00::400.75" 
		return super.log() + "::" + df.format(cash) + "::" + df.format(getChange());
	}
	
	

	
	
	
}