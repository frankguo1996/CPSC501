package logic;
public class Time {
    private int cHour;
    private int cMin;
 
 
    /**
    * Constructors:
    * When class takes an 2 integar as an argument, store two integar as hour and min
    * When class takes an 1 string(Which will be HH:MM) as an argument, seperate them by colone(:) and convert them as integar as hour and min
    * When class takes an time as  an argument, copy the parameter.
    */
    public Time(int aHour, int aMin){
        this.cHour = aHour;
        this.cMin = aMin;
    }
 
    public Time(String HHMM){
        String[] parts = HHMM.split(":");
        String HH = parts[0];
        String MM = parts[1];
        cHour = Integer.parseInt(HH);
        cMin = Integer.parseInt(MM);
 
    }
 
    public Time(Time aTime){
        this(aTime.cHour, aTime.cMin);
    }

	/**
	 * sets the Hour 
	 * only works if Hours is Valid
	 * @param hour
	 */
    public void setHour(int hour){
        if(hourValid(hour)){
            cHour = hour;
        }   
    }
	
	/**
	 * sets the Minute
	 * only works if Minute is Valid
	 * @param min
	 */
    public void setMin(int min){
        if(minValid(min)){
            cMin = min;
        }   
    }
	
 	/**
	 * Checking if hour you want to set is between 0 and 23
	 * @param hourInput
	 * @return whether input is valid(true) or not(false)
	 */
    public boolean hourValid(int hourInput){
        return( 0 <= hourInput && hourInput <= 23);
     
    }

 	/**
	 * Checking if minute you want to set is between 0 and 59
	 * @param minInput
	 * @return whether input is valid(true) or not(false)
	 */
    public boolean minValid(int minInput){
        return( 0 <= minInput && minInput <= 59);     
    }
	
    /** 
     * This accessor methods returns the hour in the Time.
     * @return the hour at the time.
     */
    public int getHour(){
        return cHour;       
    }

    /** 
     * This accessor methods returns the minute in the Time.
     * @return the minute at the time.
     */
    public int getMin(){
        return cMin;        
    }

    /**
     * This accessor method returns a string representation of this time
     * in the format (HH:MM, or HH:0M).
     * @return a string representation of time time.
     */
    public String toString(){
    	String result = "";
    	if(cMin < 10){
    		result = String.format("%d:0%d", cHour, cMin); 
    	}else{
    		result = cHour + ":" + cMin;
    	}
        return(result);
    }
}