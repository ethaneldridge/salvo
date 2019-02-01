package com.ethaneldridge.salvo.dal.impl;

import java.util.ArrayList;
import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoChatDal;
import com.ethaneldridge.salvo.data.SalvoChat;

public class SalvoChatDalImpl implements SalvoChatDal {

	@Override
	public SalvoChat getById(String id) {
		return salvoChats.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<SalvoChat> searchAll() {
		return new ArrayList<SalvoChat> (salvoChats);
	}

	@Override
	public List<SalvoChat> searchBySizeAndOffset(int offset, int size) {
		// TODO Auto-generated method stub
		return new ArrayList<SalvoChat>(salvoChats.subList(offset, size));
	}

	@Override
	public void save(SalvoChat salvoChat) {
		if (salvoChats.contains(salvoChat)) {
			throw new RuntimeException("Duplicat salvoChat can't be added");
		} else {
			salvoChats.add(salvoChat);
		}
	}

	private static List<SalvoChat> salvoChats = new ArrayList<SalvoChat> ();
}
