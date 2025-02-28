

package MenegazziCotroneo.Ordini.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per rappresentare un Ordine.</p>
 * 
 * <p>Il seguente schema XML specifica la struttura prevista:</p>
 * 
 * <pre>
 * &lt;complexType name="Ordine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nome_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="email_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prodotto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="quantita" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="prezzo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="data_ordine" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ordine", propOrder = {
    "nomeCliente",
    "emailCliente",
    "prodotto",
    "quantita",
    "prezzo"
})
public class Ordine {

    @XmlElement(name = "nome_cliente", required = true)
    protected String nomeCliente;

    @XmlElement(name = "email_cliente", required = true)
    protected String emailCliente;

    @XmlElement(required = true)
    protected String prodotto;

    @XmlElement(required = true)
    protected BigInteger quantita;

    @XmlElement(required = true)
    protected BigDecimal prezzo;

    @XmlAttribute(name = "id")
    protected BigInteger id;

   

    /**
     * Restituisce il nome del cliente.
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * Imposta il nome del cliente.
     */
    public void setNomeCliente(String value) {
        this.nomeCliente = value;
    }

    /**
     * Restituisce l'email del cliente.
     */
    public String getEmailCliente() {
        return emailCliente;
    }

    /**
     * Imposta l'email del cliente.
     */
    public void setEmailCliente(String value) {
        this.emailCliente = value;
    }

    /**
     * Restituisce il nome del prodotto ordinato.
     */
    public String getProdotto() {
        return prodotto;
    }

    /**
     * Imposta il nome del prodotto ordinato.
     */
    public void setProdotto(String value) {
        this.prodotto = value;
    }

    /**
     * Restituisce la quantità ordinata.
     */
    public BigInteger getQuantita() {
        return quantita;
    }

    /**
     * Imposta la quantità ordinata.
     */
    public void setQuantita(BigInteger value) {
        this.quantita = value;
    }

    /**
     * Restituisce il prezzo dell'ordine.
     */
    public BigDecimal getPrezzo() {
        return prezzo;
    }

    /**
     * Imposta il prezzo dell'ordine.
     */
    public void setPrezzo(BigDecimal value) {
        this.prezzo = value;
    }

    /**
     * Restituisce l'ID dell'ordine.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'ordine.
     */
    public void setId(BigInteger value) {
        this.id = value;
    }


}
