package com.myproject.aem.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Model(adaptables = SlingHttpServletRequest.class)
public class ReadJsonModel {

    @SlingObject
    SlingHttpServletRequest request;
    
    private String property;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadJsonModel.class);

	@PostConstruct
	protected void initModel() throws IOException {
		 String sURL = "https://mocki.io/v1/55f5245e-bcbb-4f14-8b63-5383942ff584"; //just a string

		    // Connect to the URL using java's native library
		    URL url = new URL(sURL);
		    URLConnection request = url.openConnection();
		    request.connect();

		    // Convert to a JSON object to print data
		    JsonParser jp = new JsonParser(); //from gson
		    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
		    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
		    property = rootobj.get("ListOfProperties").getAsJsonObject().get("Property").getAsJsonArray().get(0).getAsJsonObject().get("ServiceLocation").getAsJsonObject().get("PropertyType").getAsJsonObject().get("SuperiorPropertyType").getAsJsonObject().get("Description").toString();
		    LOGGER.debug("property Value is :{}", property);		
	}
	
	public String getProperty() {
		return property;
	}
}
