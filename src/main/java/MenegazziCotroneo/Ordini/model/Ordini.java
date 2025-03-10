package MenegazziCotroneo.Ordini.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per rappresentare una lista di ordini.</p>
 * 
 * <p>Il seguente frammento XML mostra la struttura prevista:</p>
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ordine" type="{}Ordine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ordine"
})
@XmlRootElement(name = "ordini")
public class Ordini {

    @XmlElement(name = "ordine")
    protected List<Ordine> ordine;

    /**
     * Restituisce la lista degli ordini.
     * 
     * <p>
     * Questo metodo restituisce un riferimento alla lista effettiva,
     * quindi qualsiasi modifica si rifletterà nell'oggetto XML.
     * 
     * <p>
     * Per esempio, per aggiungere un nuovo ordine:
     * <pre>
     *    getOrdine().add(new Ordine());
     * </pre>
     * 
     * <p>
     * Oggetti di tipo {@link Ordine} sono consentiti nella lista.
     * 
     * @return lista di ordini
     */
    public List<Ordine> getOrdine() {
        if (ordine == null) {
            ordine = new ArrayList<Ordine>();
        }
        return this.ordine;
    }
}
