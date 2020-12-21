package cat.alkaid.projects.intrastat.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.Date;
import com.fasterxml.jackson.databind.JsonSerializer;

public class JsonStdDateSerializer extends JsonSerializer<Date>
{
    public void serialize(final Date value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        final DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        final String formattedDate = myformat.format(value);
        jgen.writeString(formattedDate);
    }
}
