package VASSAL.build.module.turn;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SalvoTurnTrackerSerializer.class)
public class SalvoTurnTracker extends TurnTracker {

	public SalvoTurnTracker() {
		super();
	}
	
	@Override
	protected String getTurnString() {
		String turnString = super.getTurnString();

		this.turnDescription = turnString;
		
		return turnString;
	}
	
//	@Override
//	protected void next() {
//		super.next();
//		this.turnDescription = super.getTurnString();
//	}

	public String getTurnDescription() {
		return this.turnDescription;
	}
	
	private String turnDescription;
}
