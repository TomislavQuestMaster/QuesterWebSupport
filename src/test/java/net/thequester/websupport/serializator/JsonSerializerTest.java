package net.thequester.websupport.serializator;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.thequester.websupport.model.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Tomo.
 */
public class JsonSerializerTest {

    JsonSerializer serializator;

    @Before
    public void setUp(){
        serializator = new JsonSerializer();
    }

    @Test
    public void test() throws JsonProcessingException {

        Response response = new Response(1,"ok");
        String json = serializator.serialize(response);
        assertEquals("{\"bla\":1,\"reason\":\"ok\"}", json);
    }

    @Test
    public void deserializeTest() throws IOException {

        Response response = (Response) serializator.deserialize("{\"bla\":1,\"reason\":\"ok\"}", Response.class);
        assertEquals(1, response.getStatus());
        assertEquals("ok", response.getMessage());
    }

}
