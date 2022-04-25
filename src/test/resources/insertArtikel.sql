insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, garantie, soort, artikelgroepId)
values ('testfood', 100, 120, 7, null, 'F', (select id from artikelgroepen where naam = 'test')),
       ('testnonfood', 100, 120, null, 30, 'NF', (select id from artikelgroepen where naam = 'test'));
insert into kortingen(artikelId, vanafAantal, percentage)
values ((select id from artikels where naam = 'testfood'), 1, 10);