package be.vdab.keuken.services;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(long id, BigDecimal bedrag);
}
