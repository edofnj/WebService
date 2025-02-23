<?php
    /* 
    Schema del database:
    
    CREATE TABLE utenti (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(150) UNIQUE NOT NULL,
        data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE ordini (
        id INT AUTO_INCREMENT PRIMARY KEY,
        utente_id INT NOT NULL,
        prodotto VARCHAR(255) NOT NULL,
        quantita INT NOT NULL,
        prezzo DECIMAL(10,2) NOT NULL,
        data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (utente_id) REFERENCES utenti(id)
    );
    */

    /**
     * Web Service API - Gestione Ordini
     * ---------------------------------
     * Questa API consente di ottenere informazioni sugli ordini effettuati dagli utenti.
     * I dati vengono restituiti in formato XML.
     *
     * Endpoint disponibili:
     *
     * 1. Ottenere tutti gli ordini
     *    Metodo: GET
     *    URL: http://localhost/ordini.php
     *    Parametri: Nessuno
     *
     * 2. Ottenere gli ordini di un utente specifico tramite ID
     *    Metodo: GET
     *    URL: http://localhost/ordini.php?utente_id={ID_UTENTE}
     *    Parametri:
     *       - utente_id (int) → Filtra gli ordini in base all'ID dell'utente
     * 
     * 3. Ottenere gli ordini di un utente specifico tramite nome
     *    Metodo: GET
     *    URL: http://localhost/ordini.php?nome={NOME}
     *    Parametri:
     *       - nome (string) → Filtra gli ordini in base al nome dell'utente (case-insensitive)
     */

    // Imposta l'intestazione per il formato XML
    header('Content-Type: application/xml');

    // Connessione al database
    $conn = new mysqli("localhost", "root", "", "webservice");

    // Verifica della connessione
    if ($conn->connect_error) {
        die("<error>Connessione fallita: " . $conn->connect_error . "</error>");
    }

    // Se il metodo della richiesta è GET
    if ($_SERVER['REQUEST_METHOD'] == "GET") {
        $sql = "SELECT ordini.id, utenti.nome, utenti.email, ordini.prodotto, ordini.quantita, ordini.prezzo, ordini.data_ordine
                FROM ordini
                JOIN utenti ON ordini.utente_id = utenti.id";

        // Controlla se è stato passato il parametro utente_id
        if (isset($_GET["utente_id"])) {
            $utente_id = $conn->real_escape_string($_GET["utente_id"]);
            $sql .= " WHERE ordini.utente_id = '$utente_id'";
        } 
        
        // Controlla se è stato passato il parametro nome
        elseif (isset($_GET["nome"])) {
            $nome = $conn->real_escape_string($_GET["nome"]);
            
            // Recupera l'ID dell'utente dal nome
            $query_utente = "SELECT id FROM utenti WHERE nome LIKE '%$nome%' LIMIT 1";
            $res_utente = $conn->query($query_utente);

            if ($res_utente->num_rows > 0) {
                $utente = $res_utente->fetch_assoc();
                $utente_id = $utente['id'];
                $sql .= " WHERE ordini.utente_id = '$utente_id'";
            } else {
                die("<error>Nessun utente trovato con il nome '$nome'</error>");
            }
        }

        $res = $conn->query($sql);

        // Creazione dell'elemento radice XML
        $xml = new SimpleXMLElement('<ordini/>');

        // Popolamento dell'XML con i dati
        while ($record = $res->fetch_assoc()) {
            $ordine = $xml->addChild('ordine');

            // Attributi
            $ordine->addAttribute('id', $record['id']);
            $ordine->addAttribute('data_ordine', $record['data_ordine']);

            // Dati dell'ordine
            $ordine->addChild('nome_cliente', $record['nome']);
            $ordine->addChild('email_cliente', $record['email']);
            $ordine->addChild('prodotto', $record['prodotto']);
            $ordine->addChild('quantita', $record['quantita']);
            $ordine->addChild('prezzo', $record['prezzo']);
        }

        $res->free();

        // Invio della risposta XML al client
        echo $xml->asXML();
    }

    // Chiusura della connessione
    $conn->close();
?>