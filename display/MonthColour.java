package display;

import java.awt.Color;
/**
 * Enum used to determine colour that corresponds to each month
 * @author Kevin
 */

public enum MonthColour { JANUARAY(Color.BLUE), FEBURARY(Color.PINK), MARCH(Color.ORANGE), APRIL(Color.DARK_GRAY), MAY(Color.LIGHT_GRAY), JUNE(Color.CYAN), JULY(Color.YELLOW), AUGUST(Color.MAGENTA), SEPTEMBER(Color.GREEN), OCTOBER(Color.RED), NOVEMBER(Color.BLACK), DECEMBER(Color.WHITE);
	private Color colour;
	
	MonthColour(Color col){
		this.colour = col;
	}
	public Color getColour(){
		return colour;
	}
}
