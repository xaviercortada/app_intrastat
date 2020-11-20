package cat.alkaid.projects.intrastat.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import java.util.Date;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonStdDateDeserializer extends JsonDeserializer<Date>
{
    public Date deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException, JsonProcessingException {
        final DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        final String value = jp.getText();
        Date date = null;
        try {
            date = myformat.parse(value);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
