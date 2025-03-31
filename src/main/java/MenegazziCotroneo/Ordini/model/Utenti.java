package MenegazziCotroneo.Ordini.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "utente"
})
@XmlRootElement(name = "utenti")
public class Utenti {

    @XmlElement(name = "utente")
    protected List<Utente> utente;

    
    public List<Utente> getUtente() {
        if (utente == null) {
            utente = new ArrayList<Utente>();
        }
        return this.utente;
    }
}
