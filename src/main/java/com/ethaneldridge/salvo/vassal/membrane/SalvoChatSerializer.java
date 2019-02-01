package com.ethaneldridge.salvo.vassal.membrane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.ethaneldridge.salvo.data.SalvoChat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class SalvoChatSerializer extends StdSerializer<SalvoChat> {

	private static final long serialVersionUID = 1L;

	public SalvoChatSerializer() {
		this(null);
	}
	protected SalvoChatSerializer(Class<SalvoChat> t) {
		super(t);
	}

	@Override
	public void serialize(SalvoChat salvoChat, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("id", salvoChat.getId());
		jgen.writeStringField("timestamp", salvoChat
				.getTimestamp()
				.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		jgen.writeStringField("message", salvoChat.getMessage());
		jgen.writeEndObject();
	}

}
