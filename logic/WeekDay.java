package logic;

/**
 * Enum used to determine the index of a weekday
 *
 */

public enum WeekDay { SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(6);
	
	private int day;
	
	WeekDay(int day){
		this.day = day;
	}
	public int day(){
		return day;
	}
}
