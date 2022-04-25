package be.vdab.keuken.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(DefaultArtikelService.class)
@ComponentScan(value = "be.vdab.keuken.repositories", resourcePattern = "JpaArtikelRepository.class")
@Sql({"/insertArtikelGroep.sql", "/insertArtikel.sql"})
class DefaultArtikelServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ARTIKELS = "artikels";
    private final DefaultArtikelService service;
    private final EntityManager manager;

    DefaultArtikelServiceIntegrationTest(DefaultArtikelService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }

    private long idVanTestArtikel() {
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testfood'", Long.class);
    }

    @Test
    void verhoogVerkoopPrijs() {
        var id = idVanTestArtikel();
        service.verhoogVerkoopPrijs(id, BigDecimal.TEN);
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 130 and id = " + id)).isOne();
    }
}
