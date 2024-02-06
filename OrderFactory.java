package app;
/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;

public class OrderFactory {
	
    
    public static Order createOneOfEachOrder(String customerName, String[] paymentInfo) {
    	
    	Customer c = new Customer(customerName);
        Order order = new Order(c);
        for (int barcode = 1; barcode <= ItemFactory.MAX_BARCODE; barcode++) {
            order.addItem(ItemFactory.createItem(barcode, 1));
        }
        
        if(paymentInfo != null) {
        	order.setPayment(paymentInfo);
    	}
    	
    	return order;
    }
    
    

    /**
     * Read the text file at `filepath` and create Order using the information in the file.
     *
     * The format of the file is:
     * Customer Name 
     * barcode1 quantity1
     * barcode2 qunatity2
     * . . .
     * NONE or Payment Data (at the last line)
     *
     * If the quantity is negative, it means that the customer wants to reduce the items.
     *
     * For example:
     * Hesitant Customer
     * 1 20
     * 3 10
     * 2 5
     * 1 -10
     * 1 -5
     * 2 5
     * 1 -10
     * 1 -100
     * 2 -10
     * NONE
     *
     * In this example, only items with the barcode of 3 is left (quantity = 10) and there is no payment method.
     *
     * @param filepath
     * @return Order | null
     */
    public static Order createOrderFromFile(String filepath) {
        // TODO 26: Implement createOrderFromFile(String filepath)
    	    // Create a Gson object with a custom adapter for Payment objects
    	try { FileReader file = new FileReader(filepath);
        BufferedReader rd = new BufferedReader(file);
        
        String name = rd.readLine();
        Order order = null;
      //Funny_N@me (funny@mail.com, 10700)
        String pattern = "([\\W\\w ]+) \\(([\\w\\W ]+), (\\d{5})\\)"; //digit
        //check online or not
        Pattern check = Pattern.compile(pattern);
        Matcher matcher = check.matcher(name);
        if(name.matches(pattern)){
    		if(matcher.find()) {
    			order = new Order(new OnlineCustomer(matcher.group(1),matcher.group(2),matcher.group(3)));
    		}
    	}else order = new Order(new Customer(name));
    	
        String l; //line
      while((l = rd.readLine())!=null &&  !l.equals("NONE") && !l.contains("::")){
      	String[] splitt = l.split(" ");
      	int barcode= Integer.parseInt(splitt[0]);
      	int quantity= Integer.parseInt(splitt[1]);

      	if(quantity>0){
    		order.addItem(ItemFactory.createItem(barcode, quantity));
    	}else {
    		//1 -100
    		order.reduceItem(barcode, -quantity);
    	}
     }
      if(!l.contains("NONE") && l != null){
  		String[] paymentInfo = l.split("::");
  		order.setPayment(paymentInfo);
      } return order;
    }catch(IOException e){
    	return null;
    }
    }

    /**
     * Write `order` into a file at `filepath`. The format of the output is:
     *
     * Sale Person: Student Full Name (Student ID)
     * <order log()>
     *
     * For example:
     * Sale Person: Siripen Pongpaichet (6488000)
	 * Customer: 1: Thanapon Noraset
	 * - Sinovac\t3000.00\t2 (doses)\t6000.00
	 * - AstraZeneca\t300.00\t1 (doses)\t300.00
	 * Total: 6300.00
	 * [VALID] CASH::6300.00::7000.00::700.00
     *
     * @param order
     * @param filepath
     * @throws IOException
     */
    public static void writeOrderText(Order order, String filepath) throws IOException {
        // TODO 27: Implement writeOrderText(Order order, String filepath)
    	File file = new File(filepath);
    	PrintWriter wt = new PrintWriter(file);
    	wt.println("Sale Person: Nichakul Kongnual (6588178)\n");
        wt.println(order.log());
        wt.close();
    }
    
    /**
     * Write `order` into a file at `filepath`. The format of the output is in JSON:
     * 
-----------------------------------------------------
{
  "orderID": 4,
  "customer": {
    "customerID": 1,
    "name": "Thanapon Noraset"
  },
  "items": [
    {
      "name": "Sinovac",
      "price": 3000.0,
      "quantity": 2
    },
    {
      "name": "AstraZeneca",
      "price": 300.0,
      "quantity": 1
    },
    
  ],
  "payment": {
    "type": "Cash",
    "properties": {
      "cash": 7000.0,
      "amount": 6300.0,
      "method": "CASH"
    }
  }
}
-----------------------------------------------------
     * 
     * @param order
     * @param filepath
     * @throws JsonIOException
     * @throws IOException
     */
    public static void writeOrderJson(Order order, String filepath) throws JsonIOException, IOException {
    	// TODO 28: Implement writeOrderJson(Order order, String filepath)
    	// Hint. Since the Payment is an abstract class which cannot be constructed,
    	// so you will need to apply your custom serializer and deserializer.
    	// If you use Gson library, you can register GsonBuilder with PaymentAdapter class  
    	// (already provided - see the PaymentAdapter.java file for more detail).
    	// However, feel free to explore other methods and create any additional classes or methods as needed.
    	GsonBuilder gsonBuilder = new GsonBuilder();
    		gsonBuilder.registerTypeAdapter(Payment.class, new PaymentAdapter());
    		Gson gson = gsonBuilder.setPrettyPrinting().create();
    	String json = gson.toJson(order);
    	PrintWriter wt = new PrintWriter(filepath);
    	 	wt.println(json);
    	 	wt.close();
    }	
}
