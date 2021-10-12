package logic;
 
import java.util.Iterator;
import java.util.Set;

/**
 * Change log:
 * 	Added monthGrid as instance variable
 * 	Used makeMonth in constructor
 * 	Created getGrid 
 *
 */

/* Month class is responsible for providing the grid structure of each month for the default calendar view
 * along with determining how many days in each month there are and checking validity
 * Changelog (April 4 at 7:24PM): added documentation and fixed indexing 
 * @author Parker
 */
public class Month {
    private Date date;
    private String monthGrid[][];
     
    public Month(){
    	
    }
    public Month(Date date){
    	this.date = date;
    	this.monthGrid = makeGrid();
    }

    /*
     * Makes a 2d string array which provides the grid format needed to display each month
     * @return grid which is the proper 2d array for each month
     */
    public String[][] makeGrid() { 
        int col = 7, row = 7;
        String[][] grid = new String[col][row];
 
            int totalDays = numOfDay();
            int weekIdx = date.getFirstDay() - 1;
            int day = 1;           
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (r == 0) {
                        if (c < weekIdx) {
                            grid[r][c] = "";
   
                        } else if (c >= weekIdx) {
                            grid[r][c] = Integer.toString(day);
                            day++;
                        }
                    } else {
                        if (day <= totalDays) {
                            grid[r][c] = Integer.toString(day);
                            day++;
                        } else {
                            grid[r][c] = "";
                        }
                    }
                }
            }
        return grid;
    }  
    /* 
     * @return result true if the total number of days is valid within the month specified
     */
    public boolean isValid(int day){
        boolean result = false;
        if (numOfDay() == 28 || numOfDay() == 29 || numOfDay() == 30 || numOfDay() == 31){
            result = true;
        }        
        return result;
            
    }
    /* calculates the total number of days as long as the year is also within the proper range
     * @return totalDays
     */
    public int numOfDay(){ 
        int totalDays = 0;
        if(date.getYear() >= 1970 && date.getYear() <= 2099){
            if(date.getMonth() >= 1 && date.getMonth() <= 12){
                if(date.getMonth() == 1 || date.getMonth() == 3 || date.getMonth() == 5 || date.getMonth() == 7 || date.getMonth() == 8 || date.getMonth() == 10 || date.getMonth() == 12){
                    totalDays = 31;
                }else if(date.getMonth() == 4 || date.getMonth() == 6 || date.getMonth() == 9 || date.getMonth() == 11){
                    totalDays = 30;
                }else if(date.getMonth() == 2){
                    if((date.getYear() % 400 == 0) || ((date.getYear() % 4 == 0) && (date.getYear() % 100 != 0))){
                        totalDays = 29;
                    }else{
                        totalDays = 28;
                    }
                }
            }
        }
         return totalDays;
    }
    
    /**
	 * Adds the "*" to dates that has events
	 * @param year
	 * @param month
	 * @param day
	 */
	public void labelCells(EventCollection event){
		Set dates = event.getAllDates();
		Iterator iter = dates.iterator();
		
		while(iter.hasNext()){
			Date dateTemp = (Date)iter.next();
			if(date.getMonth() == dateTemp.getMonth() && date.getYear() == dateTemp.getYear()){
				for(int row = 0; row < monthGrid.length; row ++){
					for(int col = 0; col < monthGrid[0].length; col++){
						if(monthGrid[row][col].equals(Integer.toString(dateTemp.getDay()))){
							monthGrid[row][col] = monthGrid[row][col] + "*";
						}
					}
				}
			}
		}
		
		
	}
    /* sets the month number as an integer
     * @param month
     */
    public void setMonthNum(int month) {
        date.setMonth(month);
    }
    /* sets the year as an integer
     * @param year
     */
    public void setYear(int year){
        date.setYear(year);
    }
     
    /* gets the month number as an integer
     * @return this.monthNum
     */
    public int getMonthNum(){
        return this.date.getMonth();
    }
    /* gets the year as an integer
     * @return this.year
     */
    public int getYear(){
        return this.date.getYear();
    }
    public String[][] getGrid(){
    	return monthGrid;
    }
     
}