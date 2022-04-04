package be.vdab.keuken.services;

import be.vdab.keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
class DefaultArtikelService implements ArtikelService {
    private final ArtikelRepository artikelRepository;

    DefaultArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal bedrag) {
        artikelRepository.findById(id)
                .orElseThrow(ArtikelNietGevondenException::new)
                .verhoogVerkoopPrijs(bedrag);
    }
}