package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ArtikelGroepTest {
    private ArtikelGroep groep1;
    private ArtikelGroep groep2;
    private Artikel artikel1;

    @BeforeEach
    void beforeEach() {
        groep1 = new ArtikelGroep("test");
        groep2 = new ArtikelGroep("test2");
        artikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE, 1, groep1);
    }

    @Test
    void groep1IsDeArtikelGroepVanArtikel1() {
        assertThat(artikel1.getArtikelGroep()).isEqualTo(groep1);
        assertThat(groep1.getArtikels()).containsOnly(artikel1);
    }

    @Test
    void artikel1VerhuistNaarGroep2() {
        assertThat(groep2.add(artikel1)).isTrue();
        assertThat(groep1.getArtikels()).doesNotContain(artikel1);
        assertThat(groep2.getArtikels()).containsOnly(artikel1);
    }

    @Test
    void nullAlsArtikelToevoegenMislukt() {
        assertThatNullPointerException().isThrownBy(() -> groep1.add(null));
    }
}