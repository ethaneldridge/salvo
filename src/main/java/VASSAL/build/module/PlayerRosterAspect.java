package VASSAL.build.module;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.vassal.membrane.SalvoBasicModule;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

import VASSAL.build.GameModule;
import VASSAL.build.module.PlayerRoster;
import VASSAL.build.module.PlayerRoster.PlayerInfo;

@Aspect
public class PlayerRosterAspect {

	// This static member must be declared before static block
	private static final Map<String, PlayerInfo> players = new HashMap<>();
	static {
		String playerId = "FIXMEPlayerId";
		String playerName = "FIXMEPlayerName";
		String side = "FIXMEPlayerSide";
		PlayerInfo playerA = new PlayerInfo(playerId, playerName, side);
		PlayerRosterAspect.players.put("PlayerA", null);
		PlayerRosterAspect.players.put("PlayerB", null);
	}
	@Around("execution(* VASSAL.build.module.PlayerRoster.getMySide(boolean))")
	public Object aroundPlayerRosterGetMySide(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundPlayerRosterGetMySide");

		Object[] args = joinPoint.getArgs();
		Object parameter = args[0];

		SalvoBasicModule salvoGameModule = (SalvoBasicModule)GameModule.getGameModule();
		String activePlayerName = salvoGameModule.getActivePlayerName();
		PlayerInfo activePlayerInfo = players.get(activePlayerName);
		PlayerRoster playerRoster = PlayerRoster.getInstance();
		String playerId = "FIXMEPlayerId";
		String playerName = "FIXMEPlayerName";
		String side = "FIXMEPlayerSide";
//		playerRoster.add(playerId, playerName, side);

		// Get the player from
		return joinPoint.proceed(args);
		
	}

	private static final Logger logger = LoggerFactory.getLogger(PlayerRosterAspect.class);

}
