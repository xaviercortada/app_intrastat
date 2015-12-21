package cat.alkaid.projects.intrastat.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class MyUserSerializer extends JsonSerializer<Person>{

	@Override
	public void serialize(Person item, JsonGenerator gen, SerializerProvider prov)
			throws IOException, JsonProcessingException {
		
		gen.writeString(item.getFirstName());
		
	}
	
}