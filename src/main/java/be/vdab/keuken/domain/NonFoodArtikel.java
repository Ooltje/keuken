package be.vdab.keuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {
    private int garantie;

    protected NonFoodArtikel() {
    }

    public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int garantie, ArtikelGroep artikelGroep) {
        super(naam, aankoopprijs, verkoopprijs, artikelGroep);
        this.garantie = garantie;
    }

    public int getGarantie() {
        return garantie;
    }
}
