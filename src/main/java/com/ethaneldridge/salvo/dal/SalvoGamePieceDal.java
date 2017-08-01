package com.ethaneldridge.salvo.dal;

import java.awt.Point;

import com.ethaneldridge.salvo.data.SalvoGamePiece;

import VASSAL.counters.GamePiece;

public interface SalvoGamePieceDal {

	SalvoGamePiece getSalvoGamePieceFromVassalGamePiece(GamePiece gamePiece, Point offset);
}
