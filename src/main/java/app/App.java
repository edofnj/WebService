package app;

import app.model.xml.Ordini;
import app.model.xml.Ordine;
import app.util.WsClient;

public class App {
	public static void main(String[] args) {
		WsClient c = new WsClient("http://localhost/webservice/ordini.php");

		try {
			// Recupera tutti gli ordini
			Ordini ordini = c.getOrdini();
			
			// Recupera gli ordini di un utente specifico (per ID)
			// Ordini ordini = c.getOrdini("2"); // Cambia con l'ID utente desiderato
			
			// Recupera gli ordini di un utente specifico (per nome)
			// Ordini ordini = c.getOrdiniByName("Mario Rossi");

			for (Ordine ordine : ordini.getOrdine()) {
				System.out.println("Cliente: " + ordine.getNomeCliente() +
						", Email: " + ordine.getEmailCliente() +
						", Prodotto: " + ordine.getProdotto() +
						", Quantità: " + ordine.getQuantita() +
						", Prezzo: " + ordine.getPrezzo() +
						", Data ordine: " + ordine.getDataOrdine());
			}
		} catch (Exception e) {
			System.err.println("Eccezione: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
