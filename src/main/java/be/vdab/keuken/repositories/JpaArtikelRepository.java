package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaArtikelRepository implements ArtikelRepository {
    private final EntityManager manager;

    JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    @Override
    public void create(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findByNaamContains(String woord) {
        return manager.createNamedQuery("Artikel.findByNaamContains", Artikel.class)
                .setParameter("zoals", '%' + woord + '%')
                .getResultList();
    }

    @Override
    public int verhoogAlleVerkoopPrijzen(BigDecimal percentage) {
        return manager.createNamedQuery("Artikel.verhoogAlleVerkoopPrijzen")
                .setParameter("percentage", percentage)
                .executeUpdate();
    }
}
