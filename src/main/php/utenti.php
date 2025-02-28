<?php
    /* 
    Schema del database:
    
    CREATE TABLE utenti (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(150) UNIQUE NOT NULL
    );
    */

    /* 
    Web Service API - Gestione Utenti
    ---------------------------------

    Questa API fornisce un'interfaccia per gestire gli utenti. Supporta operazioni di lettura, creazione, aggiornamento e cancellazione degli utenti.
    I dati vengono restituiti in formato XML e le operazioni sono accessibili tramite i metodi HTTP appropriati.

    Endpoint disponibili:
    
    1. Ottenere tutti gli utenti
        - Metodo: GET
        - URL: http://localhost/utenti.php
        - Parametri: Nessuno
        - Descrizione: Restituisce tutti gli utenti.

    2. Ottenere un utente specifico tramite ID
        - Metodo: GET
        - URL: http://localhost/utenti.php?id={ID_UTENTE}
        - Parametri:
            - id (int) → ID dell'utente.
        - Descrizione: Restituisce un utente specifico identificato dal suo ID.

    3. Creare un nuovo utente
        - Metodo: POST
        - URL: http://localhost/utenti.php
        - Parametri (in formato XML nel corpo della richiesta):
            - nome (string) → Nome dell'utente.
            - email (string) → Email dell'utente.
        - Descrizione: Crea un nuovo utente.

    4. Aggiornare un utente esistente
        - Metodo: PUT
        - URL: http://localhost/utenti.php
        - Parametri (in formato XML nel corpo della richiesta):
            - id (int) → ID dell'utente da aggiornare.
            - nome (string, opzionale) → Nuovo nome dell'utente.
            - email (string, opzionale) → Nuova email dell'utente.
        - Descrizione: Aggiorna i dettagli di un utente esistente.

    5. Eliminare un utente
        - Metodo: DELETE
        - URL: http://localhost/utenti.php?id={ID_UTENTE}
        - Parametri:
            - id (int) → ID dell'utente da eliminare.
        - Descrizione: Elimina un utente specificato dal suo ID.

    Formato di risposta:
    La risposta delle operazioni è sempre restituita in formato XML.

    Risposta di successo per una richiesta GET (elenco utenti):
    <utenti>
        <utente id="1">
            <nome>Mario Rossi</nome>
            <email>mario.rossi@example.com</email>
        </utente>
        ...
    </utenti>

    Risposta di successo per una richiesta POST (creazione utente):
    <response>
        <status>success</status>
        <message>Utente creato con successo</message>
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

    // Funzione per ottenere gli utenti
    function getUsers($conn, $id = null) {
        $sql = "SELECT id, nome, email FROM utenti";
        
        if ($id) {
            $sql .= " WHERE id = '$id'";
        }

        return $conn->query($sql);
    }

    // Funzione per creare un nuovo utente
    function createUser($conn, $nome, $email) {
        $sql = "INSERT INTO utenti (nome, email) VALUES ('$nome', '$email')";
        return $conn->query($sql);
    }

    // Funzione per aggiornare un utente
    function updateUser($conn, $id, $nome = null, $email = null) {
        $fields = [];

        if ($nome != null) {
            $fields[] = "nome = '$nome'";
        }

        if ($email != null) {
            $fields[] = "email = '$email'";
        }

        if (count($fields) > 0) {
            $sql = "UPDATE utenti SET " . implode(", ", $fields) . " WHERE id = '$id'";
            return $conn->query($sql);
        }

        return false;
    }

    // Funzione per eliminare un utente
    function deleteUser($conn, $id) {
        $sql = "DELETE FROM utenti WHERE id = '$id'";
        return $conn->query($sql);
    }

    // Funzione per costruire la risposta XML
    function buildXmlResponse($res) {
        $xml = new SimpleXMLElement('<utenti/>');
        while ($record = $res->fetch_assoc()) {
            $utente = $xml->addChild('utente');
            $utente->addAttribute('id', $record['id']);
            $utente->addChild('nome', $record['nome']);
            $utente->addChild('email', $record['email']);
        }
        return $xml->asXML();
    }

    // Gestione delle richieste
    $conn = getDbConnection();

    if ($_SERVER['REQUEST_METHOD'] == "GET") {
        if (isset($_GET["utente_id"])) {
            $id = $_GET["utente_id"];
            $res = getUsers($conn, $id);
        } else {
            $res = getUsers($conn);
        }

        echo buildXmlResponse($res);
    } 
    elseif ($_SERVER['REQUEST_METHOD'] == "POST") {
        $data = simplexml_load_string(file_get_contents("php://input"));

        if (isset($data->nome) && isset($data->email)) {
            $nome = $data->nome;
            $email = $data->email;

            if (createUser($conn, $nome, $email)) {
                echo "<response><status>success</status><message>Utente creato con successo</message></response>";
            } else {
                echo "<error>Errore durante la creazione dell'utente</error>";
            }
        } else {
            echo "<error>Dati incompleti</error>";
        }
    }
    elseif ($_SERVER['REQUEST_METHOD'] == "PUT") {
        $data = simplexml_load_string(file_get_contents("php://input"));

        if (isset($data->id)) {
            $id = $data->id;
            $nome = isset($data->nome) ? $data->nome : null;
            $email = isset($data->email) ? $data->email : null;

            if (updateUser($conn, $id, $nome, $email)) {
                echo "<response><status>success</status><message>Utente aggiornato</message></response>";
            } else {
                echo "<error>Errore durante l'aggiornamento dell'utente</error>";
            }
        } else {
            echo "<error>ID utente mancante</error>";
        }
    }
    elseif ($_SERVER['REQUEST_METHOD'] == "Delete") {
        if (isset($_GET['id'])) {
            $id = $_GET['id'];

            if (deleteUser($conn, $id)) {
                echo "<response><status>success</status><message>Utente eliminato</message></response>";
            } else {
                echo "<error>Errore durante l'eliminazione dell'utente</error>";
            }
        } else {
            echo "<error>ID utente mancante</error>";
        }
    }

    // Chiusura della connessione
    $conn->close();
?>
