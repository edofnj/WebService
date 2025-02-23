package app.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import app.errors.WsException;
import app.model.xml.Ordini;

public class WsClient {
	private String baseUrl;
	private HttpClient client;

	public WsClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
	}

	/**
	 * Recupera tutti gli ordini dal web service.
	 */
	public Ordini getOrdini() throws Exception {
		URI uri = new URI(this.baseUrl);
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WsException("Errore HTTP: " + res.statusCode());

		String body = res.body();
		return XmlUtils.unmarshal(Ordini.class, body);
	}

	/**
	 * Recupera gli ordini di un utente specifico tramite ID.
	 * 
	 * @param utenteId L'ID dell'utente
	 */
	public Ordini getOrdini(String utenteId) throws Exception {
		URI uri = new URI(this.baseUrl + "?utente_id=" + utenteId);
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WsException("Errore HTTP: " + res.statusCode());

		String body = res.body();
		return XmlUtils.unmarshal(Ordini.class, body);
	}

	/**
	 * Recupera gli ordini di un utente specifico tramite nome.
	 * 
	 * @param nome Il nome dell'utente
	 */
	public Ordini getOrdiniByName(String nome) throws Exception {
		URI uri = new URI(this.baseUrl + "?nome=" + nome.replace(" ", "%20")); // Encoding spazi
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WsException("Errore HTTP: " + res.statusCode());

		String body = res.body();
		return XmlUtils.unmarshal(Ordini.class, body);
	}
}
