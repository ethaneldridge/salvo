package com.ethaneldridge.salvo.vassal.membrane.command;

public interface Command {
	public Object apply(String request) throws Exception;
}
