/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krn_cs3050_final;

import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public abstract class Stock_Sorter 
{
    public static ArrayList<Prospect> sortStocks(int numDays, int transactions, ArrayList<Integer> prices)
    {
        //int numDays = 6;//NUMBER OF DAYS SHOULD BE GIVEN BY USER
        //int transactions = 1;//NUMBER OF TIMES STOCKS CAN BE BOUGHT AND SOLD SHOULD BE GIVEN BY USER
        /*ArrayList<Integer> prices = new ArrayList<Integer>()//MY INPUT FILE SHOULD BE GIVEN BY USER
        {{
            add(1);
            add(3);
            add(4);
            add(6);
            add(2);
            add(10);
        }};*/
        Boolean increasing = false;
        ArrayList<Prospect> growthPeriods = new ArrayList<>();
       
        //Create complete list of periods of growth
       for(int count = 0; count < numDays; count++)//dont create a condition for start but one for end to make sure you dont go out of the array list
       {
           //if we are at the last day we have a price for
           if(count+1 == numDays)
           {
               if(increasing == true)
               {//==========================================
                   increasing = false;
                   Prospect temp = growthPeriods.get(growthPeriods.size()-1);
                   temp.setEnd(count+1);
                   //long confusing line to set the increase of this growth period by subtracting the price of the start date from the price at the end date
                   temp.setIncrease((prices.get(temp.getEnd()-1) - prices.get(temp.getStart()-1)));
               }
           }
           //start of growth period
           else if(prices.get(count) < prices.get(count+1) && increasing == false)
           {
               increasing = true;
               growthPeriods.add(new Prospect(count+1));//count plus one because the array has day 1 in the zero position
           }
           //end of growth period
           else if(prices.get(count) > prices.get(count+1) && increasing == true)
           {
               increasing = false;
               Prospect temp = growthPeriods.get(growthPeriods.size()-1);
               temp.setEnd(count+1);
               //long confusing line to set the increase of this growth period by subtracting the price of the start date from the price at the end date
               temp.setIncrease((prices.get(temp.getEnd()-1) - prices.get(temp.getStart()-1)));
           }
       }
       
       //Test print of origonal list
       for(int i = 0; i < growthPeriods.size(); i++)
       {
           System.out.println(" Start date: " +growthPeriods.get(i).getStart() + " End date: " + growthPeriods.get(i).getEnd() + " Growth: " + growthPeriods.get(i).getIncrease());
       }
       
       //I now have my list of all possible increase periods I must now decrease the values
       //go through the array list of growth periods and combine the days where the price from the height of one ends with
       //the smallest difference to the beginning of the next one (the smallest drop)
       //need to make a check for end (could end on a low climb which would just want to be deleted---------------XXXXXXX havent done
       
       //get number of growth periods down to r(transactions)
       while(growthPeriods.size() > transactions)
       {
           Prospect firstToCombine = growthPeriods.get(0);
           Prospect secondToCombine = growthPeriods.get(0);
           int lowestDifference =  -1;
           int lowestIncrease = -1;
           Prospect leastValuable = growthPeriods.get(0);
           
           for(int count = 0; count < growthPeriods.size()-1; count++)
           {
               int currentEndPrice = prices.get(growthPeriods.get(count).getEnd()-1);
               int nextStartPrice = prices.get(growthPeriods.get(count).getStart()-1);
               int nextEndPrice = prices.get(growthPeriods.get(count).getEnd()-1);
               
               if(currentEndPrice <= nextEndPrice)//make sure im not losing profit of of combining and not selling sooner
               {
                   //find the smallest profit loss if we combine two growthPeriods
                   if(lowestDifference == -1 || currentEndPrice - nextStartPrice < lowestDifference)
                   {
                       firstToCombine = growthPeriods.get(count);
                       secondToCombine = growthPeriods.get(count+1);
                       lowestDifference = currentEndPrice - nextStartPrice;
                   }
               }
           }
           
           //check to see if it would be cheaper to just remove a growthPeriod all together
           for(int count = 0; count < growthPeriods.size(); count++)
           {
               if(lowestIncrease == -1)
               {
                   lowestIncrease = growthPeriods.get(count).getIncrease();
                   leastValuable = growthPeriods.get(count);
               }
               else if(growthPeriods.get(count).getIncrease() < leastValuable.getIncrease())
               {
                   lowestIncrease = growthPeriods.get(count).getIncrease();
                   leastValuable = growthPeriods.get(count);
               }
           }
           
           //check if it is better to remove or combine growthperiods
           if(lowestIncrease == -1)
           {
               System.out.println("Problem with lowestIncrease... should not be -1 (arraylist is empty)");
           }
           //if we should remove
           else if(lowestDifference == -1 || lowestIncrease < lowestDifference)
           {
               growthPeriods.remove(leastValuable);
           }
           //if we should combine
           else
           {   
               //update the end and new profit gained in current growthPeriod
               firstToCombine.setEnd(secondToCombine.getEnd());
               firstToCombine.setIncrease(prices.get(secondToCombine.getEnd()-1) - prices.get(firstToCombine.getStart()-1));
               
               //remove growthPeriod that was merged
               growthPeriods.remove(secondToCombine);

           }
       }
       
       //Test print of new
       System.out.println("\n\n After Removing\n");
       for(int i = 0; i < growthPeriods.size(); i++)
       {
           System.out.println(" Start date: " +growthPeriods.get(i).getStart() + " End date: " + growthPeriods.get(i).getEnd() + " Growth: " + growthPeriods.get(i).getIncrease());
       }
       return(growthPeriods);
    }
}
