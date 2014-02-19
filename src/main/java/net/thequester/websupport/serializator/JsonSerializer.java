package net.thequester.websupport.serializator;

/**
 * Created by Tomo.
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {

    private final ObjectMapper mapper;

    public JsonSerializer(){
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,true);
    }

    public String serialize(Object object) throws JsonProcessingException {

        return mapper.writeValueAsString(object);
    }

    public Object deserialize(String json,Class type) throws IOException {

        return mapper.readValue(json,type);
    }

}
