package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql({"/insertArtikelGroep.sql", "/insertArtikel.sql"})
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ARTIKELS = "artikels";
    //private Artikel artikel;
    private final JpaArtikelRepository repository;
    private final EntityManager manager;

    /*
    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
    }

     */

    JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testfood'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testnonfood'", Long.class);
    }


    @Test
    void findFoodArtikelById() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .containsInstanceOf(FoodArtikel.class)
                .hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testfood"));
    }

    @Test
    void findNonFoodArtikelById() {
        assertThat(repository.findById(idVanTestNonFoodArtikel()))
                .containsInstanceOf(NonFoodArtikel.class)
                .hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testnonfood"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void createFoodArtikel() {
        var groep = new ArtikelGroep("test");
        manager.persist(groep);
        var artikel = new FoodArtikel("testfood", BigDecimal.ONE,BigDecimal.TEN,7, groep);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void createNonFoodArtikel() {
        var groep = new ArtikelGroep("test");
        manager.persist(groep);
        var artikel = new NonFoodArtikel("testnonfood", BigDecimal.ONE,BigDecimal.TEN,30, groep);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void findByNaamContains() {
        assertThat(repository.findByNaamContains("es"))
                .hasSize(countRowsInTableWhere(ARTIKELS, "naam like '%es%'"))
                .extracting(Artikel::getNaam)
                .allSatisfy(naam -> assertThat(naam).containsIgnoringCase("es"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.verhoogAlleVerkoopPrijzen(BigDecimal.TEN))
                .isEqualTo(countRowsInTable(ARTIKELS));
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs= 132 and id = " + idVanTestFoodArtikel())).isOne();
    }

    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getKortingen())
                        .containsOnly(new Korting(1, BigDecimal.TEN)));
    }

    @Test
    void artikelGroepLazyLoaded() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getArtikelGroep().getNaam()).isEqualTo("test"));
    }
}