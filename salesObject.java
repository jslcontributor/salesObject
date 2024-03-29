import java.util.*;
import java.io.*;



/* Filename: salesObject.java
 * Author: Justin Lee
 * Date: 10 December 2019
 * Description:  This will be the main driver for the program
 */


public class salesObject{
  
   //we instantiate local variables here, this is because we are assuming the inputs
   //are robust therefore we are going to assume that these variables are correctly set
   //at each iteration of the while loop within the for loop
   private int quantity;
   private boolean imported;
   private String name;
   private double price; 
   private boolean exempt;




   public salesObject() { //default constructor
      //we can leave this empty


   }
  
   //partial constructor is unnecessary as well 
   

   //this would be the partial constructor 
   public salesObject(int q, boolean i, String n, double p){
      this.quantity = q;
      this.imported = i;
      this.name = n;
      this.price = p;
      this.exempt = false;
   }

   //this is the full constructor
   public salesObject(int q, boolean i, String n, double p, boolean e){
      this.quantity = q;
      this.imported = i;
      this.name = n;
      this.price = p;
      this.exempt = e;
 
   }

   //getters (we do not need setters) (potentially we do not need this
   public int getQuantity(){
      return this.quantity;
   }

   public boolean getImport(){
      return this.imported;
   }
   
   public String getName(){
      return this.name;
   }
   
   public double getPrice(){
      return this.price;
   }
   
   public boolean getExempt(){
      return this.exempt;
   } 


   //this method will be used to print info from the salesObject
   //this method can be unit tested
   public void printInfo(){
      System.out.printf("%d ", this.quantity);
      //determine if we print imported
      if(this.imported){
         System.out.printf("imported ");
      }
      System.out.printf("%s: %.2f\n", this.name, this.price);
         
   }

   public static void main(String[] args){
      File input1;
      StringTokenizer st;
      String line;
      String curr;
      
      int quantity;
      boolean imported;
      boolean exempted;
      String name;
      double price;      
      totalSales salesTotal = new totalSales();
      String inputtxt = "input"; 
      Scanner scan;

      /* We will instantiate a hashMap here so that we can use it as a dictionary
       * to check whether the name of the product is in the list of tax exempt
       * items
       */
       HashMap<String, Integer> dictionary = new HashMap<String, Integer> ();
       dictionary.put("chocolate bar", 1);
       dictionary.put("box of chocolates", 1);
       dictionary.put("book", 1);
       dictionary.put("packet of headache pills", 1);

 
      //we set up this for loop so that we can iterate through input1, input2, etc
     
      //we now have to separate the output based on which input file it is from

      for(int i=1; i<= 3; i++){
         System.out.printf("Output %d:\n", i);
         input1 = new File(inputtxt + i + ".txt");
         try{
            scan = new Scanner(input1);
         }
         catch(Exception e){return; } //we are assuming that the input is robust. 
         //this while loop will scan the file.  within the while loop, the first word
         //will be the quantity of the object while the second word will determine
         //if the item is imported or not and that is determined by checking if the
         //second word is "imported" or not.  therefore
         


         while(scan.hasNextLine()){
          
         //when we tokenize the string:
         //The first token will be the quantity
         //If the next token is "imported" then we can set the boolean to true
         //the next tokens until the token "at" will be the name of the product
         //the last token will be parsed into the a double and will be the price
         //of the item

            line = scan.nextLine(); //we need to tokenize this string
            st = new StringTokenizer(line);
            curr = st.nextToken(); //we know that the first token is the quantity
            quantity = Integer.parseInt(curr);
            imported = false;  //by default we assume that item is not imported
            exempted = false; //by default we assume that the item is not exempt
            curr = st.nextToken(); //this is either part of the name or import toggle
            if(curr.equals("imported")){ //if it is the import toggle, set the toggle
               imported = true;
               curr = st.nextToken(); 
            }
            //current token is now the first part of the name
            name = curr;
            curr = st.nextToken();
            while(!curr.equals("at")){
               name = name + " " + curr;
               curr = st.nextToken();
            }
            //now that the name is properly set, we can check for a match within
            //the hashmap.  the name of the object is dictionary
            if(dictionary.containsKey(name)){
               exempted = true;
            }


            //curr aka current token should be "at" so we proceed to the next token
            curr = st.nextToken();
            //right now the curr string should be at the end, i.e we convert
            //the string to a double and that should give us the price
 
            price = Double.parseDouble(curr); //now the price variable set

            //we can now instantiate the salesObject
            salesObject item = new salesObject(quantity, imported, name, price, exempted);
           
            //we can print the item line here;
            item.printInfo();
            
            //we now need to add the current item price to the total sales price
            salesTotal.incfromsales(item); 
         } //end while
         salesTotal.taxandtotalprinter();
         salesTotal.clear();
      } //end for

//1 imported bottle of perfume at 27.99
//1 bottle of perfume at 18.99
//1 packet of headache pills at 9.75
//1 imported box of chocolates at 11.25

//27.99 * 0.15 = 4.1985
//18.99 * 0.10 = 1.8999
//11.25 * 0.05 = 0.5625
//             = 6.66 if you dont round first
//             = 6.64 if you round first

      //we can now print the sales tax and the total price
      return;
   } //end main 



}
