package logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 * A single getter/setter for events in the HashMap
 * TODO: Reminders
 * @author Kevin Lee
 * 2017-04-03 1:09 PM
 * Change Log:
 * 		Added clone constructor, with getters and setters 
 */

public class EventCollection {
	private HashMap <Date, ArrayList <CalendarEvents>> events;
	
	public EventCollection(){		
		events = new HashMap<Date, ArrayList <CalendarEvents>>();
	}
	
	public EventCollection(EventCollection toCopy){
		events = new HashMap<Date, ArrayList <CalendarEvents>>();
		setMap(toCopy.getMap());
	}
	
	/**
	 * Tests if hashmap already contains a key
	 * @param date
	 * @return boolean if the key exists
	 */
	public boolean containsKey(Date date){
		boolean contains = false;
		Set key = getAllDates();
		Iterator iter = key.iterator();
		while(iter.hasNext()){
			Date temp = (Date) iter.next();
			contains = temp.compareTo(date);
		}
		return contains;
	}
	
	/**
	 * Setter for new events
	 * @param key
	 * @param value
	 */
	public void setEvent(Date key, CalendarEvents value){
		ArrayList<CalendarEvents> temp = new ArrayList<CalendarEvents>();
		Date tempKey = key;
		if(containsKey(key)){
			tempKey = getKey(key);
			temp = getEvents(tempKey);
			
		}
		temp.add(value);
		events.put(tempKey, temp);		
	}
	
	/**
	 * Setter for new events
	 * @param key
	 * @param eventList
	 */
	public void setEvent(Date key, ArrayList<CalendarEvents> eventList){
		for(CalendarEvents value: eventList){
			setEvent(key, value);	
		}
	}
	
	/**
	 * Setter for the events HashMap
	 * Used to prevent privacy leaks
	 * @param toSet
	 */
	public void setMap(HashMap toSet){
		events = toSet;
	}
	
	/**
	 * Getter for list of events when specified a date
	 * @param key
	 * @return arraylist of events
	 */
	public ArrayList<CalendarEvents> getEvents(Date key){
		
		return events.get(key);		
	}
	/**
	 * Since identical Dates are technically different objects, use this method to get the right object 
	 * @param toGet
	 * @return
	 */
	public Date getKey(Date toGet){
		Date toReturn = new Date("2017/01/01");		
		Set key = getAllDates();
		Iterator iter = key.iterator();
		while(iter.hasNext()){
			Date temp = (Date) iter.next();
			if(temp.compareTo(toGet)){
				toReturn = temp;
			}
		}
		return toReturn;
	}
	
	/**
	 * Checks if there already exists an event with the identical date and time
	 * @param date
	 * @param event
	 * @return exists boolean
	 */
	public boolean checkEvent(Date date, CalendarEvents event){
		boolean exists = false;
		if(containsKey(date)){
			Date key = getKey(date);
			ArrayList<CalendarEvents> list = getEvents(key);
			for(CalendarEvents temp : list){
				if(temp.getHour() == event.getHour()){
					exists = true;
				}
			}
		}
		return exists;
	}
	
	/**
	 * Get a Set of all the Dates that contains events
	 * @return Set of Dates
	 */
	public Set getAllDates(){
		return events.keySet();
	}
	
	/**
	 * Getter for the HashMap instance variable
	 * Used to prevent privacy leaks
	 * @return
	 */
	public HashMap getMap(){
		return events;
	}	
}