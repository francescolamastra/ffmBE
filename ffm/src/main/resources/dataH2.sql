INSERT INTO stadio (LIVELLO, COEFFICIENTE, COMPENSO_VITTORIA, COMPENSO_PAREGGIO, BONUS, MANUTENZIONE) VALUES
(1, 0.25, 3, 1, 1, 25),
(2, 0.50, 6, 2, 1.5, 50);

-- Categoria
INSERT INTO categoria (DESCRIZIONE, SIGLA) VALUES
('Categoria 1', 'A'),
('Categoria 2', 'B'),
('Categoria 3', 'C')
;

----Giocatore
--INSERT INTO giocatore (ID_FANTAGAZZETTA, NOME, RUOLO) VALUES
--(1, 'Giocatore 1', 'P'),
--(2, 'Giocatore 2', 'A');

-- Nazione
INSERT INTO nazione (DESCRIZIONE, SIGLA) VALUES
('Italia', 'ITA'),
('Euro Nord', 'EN'),
('Francia', 'FRA');

---- Squadra
--INSERT INTO squadra (ID_NAZIONE, ID_CATEGORIA, NOME, ID_STADIO, DATA_CREAZIONE) VALUES
--(1, 1, 'Squadra 1', 1, CURRENT_TIMESTAMP),
--(2, 2, 'Squadra 2', 2, CURRENT_TIMESTAMP);

-- Stagione
INSERT INTO stagione (ANNO_INIZIO, ANNO_FINE) VALUES
(2021, 2022),
(2022, 2023),
(2023, 2024),
(2024, 2025);

-- Utente
INSERT INTO utente (NOME, COGNOME, TELEFONO, EMAIL, DATA_CREAZIONE) VALUES
('Mario', 'Rossi', '340123456', 'test1@mail.it', CURRENT_TIMESTAMP),
('Luigi', 'Bianchi', '340123456', 'test1@mail.it', CURRENT_TIMESTAMP);

-- Tipo Operazione
INSERT INTO tipo_operazione (SIGLA, DESCRIZIONE) VALUES
('A', 'Acquisto'),
('C', 'Cessione'),
('S', 'Svincolo');

-- Tipo Dett Trattativa
INSERT INTO tipo_dett_trattativa (SIGLA, DESCRIZIONE) VALUES
('D', 'Definitivo'),
('P', 'Prestito');

---- Operazione
--INSERT INTO operazione (ID_SQUADRA, ID_GIOCATORE, ID_TIPO_OPERAZIONE, ID_STAGIONE, DATA_CREAZIONE) VALUES
--(1, 1, 1, 1, CURRENT_TIMESTAMP),
--(2, 2, 2, 2, CURRENT_TIMESTAMP);

-- Competizione
INSERT INTO competizione (SIGLA, DESCRIZIONE) VALUES
('C', 'Campionato'),
('CDL', 'Coppa di Lega'),
('CL', 'Champions League');