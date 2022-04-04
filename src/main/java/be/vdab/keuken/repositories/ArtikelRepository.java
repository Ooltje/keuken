package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;

import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
}
