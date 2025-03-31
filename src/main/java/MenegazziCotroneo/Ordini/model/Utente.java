package MenegazziCotroneo.Ordini.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Utente", propOrder = {
    "nome",
    "email"
})
public class Utente {
	
	

    @XmlElement(required = true)
    protected String nome;

    @XmlElement(required = true)
    protected String email;

    @XmlAttribute(name = "id")
    protected Integer id;
    

    /**
     * Restituisce il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Restituisce l'email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente.
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Restituisce l'ID dell'utente.
     */
    public Integer getId() {
        return id;
    }


}
