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
    "ordine"
})
@XmlRootElement(name = "ordini")
public class Ordini {

    @XmlElement(name = "ordine")
    protected List<Ordine> ordine;

    
    public List<Ordine> getOrdine() {
        if (ordine == null) {
            ordine = new ArrayList<Ordine>();
        }
        return this.ordine;
    }
}
