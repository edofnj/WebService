package MenegazziCotroneo.Ordini.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import MenegazziCotroneo.Ordini.errors.WsException;
import MenegazziCotroneo.Ordini.model.Ordine;
import MenegazziCotroneo.Ordini.model.Ordini;
import MenegazziCotroneo.Ordini.model.Utente;
import MenegazziCotroneo.Ordini.model.Utenti;

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

    /**
     * Crea un nuovo ordine.
     * 
     * @param utenteId L'ID dell'utente che effettua l'ordine.
     * @param prodotto Il nome del prodotto.
     * @param quantita La quantità del prodotto.
     * @param prezzo Il prezzo del prodotto.
     * @return La risposta del web service.
     */
    public String createOrder(String utenteId, String prodotto, int quantita, double prezzo) throws Exception {
        URI uri = new URI(this.baseUrl);
        String xmlRequest = String.format(
            "<ordine><utente_id>%s</utente_id><prodotto>%s</prodotto><quantita>%d</quantita><prezzo>%f</prezzo></ordine>",
            utenteId, prodotto, quantita, prezzo
        );
        
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/xml")
            .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
            .build();
        
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }

    /**
     * Aggiorna un ordine esistente.
     * 
     * @param id L'ID dell'ordine da aggiornare.
     * @param prodotto Il nuovo nome del prodotto (opzionale).
     * @param quantita La nuova quantità (opzionale).
     * @param prezzo Il nuovo prezzo (opzionale).
     * @return La risposta del web service.
     */
    public String updateOrder(int id, String prodotto, Integer quantita, Double prezzo) throws Exception {
        URI uri = new URI(this.baseUrl);
        StringBuilder xmlRequest = new StringBuilder("<ordine><id>" + id + "</id>");
        
        if (prodotto != null) {
            xmlRequest.append("<prodotto>").append(prodotto).append("</prodotto>");
        }
        
        if (quantita != null) {
            xmlRequest.append("<quantita>").append(quantita).append("</quantita>");
        }
        
        if (prezzo != null) {
            xmlRequest.append("<prezzo>").append(prezzo).append("</prezzo>");
        }
        
        xmlRequest.append("</ordine>");
        
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/xml")
            .PUT(HttpRequest.BodyPublishers.ofString(xmlRequest.toString()))
            .build();
        
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }

    /**
     * Elimina un ordine.
     * 
     * @param id L'ID dell'ordine da eliminare.
     * @return La risposta del web service.
     */
    public String deleteOrder(int id) throws Exception {
        URI uri = new URI(this.baseUrl + "?id=" + id);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .DELETE()
            .build();

        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }
    
    /**
     * Recupera tutti gli utenti dal web service.
     */
    public Utenti getUtenti() throws Exception {
        URI uri = new URI(this.baseUrl + "/utenti.php");
        HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        String body = res.body();
        return XmlUtils.unmarshal(Utenti.class, body);
    }

    /**
     * Recupera un utente specifico tramite ID.
     * 
     * @param utenteId L'ID dell'utente
     */
    public Utente getUtente(String utenteId) throws Exception {
        URI uri = new URI(this.baseUrl + "?utente_id=" + utenteId);
        HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        String body = (String) res.body();
        System.out.println(body);
        return XmlUtils.unmarshal(Utente.class, body);
    }

    /**
     * Crea un nuovo utente.
     * 
     * @param nome Il nome dell'utente.
     * @param email L'email dell'utente.
     * @return La risposta del web service.
     */
    public String createUtente(String nome, String email) throws Exception {
        URI uri = new URI(this.baseUrl + "/utenti.php");
        String xmlRequest = String.format(
            "<utente><nome>%s</nome><email>%s</email></utente>",
            nome, email
        );
        
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/xml")
            .POST(HttpRequest.BodyPublishers.ofString(xmlRequest))
            .build();
        
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }

    /**
     * Aggiorna un utente esistente.
     * 
     * @param id L'ID dell'utente da aggiornare.
     * @param nome Il nuovo nome dell'utente (opzionale).
     * @param email La nuova email dell'utente (opzionale).
     * @return La risposta del web service.
     */
    public String updateUtente(int id, String nome, String email) throws Exception {
        URI uri = new URI(this.baseUrl + "/utenti.php");
        StringBuilder xmlRequest = new StringBuilder("<utente><id>" + id + "</id>");
        
        if (nome != null) {
            xmlRequest.append("<nome>").append(nome).append("</nome>");
        }
        
        if (email != null) {
            xmlRequest.append("<email>").append(email).append("</email>");
        }
        
        xmlRequest.append("</utente>");
        
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/xml")
            .PUT(HttpRequest.BodyPublishers.ofString(xmlRequest.toString()))
            .build();
        
        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }

    /**
     * Elimina un utente.
     * 
     * @param id L'ID dell'utente da eliminare.
     * @return La risposta del web service.
     */
    public String deleteUtente(int id) throws Exception {
        URI uri = new URI(this.baseUrl + "/utenti.php?utente_id=" + id);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(uri)
            .DELETE()
            .build();

        HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

        if (res.statusCode() != 200)
            throw new WsException("Errore HTTP: " + res.statusCode());

        return res.body();
    }
}
