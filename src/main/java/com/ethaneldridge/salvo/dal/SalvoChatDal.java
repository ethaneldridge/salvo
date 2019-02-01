package com.ethaneldridge.salvo.dal;

import java.util.List;

import com.ethaneldridge.salvo.data.SalvoChat;

public interface SalvoChatDal {

	public SalvoChat getById(String id);
	public List<SalvoChat> searchAll();
	public List<SalvoChat> searchBySizeAndOffset(int size, int offset);

	public void save(SalvoChat salvoChat);
}
