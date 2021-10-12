package logic;


import java.util.Calendar;


/**
 * Date class contains the day, year, and month. We also use java Calendar as the base to adjust date.
 * Date class also deal with finding the first day of each month. It will be used for making the grid.
 * Date class also contains the date in String format. 
 * This class has 4 Constructor.
 * 
 *
 */


public class Date {
    private Calendar Cal = Calendar.getInstance();
    private int year;
    private int month;
    private int day;
    static int YEAR_INDEX = 1;
    static int MONTH_INDEX = 2;
    static int DAY_INDEX = 5;


    
    /**
     * Default Constructor. Setting current Day, Month and Year.
     */
    public Date(){
    	this.year = Cal.get(YEAR_INDEX);
    	this.month = Cal.get(MONTH_INDEX) + 1;
    	this.day = Cal.get(DAY_INDEX);
    	
    }
    
    /**
     * Constructor: Takes an object and set current date as the Object date. 
     * @param date
     */
    public Date(Date date){
    	this.year = date.getYear();
    	this.month = date.getMonth();
    	this.day = date.getDay();
    	
    	this.Cal.set(Calendar.YEAR, date.getYear());
    	this.Cal.set(Calendar.MONTH, date.getMonth());
    	this.Cal.set(Calendar.DAY_OF_MONTH, date.getDay());
    }
    
    /**
     * Constructor: Takes a String of date, and convert it into integer, and then set the current date.
     * @param date
     */
    public Date(String date){
    	int year = getDateFromString(date,"year");
    	int month = getDateFromString(date,"month");
    	int day = getDateFromString(date,"day");
    	
    	this.year = year;
    	this.month = month;
    	this.day = day;
    	
    	this.Cal.set(Calendar.YEAR, year);
    	this.Cal.set(Calendar.MONTH, month);
    	this.Cal.set(Calendar.DAY_OF_MONTH, day);
    }
    
    /**
     * Constructor: Takes 3 integers, day, month, year. And set them to current date. 
     * @param year
     * @param month
     * @param day
     */
    public Date (int year, int month, int day){
    	this.year = year;
    	this.month = month;
    	this.day = day;
    	
    	this.Cal.set(Calendar.YEAR, year);
    	this.Cal.set(Calendar.MONTH, month);
    	this.Cal.set(Calendar.DAY_OF_MONTH, day);
    }
    
    /**
     * Setters.
     * The next three methods: Set day, month, year individually.  
     * @param aYear
     */
    public void setYear(int aYear){
        this.year = aYear;
        this.Cal.set(Calendar.YEAR, aYear);
    }
    
    public void setMonth(int aMonth){
        this.month = aMonth;
        this.Cal.set(Calendar.MONTH, aMonth-1);
    }
    
    public void setDay(int aDay){
        this.day = aDay;
        this.Cal.set(Calendar.DAY_OF_MONTH, aDay);
    }
    
    /**
     * Getters.
     * Methods return an integer.
     * @return
     */
    public int getYear(){
        return this.year;
    }
     
    public int getMonth(){
        return this.month;
    }
     
    public int getDay(){
        return this.day;
    }
    
    /**
     * Overridding.
     * Method that doesnt take any parameters will return the date in Stirng.
     * Method that takes three integers, day, month, year will return a String date.
     * @return
     */
    public String getDateInString(){
    	return getDateInString(year, month, day);
    }
    
    public String getDateInString(int year, int month, int day){
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        String result = "null";
        if(month < 10){
            monthStr = "0" + monthStr;
        }
        if(day < 10){
            dayStr = "0" + dayStr;
        }
        result = yearStr + "/" + monthStr + "/" + dayStr;
        return result;
    }
    
    /**
     * Takes a String of date and a type of day or month or year.
     * Will return and integer of day or month or year depends on the type.
     * @param date
     * @param type
     * @return
     */
    public int getDateFromString(String date, String type){
    	String[] line = date.split("/");
    	int result = 0;
    	if(type.equals("year")){
    		result = Integer.parseInt(line[0]);
    	}else if (type.equals("month")){
    		result = Integer.parseInt(line[1]);
    	}else if(type.equals("day")){
    		result = Integer.parseInt(line[2]);
    	}
    	
    	return result;
    }
    
    /**
     * This method will temporarily set the day to the first day of current month, and then get the week index of that day.
     * This method will return a integer represents the week index which determine the first day on the grid.
     * @return
     */
    public int getFirstDay(){
        Cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = Cal.get(Calendar.DAY_OF_WEEK);      
        return firstDay;
    }
    
    /**
     * This method will return a boolean.
     * This method will take an object, and compare with another object to see if they match.
     * @param toCompare
     * @return
     */
    public boolean compareTo(Date toCompare){
    	if(toCompare.getDateInString().equals(getDateInString())){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    /**
     * Clone method. 
     */
    public Date clone(){
    	Date dateClone = new Date(getYear(), getMonth(), getDay());
        return dateClone;
    }
}