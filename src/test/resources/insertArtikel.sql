insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, garantie, soort)
values ('testfood', 100, 120, 7, null, 'F'),
       ('testnonfood', 100, 120, null, 30, 'NF');
insert into kortingen(artikelId, vanafAantal, percentage)
values ((select id from artikels where naam = 'testfood'), 1, 10);