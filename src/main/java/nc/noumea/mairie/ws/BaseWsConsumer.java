package nc.noumea.mairie.ws;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import flexjson.JSONDeserializer;

public abstract class BaseWsConsumer {


	public ClientResponse createAndFireGetRequest(Map<String, String> parameters, String url) {
		return createAndFireRequest(parameters, url, false, null);
	}
	
	public ClientResponse createAndFireRequest(Map<String, String> parameters, String url, boolean isPost, String postContent) {

		Client client = Client.create();
		WebResource webResource = client.resource(url);
		
		for (String key : parameters.keySet()) {
			webResource = webResource.queryParam(key, parameters.get(key));
		}

		ClientResponse response = null;

		try {
			if (isPost)
				if (postContent != null)
					response = webResource.accept(MediaType.APPLICATION_JSON_VALUE).post(ClientResponse.class);
				else
					response = webResource.accept(MediaType.APPLICATION_JSON_VALUE).post(ClientResponse.class, postContent);
			else
				response = webResource.accept(MediaType.APPLICATION_JSON_VALUE).get(ClientResponse.class);
		} catch (ClientHandlerException ex) {
			throw new WSConsumerException(String.format("An error occured when querying '%s'.", url), ex);
		}

		return response;
	}
	
	public <T> T readResponse(Class<T> targetClass, ClientResponse response, String url) {

		T result = null;

		try {

			result = targetClass.newInstance();

		} catch (Exception ex) {
			throw new WSConsumerException("An error occured when instantiating return type when deserializing JSON from WS request.", ex);
		}

		if (response.getStatus() == HttpStatus.NO_CONTENT.value()) {
			return null;
		}

		if (response.getStatus() != HttpStatus.OK.value()) {
			throw new WSConsumerException(String.format("An error occured when querying '%s'. Return code is : %s, content is %s", 
					url, response.getStatus(), response.getEntity(String.class)));
		}

		String output = response.getEntity(String.class);

		result = new JSONDeserializer<T>().deserializeInto(output, result);

		return result;
	}
}