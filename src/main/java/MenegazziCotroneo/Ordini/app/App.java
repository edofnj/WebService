package MenegazziCotroneo.Ordini.app;

import java.util.Scanner;

import MenegazziCotroneo.Ordini.model.Utente;
import MenegazziCotroneo.Ordini.model.Utenti;
import MenegazziCotroneo.Ordini.model.Ordine;
import MenegazziCotroneo.Ordini.model.Ordini;
import MenegazziCotroneo.Ordini.util.WsClient;

public class App {
    public static void main(String[] args) {
        // Crea i client per gli ordini e per gli utenti con i rispettivi URL
        WsClient clientOrdini = new WsClient("http://localhost/webservice/ordini.php");
        WsClient clientUtenti = new WsClient("http://localhost/webservice/utenti.php");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Visualizza tutti gli ordini");
            System.out.println("2. Visualizza gli ordini di un utente per ID");
            System.out.println("3. Visualizza gli ordini di un utente per nome");
            System.out.println("4. Crea un nuovo ordine");
            System.out.println("5. Aggiorna un ordine esistente");
            System.out.println("6. Elimina un ordine");
            System.out.println("7. Visualizza tutti gli utenti");
            System.out.println("8. Visualizza un utente per ID");
            System.out.println("9. Crea un nuovo utente");
            System.out.println("10. Aggiorna un utente esistente");
            System.out.println("11. Elimina un utente");
            System.out.println("12. Esci");
            System.out.print("Scegli un'opzione: ");
            
            int scelta = scanner.nextInt();
            scanner.nextLine();  // Consuma la newline lasciata da nextInt()
            
            switch (scelta) {
                case 1:
                    // Recupera tutti gli ordini
                    try {
                        Ordini ordini = clientOrdini.getOrdini();
                        for (Ordine ordine : ordini.getOrdine()) {
                            System.out.println("Cliente: " + ordine.getNomeCliente() +
                                    ", Email: " + ordine.getEmailCliente() +
                                    ", Prodotto: " + ordine.getProdotto() +
                                    ", Quantità: " + ordine.getQuantita() +
                                    ", Prezzo: " + ordine.getPrezzo());
                        }
                    } catch (Exception e) {
                        System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Recupera gli ordini di un utente per ID
                    System.out.print("Inserisci l'ID dell'utente: ");
                    String utenteId = scanner.nextLine();
                    try {
                        Ordini ordini = clientOrdini.getOrdini(utenteId);
                        for (Ordine ordine : ordini.getOrdine()) {
                            System.out.println("Cliente: " + ordine.getNomeCliente() +
                                    ", Email: " + ordine.getEmailCliente() +
                                    ", Prodotto: " + ordine.getProdotto() +
                                    ", Quantità: " + ordine.getQuantita() +
                                    ", Prezzo: " + ordine.getPrezzo());
                        }
                    } catch (Exception e) {
                        System.err.println("Errore durante il recupero degli ordini per ID: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Recupera gli ordini di un utente per nome
                    System.out.print("Inserisci il nome dell'utente: ");
                    String nome = scanner.nextLine();
                    try {
                        Ordini ordini = clientOrdini.getOrdiniByName(nome);
                        for (Ordine ordine : ordini.getOrdine()) {
                            System.out.println("Cliente: " + ordine.getNomeCliente() +
                                    ", Email: " + ordine.getEmailCliente() +
                                    ", Prodotto: " + ordine.getProdotto() +
                                    ", Quantità: " + ordine.getQuantita() +
                                    ", Prezzo: " + ordine.getPrezzo());
                        }
                    } catch (Exception e) {
                        System.err.println("Errore durante il recupero degli ordini per nome: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Crea un nuovo ordine
                    System.out.print("Inserisci l'ID dell'utente: ");
                    String utenteIdCreate = scanner.nextLine();
                    System.out.print("Inserisci il prodotto: ");
                    String prodotto = scanner.nextLine();
                    System.out.print("Inserisci la quantità: ");
                    int quantita = scanner.nextInt();
                    System.out.print("Inserisci il prezzo: ");
                    double prezzo = scanner.nextDouble();
                    scanner.nextLine();  // Consuma la newline
                    try {
                        clientOrdini.createOrder(utenteIdCreate, prodotto, quantita, prezzo);
                        System.out.println("Ordine creato con successo!");
                    } catch (Exception e) {
                        System.err.println("Errore durante la creazione dell'ordine: " + e.getMessage());
                    }
                    break;

                case 5:
                    // Aggiorna un ordine esistente
                    System.out.print("Inserisci l'ID dell'ordine da aggiornare: ");
                    String ordineIdUpdateStr = scanner.nextLine();
                    System.out.print("Nuovo prodotto (lascia vuoto per non cambiare): ");
                    String prodottoUpdate = scanner.nextLine();
                    System.out.print("Nuova quantità (lascia 0 per non cambiare): ");
                    int quantitaUpdate = scanner.nextInt();
                    System.out.print("Nuovo prezzo (lascia 0 per non cambiare): ");
                    double prezzoUpdate = scanner.nextDouble();
                    scanner.nextLine();  // Consuma la newline

                    try {
                        // Converti l'ID dell'ordine da String a int
                        int ordineIdUpdate = Integer.parseInt(ordineIdUpdateStr);  // Conversione da String a int

                        // Aggiorna l'ordine, passando i parametri al metodo updateOrder
                        clientOrdini.updateOrder(ordineIdUpdate, 
                            prodottoUpdate.isEmpty() ? null : prodottoUpdate, 
                            quantitaUpdate == 0 ? null : quantitaUpdate, 
                            prezzoUpdate == 0 ? null : prezzoUpdate
                        );
                        System.out.println("Ordine aggiornato con successo!");
                    } catch (NumberFormatException e) {
                        System.err.println("Errore: l'ID dell'ordine deve essere un numero intero.");
                    } catch (Exception e) {
                        System.err.println("Errore durante l'aggiornamento dell'ordine: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Elimina un ordine
                    System.out.print("Inserisci l'ID dell'ordine da eliminare: ");
                    String ordineIdDeleteStr = scanner.nextLine();
                    try {
                        int ordineIdDelete = Integer.parseInt(ordineIdDeleteStr);  // Converti la stringa in int
                        clientOrdini.deleteOrder(ordineIdDelete);
                        System.out.println("Ordine eliminato con successo!");
                    } catch (NumberFormatException e) {
                        System.err.println("Errore: l'ID dell'ordine deve essere un numero intero.");
                    } catch (Exception e) {
                        System.err.println("Errore durante l'eliminazione dell'ordine: " + e.getMessage());
                    }
                    break;

                case 7:
                    // Visualizza tutti gli utenti
                    try {
                        Utenti utenti = clientUtenti.getUtenti();
                        for (Utente utente : utenti.getUtente()) {
                            System.out.println("ID: " + utente.getId() + ", Nome: " + utente.getNome() +
                                    ", Email: " + utente.getEmail());
                        }
                    } catch (Exception e) {
                        System.err.println("Errore durante il recupero degli utenti: " + e.getMessage());
                    }
                    break;

                case 8:
                    // Visualizza un utente per ID
                    System.out.print("Inserisci l'ID dell'utente: ");
                    String utenteIdRead = scanner.nextLine();
                    try {
                        Utente utente = clientUtenti.getUtente(utenteIdRead);
                        System.out.println("ID: " + utente.getId() + ", Nome: " + utente.getNome() +
                                ", Email: " + utente.getEmail());
                    } catch (Exception e) {
                        System.err.println("Errore durante il recupero dell'utente per ID: " + e.getMessage());
                    }
                    break;

                case 9:
                    // Crea un nuovo utente
                    System.out.print("Inserisci il nome dell'utente: ");
                    String nomeCreate = scanner.nextLine();
                    System.out.print("Inserisci l'email dell'utente: ");
                    String emailCreate = scanner.nextLine();
                    try {
                        clientUtenti.createUtente(nomeCreate, emailCreate);
                        System.out.println("Utente creato con successo!");
                    } catch (Exception e) {
                        System.err.println("Errore durante la creazione dell'utente: " + e.getMessage());
                    }
                    break;

                case 10:
                    // Aggiorna un utente esistente
                    System.out.print("Inserisci l'ID dell'utente da aggiornare: ");
                    String idUtenteUpdateStr = scanner.nextLine();
                    System.out.print("Nuovo nome (lascia vuoto per non cambiare): ");
                    String nomeUpdate = scanner.nextLine();
                    System.out.print("Nuova email (lascia vuoto per non cambiare): ");
                    String emailUpdate = scanner.nextLine();
                    try {
                        int idUtenteUpdate = Integer.parseInt(idUtenteUpdateStr);
                        clientUtenti.updateUtente(idUtenteUpdate, 
                            nomeUpdate.isEmpty() ? null : nomeUpdate, 
                            emailUpdate.isEmpty() ? null : emailUpdate
                        );
                        System.out.println("Utente aggiornato con successo!");
                    } catch (Exception e) {
                        System.err.println("Errore durante l'aggiornamento dell'utente: " + e.getMessage());
                    }
                    break;

                case 11:
                    // Elimina un utente
                    System.out.print("Inserisci l'ID dell'utente da eliminare: ");
                    String idUtenteDeleteStr = scanner.nextLine();
                    try {
                        int idUtenteDelete = Integer.parseInt(idUtenteDeleteStr);
                        clientUtenti.deleteUtente(idUtenteDelete);
                        System.out.println("Utente eliminato con successo!");
                    } catch (Exception e) {
                        System.err.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
                    }
                    break;

                case 12:
                    // Esci
                    System.out.println("Uscita...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }
}
