package be.vdab.keuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
    private int houdbaarheid;

    protected FoodArtikel() {
    }

    public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid, ArtikelGroep artikelGroep) {
        super(naam, aankoopprijs, verkoopprijs, artikelGroep);
        this.houdbaarheid = houdbaarheid;
    }

    public int getHoudbaarheid() {
        return houdbaarheid;
    }
}
