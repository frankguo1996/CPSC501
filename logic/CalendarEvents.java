package logic;
/**
 * Change log:
 * 	removed convert time
 * 2017-03-28
 */

/** 
 * Used to store and get details of events created
 *  
 * Last modified: 2017-03-13 12:43
 */
  
 public class CalendarEvents {
    private String eName;
    private Time eTime;
    private String eLoc;
    
    /**
     * User should not be able to create an event without a name
     * @param nameInput
     */
    public CalendarEvents(String nameInput) {
        eName = nameInput;
    }
    public CalendarEvents(String nameInput, String timeInput, String locInput) {
        eName = nameInput;
        eTime = new Time(timeInput);
        eLoc = locInput;
    }
     
    /**Method set eTime
     * @param timeInput
     */
    public void setTime(String timeInput){

        eTime = new Time(timeInput);
    }
      
    /**Method set eLoc
     * @param locInput
     */
    public void setLocation(String locInput){
        eLoc = locInput;
    }
      
    /** Method returns eName
     * @Return eName
     */
    public String getName(){
        return eName;
    }
      
    /** Method returns eTime
     * @Return eTime
     */
    public String getTime(){        
        return eTime.toString();
    }
    
    /**
     * Method returns the hour
     * @return hour
     */
    public int getHour(){
    	return eTime.getHour();
    }
      
    /** Method returns eLoc
     * @return eLoc
     */
    public String getLocation(){
        return eLoc;
    }
     
    // Prints all info corresponding to the event
    public void printSummary(){
         System.out.println("---------------------------------");
         System.out.printf("\nEvent Name: %s", eName);
         System.out.printf("\nEvent Time: %s", eTime);
         System.out.printf("\nEvent Location: %s", eLoc);
    }
}