package com.ethaneldridge.salvo.vassal.membrane;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import VASSAL.build.GameModule;
import VASSAL.launch.Player;
import VASSAL.tools.DataArchive;

public class GameWrapper extends Player {

	private static int port = 3030;
	public static void main(String[] args) {
		try {
			logger.debug("GameWrapper");

			// Get the options we need
			Options options = new Options();

			options.addOption("player", true, "Unique identifier for player.  Must have corresponding pref file");
			options.addOption("port", true, "specifies the port to use for TCP/IP server");
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse( options, args, true);

			if (cmd.hasOption("player")) {
				activePlayerName = cmd.getOptionValue("player");
			}
			if (cmd.hasOption("port")) {
				port = Integer.valueOf(cmd.getOptionValue("port"));
			}
			// Pass the remaining arguments to vassal
			String[] argsForGame = cmd.getArgs();

			new GameWrapper(argsForGame);
		} catch (Exception e) {
			// FIXME: proper exception handling
			e.printStackTrace();
		}
	}

	private GameWrapper(String[] args) throws Exception {
		super(args);
		vassalEngine.connect(port);
	}

	@Override
	protected GameModule createModule(DataArchive archive) {
		SalvoBasicModule salvoBasicModule = new SalvoBasicModule(archive);
		salvoBasicModule.setActivePlayerName(activePlayerName);
		return salvoBasicModule;
	}

	@Override
	protected void launch() throws IOException {
		super.launch();

		vassalEngine.launch();
	}

	private static final Logger logger = LoggerFactory.getLogger(GameWrapper.class);
	private static String activePlayerName;
	private VassalEngine vassalEngine = VassalEngine.theVassalEngine();
}
