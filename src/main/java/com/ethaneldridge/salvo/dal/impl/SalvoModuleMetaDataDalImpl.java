package com.ethaneldridge.salvo.dal.impl;

import com.ethaneldridge.salvo.dal.SalvoModuleMetaDataDal;
import com.ethaneldridge.salvo.data.SalvoModuleMetaData;

import VASSAL.build.GameModule;

public class SalvoModuleMetaDataDalImpl implements SalvoModuleMetaDataDal {

	@Override
	public SalvoModuleMetaData get() {
		SalvoModuleMetaData salvoModuleMetaData = new SalvoModuleMetaData();
		
		String moduleName = GameModule.getGameModule().getAttributeValueString(GameModule.MODULE_NAME);
		salvoModuleMetaData.setName(moduleName);
		return salvoModuleMetaData;
	}

}
