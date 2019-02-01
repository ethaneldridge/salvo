package com.ethaneldridge.salvo.vassal.membrane;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javax.swing.JToolBar;

import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.data.SalvoToolbar;

import VASSAL.counters.GamePiece;

public interface VassalRepository {

	public List<SalvoMap> searchAllSalvoMaps();
	public SalvoMap getSalvoMapById(String id);

	public SalvoGamePiece getSalvoGamePieceFromVassalGamePiece(GamePiece gamePiece, Point offset);
	
	public Map<JToolBar, SalvoToolbar> searchAllSalvoToolbars();
	public SalvoToolbar getSalvoToolbarByJToolBar(JToolBar jToolBar);
	public void save(SalvoToolbar salvoToolbar);

	public void loadCache();
	public void validate() throws Exception;
}
