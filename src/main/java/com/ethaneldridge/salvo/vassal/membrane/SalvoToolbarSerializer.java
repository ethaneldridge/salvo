package com.ethaneldridge.salvo.vassal.membrane;

import java.io.IOException;

import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.data.SalvoToolbarItem;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class SalvoToolbarSerializer extends StdSerializer<SalvoToolbar> {

	private static final long serialVersionUID = 1L;

	public SalvoToolbarSerializer() {
		this(null);
	}
	protected SalvoToolbarSerializer(Class<SalvoToolbar> t) {
		super(t);
	}

	@Override
	public void serialize(SalvoToolbar salvoToolbar, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeNumberField("id", salvoToolbar.getId());
		// FIXME - need to selectively write the Items b/c they have a link to the parent salvoToolbar
		jgen.writeArrayFieldStart("salvoToolbarItems");
		for(SalvoToolbarItem salvoToolbarItem : salvoToolbar.getSalvoToolbarItems()) {
			jgen.writeStartObject();
			jgen.writeStringField("description", salvoToolbarItem.getDescription());
			jgen.writeStringField("buttonText", salvoToolbarItem.getButtonText());
			jgen.writeStringField("tooltip", salvoToolbarItem.getTooltip());
			jgen.writeStringField("buttonIcon", salvoToolbarItem.getButtonIcon());
			jgen.writeStringField("buttonHotkey", salvoToolbarItem.getButtonHotkey());
			for (String menuItem : salvoToolbarItem.getMenuItems()) {
				jgen.writeStartObject();
				jgen.writeStringField("menuItem", menuItem);
				
				jgen.writeEndObject();
			}
//			jgen.writeObject(salvoToolbarItem);
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeEndObject();
	}

}
