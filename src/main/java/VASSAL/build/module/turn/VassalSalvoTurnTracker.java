package VASSAL.build.module.turn;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = VassalSalvoTurnTrackerSerializer.class)
public class VassalSalvoTurnTracker extends TurnTracker {

	public VassalSalvoTurnTracker() {
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
