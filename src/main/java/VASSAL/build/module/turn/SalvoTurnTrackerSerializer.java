package VASSAL.build.module.turn;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class SalvoTurnTrackerSerializer extends StdSerializer<SalvoTurnTracker> {

	private static final long serialVersionUID = 1L;

	public SalvoTurnTrackerSerializer() {
		this(null);
	}

	public SalvoTurnTrackerSerializer(Class<SalvoTurnTracker> t) {
		super(t);
	}

	@Override
	public void serialize(SalvoTurnTracker value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("turnDescription", value.getTurnDescription());
		jgen.writeEndObject();
	}
}
