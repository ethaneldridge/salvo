package com.ethaneldridge.salvo.vassal.membrane;

import com.ethaneldridge.salvo.data.SalvoPlayer;

import VASSAL.build.GameModule;
import VASSAL.configure.Configurer;
import VASSAL.configure.StringConfigurer;
import VASSAL.configure.TextConfigurer;
import VASSAL.i18n.Resources;
import VASSAL.launch.BasicModule;
import VASSAL.preferences.Prefs;
import VASSAL.tools.DataArchive;

public class SalvoBasicModule extends BasicModule {

	public SalvoBasicModule(DataArchive archive) {
		super(archive);
	}

	@Override
	public Prefs getPrefs() {
		// Could lay-down a file here, or let a default file be loaded and override values...
		if (preferences == null) {
			// If specified, add the activePlayerName to the preferences file
			// Otherwise, load the default preferences file
			String preferencesName = gameName + ((activePlayerName != null) ? activePlayerName : "");
			setPrefs(new Prefs(Prefs.getGlobalPrefs().getEditor(), preferencesName));
		}
		return preferences;
	}
	
	public void changeIdentityPreferences(SalvoPlayer salvoPlayer) {
		// FIXME: Are there memory leaks taking this approach?
		// This is wonky but order is important.  These are effectively global, which is nasty:
		activePlayerName = salvoPlayer.getPlayerName();
		preferences = null;
		preferences = getPrefs();
		
		initIdentityPreferences();
		
//		Configurer fullName = preferences.getOption(GameModule.REAL_NAME);
//		fullName.setValue(preferences.getValue(Resources.getString("Prefs.name_label")));
//		
//		Configurer profile = preferences.getOption(GameModule.PERSONAL_INFO);
//		profile.setValue(preferences.getValue(Resources.getString("Prefs.personal_info")));
//		
//		Configurer user = preferences.getOption(GameModule.SECRET_NAME);
//		user.setValue(preferences.getValue(Resources.getString("Prefs.password_label")));
	}

	public String getActivePlayerName() {
		return activePlayerName;
	}

	public void setActivePlayerName(String activePlayerName) {
		this.activePlayerName = activePlayerName;
	}

	private String activePlayerName;

}
