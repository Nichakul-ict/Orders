package app;

import app.CreditCard.CardType;

/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 * Secion: 2
 */


public class PaymentFactory {


	/**
	 * Crate a Payment object according to the given parameters
	 * 
	 * @param amount
	 *            total amount that need to be paid
	 * @param args
	 *            list of parameters depends on the payment type. For example, Cash
	 *            payment only requires cash tendered, while CreditCard payment
	 *            requires CardType, and card's number
	 * @return 
	 * 		Payment	
	 * @throws IllegalArgumentException
	 * 			  - if the payment type is unavailable => not [CASH|CARD],
	 * 			    throw IllegalArgumentException exception with a specific message 
	 * 				"xx is an unavailable payment type." where xxx is the unavailable input argument
	 * 
	 * 			  - if the card type is unavailable => not [VISA|AMERICANEXPRESS|JCB|MASTERCARD] 
	 * 			    throw IllegalArgumentException exception with a specific message 
	 * 				"xxx is an unavailable card type." where xxx is the unavailable input argument
	 * 
	 * @throws IndexOutOfBoundsException
	 * 			  - if the number of arguments is insufficient to construct the payment,
	 * 			    throw IndexOutOfBoundsException exception
	 * 			    For example, Cash payment needs 2 arguments: amount and cash value, 
	 * 							 CreditCard payment needs 3 arguments: amount, card's type, and card's number
	 * 
	 */
	public static Payment createPayment(double amount, String[] args)
			throws IllegalArgumentException, IndexOutOfBoundsException {

		String type = args[0];
		
		if (type.equalsIgnoreCase("CASH")) {
			// TODO 15: Create `Cash` payment class by parsing arguments according to the
			// Cash constructor method
			try{ 
				return new Cash(amount,Double.parseDouble(args[1]));
            }catch(IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException(Integer.toString(args.length));
            }
		} else if (type.equalsIgnoreCase("CARD")) {
			// TODO 16: Create `CreditCard` payment class by parsing arguments according to
			// the CreditCard constructor method 
			//public CreditCard(double amount, CardType type, String number)
			//"xxx is an unavailable card type."
			
			String cardType = args[1].toUpperCase();
		    String[] card = {"VISA","AMERICANEXPRESS","JCB","MASTERCARD"};
		    boolean have = false;
		    for (String t : card) {
		        if (t.equals(cardType)) {
		            have = true;
		            break;
		        }
		    }
		        if (!have) {
		            throw new IllegalArgumentException(cardType + " is an unavailable card type.");
		        }
		        
		        else if(args.length != 3) {
		            throw new IndexOutOfBoundsException("2");    
		        }
		        
		        String cardNumber = args[2];
		     switch (cardType) {
		           case "VISA":
		               return new CreditCard(amount, CardType.VISA, cardNumber);
		           case "AMERICANEXPRESS":
		               return new CreditCard(amount, CardType.AMERICANEXPRESS, cardNumber);
		           case "JCB":
		               return new CreditCard(amount, CardType.JCB, cardNumber);
		           case "MASTERCARD":
		               return new CreditCard(amount, CardType.MASTERCARD, cardNumber);
		           default:
		               throw new IllegalArgumentException(cardType + " is an unavailable card type.");
		     }
		} else {
			// DO NOT MODIFY
			throw new IllegalArgumentException(type + " is an unavailable payment type.");
		}

	}
}
