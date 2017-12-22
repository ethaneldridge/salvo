package VASSAL.build.module.turn;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class VassalSalvoTurnTrackerSerializer extends StdSerializer<VassalSalvoTurnTracker> {

	private static final long serialVersionUID = 1L;

	public VassalSalvoTurnTrackerSerializer() {
		this(null);
	}

	public VassalSalvoTurnTrackerSerializer(Class<VassalSalvoTurnTracker> t) {
		super(t);
	}

	@Override
	public void serialize(VassalSalvoTurnTracker value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("turnDescription", value.getTurnDescription());
		jgen.writeEndObject();
	}
}
