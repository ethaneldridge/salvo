package com.ethaneldridge.salvo.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.aspect.AboutScreenAspect;

import VASSAL.build.GameModule;
import VASSAL.tools.DataArchive;

public class SalvoAboutScreen {

	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setBackgroundImageFileName(String backgroundImageFileName) throws FileNotFoundException, IOException {
		this.backgroundImageFileName = backgroundImageFileName;
		DataArchive dataArchive = GameModule.getGameModule().getDataArchive();
		// This doesn't work
		// ClassLoader cl = this.getClass().getClassLoader();
		// URL url = cl.getResource(backgroundImageFileName);
		// logger.debug(url.toString());

		// This doesn't work
//		try (InputStream is = GameModule.getGameModule().getDataArchive().getInputStream("/"+backgroundImageFileName)) {
//			logger.debug(is.toString());
//		}
		
		// Copy the image to /Users/ethael/development/clojure/test-vassal-web/resources/public/images
		String relativeImage = String.format("%s/%s", IMAGES_DIR, backgroundImageFileName);
		InputStream in = dataArchive.getInputStream(relativeImage);
		
		String destinationLocation = "/Users/ethael/development/clojure/test-vassal-web/src/resources/public";
		String destinationImage = String.format("%s/%s", destinationLocation, relativeImage);
		FileOutputStream out = new FileOutputStream(destinationImage);
		
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len != -1) {
		    out.write(buffer, 0, len);
		    len = in.read(buffer);
		}
		
		in.close();
		out.close();
		
		URL url = dataArchive.getURL(relativeImage);
		System.out.print(relativeImage);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backgroundImageFileName == null) ? 0 : backgroundImageFileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalvoAboutScreen other = (SalvoAboutScreen) obj;
		if (backgroundImageFileName == null) {
			if (other.backgroundImageFileName != null)
				return false;
		} else if (!backgroundImageFileName.equals(other.backgroundImageFileName))
			return false;
		return true;
	}

	public SalvoAboutScreen() {
		
	}
	
	private String backgroundImageFileName;
	
	private static final Logger logger = LoggerFactory.getLogger(SalvoAboutScreen.class);
	private static final String IMAGES_DIR = "images";
}
