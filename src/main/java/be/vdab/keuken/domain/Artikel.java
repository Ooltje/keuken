package be.vdab.keuken.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "artikels")
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;
    @ElementCollection
    @OrderBy("vanafAantal")
    @CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelId"))
    private Set<Korting> kortingen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artikelgroepId")
    private ArtikelGroep artikelGroep;


    protected Artikel() {
    }

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, ArtikelGroep artikelGroep) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortingen = new LinkedHashSet<>();
        setArtikelGroep(artikelGroep);
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public void verhoogVerkoopPrijs(BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        verkoopprijs = verkoopprijs.add(bedrag);
    }

    public Set<Korting> getKortingen() {
        return Collections.unmodifiableSet(kortingen);
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelGroep(ArtikelGroep artikelGroep) {
        if (!artikelGroep.getArtikels().contains(this)){
            artikelGroep.add(this);
        }
        this.artikelGroep = artikelGroep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Artikel artikel)) return false;
        return naam.equalsIgnoreCase(artikel.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toLowerCase());
    }
}
