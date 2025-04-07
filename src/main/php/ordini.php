<?php
    /* 
    Schema del database:
    
    CREATE TABLE utenti (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(150) UNIQUE NOT NULL
    );

    CREATE TABLE ordini (
        id INT AUTO_INCREMENT PRIMARY KEY,
        utente_id INT NOT NULL,
        prodotto VARCHAR(255) NOT NULL,
        quantita INT NOT NULL,
        prezzo DECIMAL(10,2) NOT NULL,
        FOREIGN KEY (utente_id) REFERENCES utenti(id)
    );
    */
    /* 
    Web Service API - Gestione Ordini e Utenti
    -----------------------------------------

    Questa API fornisce un'interfaccia per gestire gli ordini degli utenti. Supporta operazioni di lettura, creazione, aggiornamento e cancellazione degli ordini.
    I dati vengono restituiti in formato XML e le operazioni sono accessibili tramite i metodi HTTP appropriati.

    Endpoint disponibili:
    
    1. Ottenere tutti gli ordini
        - Metodo: GET
        - URL: http://localhost/ordini.php
        - Parametri: Nessuno
        - Descrizione: Restituisce tutti gli ordini effettuati, inclusi i dettagli sugli utenti.
    
    2. Ottenere gli ordini di un utente specifico tramite ID
        - Metodo: GET
        - URL: http://localhost/ordini.php?utente_id={ID_UTENTE}
        - Parametri:
            - utente_id (int) → ID dell'utente per filtrare gli ordini.
        - Descrizione: Restituisce gli ordini effettuati da un singolo utente identificato dal suo ID.

    3. Ottenere gli ordini di un utente specifico tramite nome
        - Metodo: GET
        - URL: http://localhost/ordini.php?nome={NOME_UTENTE}
        - Parametri:
            - nome (string) → Nome dell'utente per filtrare gli ordini (la ricerca è case-insensitive).
        - Descrizione: Restituisce gli ordini effettuati da un utente specificato dal suo nome (se disponibile).

    4. Creare un nuovo ordine
        - Metodo: POST
        - URL: http://localhost/ordini.php
        - Parametri (in formato XML nel corpo della richiesta):
            - utente_id (int) → ID dell'utente che effettua l'ordine.
            - prodotto (string) → Nome del prodotto ordinato.
            - quantita (int) → Quantità del prodotto ordinato.
            - prezzo (decimal) → Prezzo del prodotto.
        - Descrizione: Crea un nuovo ordine per un utente.

    5. Aggiornare un ordine esistente
        - Metodo: PUT
        - URL: http://localhost/ordini.php
        - Parametri (in formato XML nel corpo della richiesta):
            - id (int) → ID dell'ordine da aggiornare.
            - prodotto (string, opzionale) → Nuovo nome del prodotto.
            - quantita (int, opzionale) → Nuova quantità.
            - prezzo (decimal, opzionale) → Nuovo prezzo.
        - Descrizione: Aggiorna i dettagli di un ordine esistente (può essere aggiornato uno o più campi).

    6. Eliminare un ordine
        - Metodo: DELETE
        - URL: http://localhost/ordini.php?id={ID_ORDINE}
        - Parametri:
            - id (int) → ID dell'ordine da eliminare.
        - Descrizione: Elimina un ordine specificato dal suo ID.
    
    Formato di risposta:
    La risposta delle operazioni è sempre restituita in formato XML.

    Risposta di successo per una richiesta GET (elenco ordini):
    <ordini>
        <ordine id="1">
            <nome_cliente>Mario Rossi</nome_cliente>
            <email_cliente>mario.rossi@example.com</email_cliente>
            <prodotto>Smartphone</prodotto>
            <quantita>2</quantita>
            <prezzo>599.99</prezzo>
        </ordine>
        ...
    </ordini>

    Risposta di successo per una richiesta POST (creazione ordine):
    <response>
        <status>success</status>
        <message>Ordine creato con successo</message>
    </response>

    Risposta di errore per una richiesta (ad esempio dati incompleti):
    <error>Dati incompleti</error>

    */



    // Imposta l'intestazione per il formato XML
    header('Content-Type: application/xml');

    // Funzione per la connessione al database
    function getDbConnection() {
        $conn = new mysqli("localhost", "root", "", "webservice");
        if ($conn->connect_error) {
            die("<error>Connessione fallita: " . $conn->connect_error . "</error>");
        }
        return $conn;
    }

    // Funzione per ottenere gli ordini
    function getOrders($conn, $utente_id = null, $nome = null) {
        $sql = "SELECT ordini.id, utenti.nome, utenti.email, ordini.prodotto, ordini.quantita, ordini.prezzo
                FROM ordini
                JOIN utenti ON ordini.utente_id = utenti.id";
        
        if ($utente_id) {
            $sql .= " WHERE ordini.utente_id = '$utente_id'";
        } elseif ($nome) {
            $nome = $conn->real_escape_string($nome);
            $sql .= " WHERE utenti.nome LIKE '%$nome%'";
        }
        
        return $conn->query($sql);
    }

    // Funzione per creare un ordine
    function createOrder($conn, $utente_id, $prodotto, $quantita, $prezzo) {
        $sql = "INSERT INTO ordini (utente_id, prodotto, quantita, prezzo) 
                VALUES ('$utente_id', '$prodotto', '$quantita', '$prezzo')";
        
        return $conn->query($sql);
    }

    // Funzione per aggiornare un ordine
    function updateOrder($conn, $id, $prodotto = null, $quantita = null, $prezzo = null) {
        $fields = [];

        // Verifica se il prodotto è stato fornito e aggiungi al campo SQL
        if ($prodotto != null) {
            $fields[] = "prodotto = '$prodotto'";
        }

        // Verifica se la quantità è stata fornita e aggiungi al campo SQL
        if ($quantita != null) {
            $fields[] = "quantita = '$quantita'";
        }

        // Verifica se il prezzo è stato fornito e aggiungi al campo SQL
        if ($prezzo != null) {
            $fields[] = "prezzo = '$prezzo'";
        }

        // Se ci sono campi da aggiornare
        if (count($fields) > 0) {
            $sql = "UPDATE ordini SET " . implode(", ", $fields) . " WHERE id = '$id'";
            return $conn->query($sql);
        }

        // Se non ci sono campi da aggiornare, ritorna false
        return false;
    }

    // Funzione per eliminare un ordine
    function deleteOrder($conn, $id) {
        $sql = "DELETE FROM ordini WHERE id = '$id'";
        return $conn->query($sql);
    }

    // Funzione per costruire la risposta XML
    function buildXmlResponse($res) {
        $xml = new SimpleXMLElement('<ordini/>');
        while ($record = $res->fetch_assoc()) {
            $ordine = $xml->addChild('ordine');
            $ordine->addAttribute('id', $record['id']);
            $ordine->addChild('nome_cliente', $record['nome']);
            $ordine->addChild('email_cliente', $record['email']);
            $ordine->addChild('prodotto', $record['prodotto']);
            $ordine->addChild('quantita', $record['quantita']);
            $ordine->addChild('prezzo', $record['prezzo']);
        }
        return $xml->asXML();
    }

    // Gestione delle richieste
    $conn = getDbConnection();

    if ($_SERVER['REQUEST_METHOD'] == "GET") {
        // Se è stato passato utente_id o nome
        if (isset($_GET["utente_id"])) {
            $utente_id = $_GET["utente_id"];
            $res = getOrders($conn, $utente_id);
        } elseif (isset($_GET["nome"])) {
            $nome = $_GET["nome"];
            $res = getOrders($conn, null, $nome);
        } else {
            $res = getOrders($conn);
        }

        echo buildXmlResponse($res);
    }
    // Se il metodo è POST, creiamo un nuovo ordine
    elseif ($_SERVER['REQUEST_METHOD'] == "POST") {
        $data = simplexml_load_string(file_get_contents("php://input"));

        if (isset($data->utente_id) && isset($data->prodotto) && isset($data->quantita) && isset($data->prezzo)) {
            $utente_id = $data->utente_id;
            $prodotto = $data->prodotto;
            $quantita = $data->quantita;
            $prezzo = $data->prezzo;

            if (createOrder($conn, $utente_id, $prodotto, $quantita, $prezzo)) {
                echo "<response><status>success</status><message>Ordine creato con successo</message></response>";
            } else {
                echo "<error>Errore durante la creazione dell'ordine</error>";
            }
        } else {
            echo "<error>Dati incompleti</error>";
        }
    }
    // Se il metodo è PUT, aggiorniamo un ordine
    elseif ($_SERVER['REQUEST_METHOD'] == "PUT") {
        $data = simplexml_load_string(file_get_contents("php://input"));

        if (isset($data->id)) {
            $id = $data->id;

            // Controlla se i dati sono presenti e aggiornabili
            if (isset($data->prodotto) && $data->prodotto != null) {
                $prodotto = $data->prodotto;
            } else {
                $prodotto = null;
            }

            if (isset($data->quantita) && $data->quantita != null) {
                $quantita = $data->quantita;
            } else {
                $quantita = null;
            }

            if (isset($data->prezzo) && $data->prezzo != null) {
                $prezzo = $data->prezzo;
            } else {
                $prezzo = null;
            }

            if (updateOrder($conn, $id, $prodotto, $quantita, $prezzo)) {
                echo "<response><status>success</status><message>Ordine aggiornato</message></response>";
            } else {
                echo "<error>Errore durante l'aggiornamento dell'ordine</error>";
            }
        } else {
            echo "<error>ID ordine mancante</error>";
        }
    }
    // Se il metodo è DELETE, eliminiamo un ordine
    elseif ($_SERVER['REQUEST_METHOD'] == "DELETE") {  
        if (isset($_GET['id'])) {
            $id = $_GET['id'];

            if (deleteOrder($conn, $id)) {
                echo "<response><status>success</status><message>Ordine eliminato</message></response>";
            } else {
                echo "<error>Errore durante l'eliminazione dell'ordine</error>";
            }
        } else {
            echo "<error>ID ordine mancante</error>";
        }
    }

    // Chiusura della connessione
    $conn->close();
?>
