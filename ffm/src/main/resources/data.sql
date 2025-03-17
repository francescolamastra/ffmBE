INSERT INTO stadio (ID, LIVELLO, COEFFICIENTE, COMPENSO_VITTORIA, COMPENSO_PAREGGIO, BONUS, MANUTENZIONE) VALUES
(1, 1, 0.25, 3, 1, 1, 25),
(2, 2, 0.50, 6, 2, 1.5, 50);

-- Categoria
INSERT INTO categoria (ID, DESCRIZIONE, SIGLA) VALUES
(1, 'Categoria 1', 'A'),
(2, 'Categoria 2', 'B'),
(3, 'Categoria 3', 'C')
;

--Giocatore
INSERT INTO giocatore (ID_FANTAGAZZETTA, NOME, RUOLO) VALUES
(1, 'Giocatore 1', 'P'),
(2, 'Giocatore 2', 'A');

-- Nazione
INSERT INTO nazione (ID, DESCRIZIONE, SIGLA) VALUES
(1, 'Italia', 'ITA'),
(2, 'Euro Nord', 'EN'),
(3, 'Francia', 'FRA');

-- Squadra
INSERT INTO squadra (ID_NAZIONE, ID_CATEGORIA, NOME, ID_STADIO, DATA_CREAZIONE) VALUES
(1, 1, 'Squadra 1', 1, CURRENT_TIMESTAMP),
(2, 2, 'Squadra 2', 2, CURRENT_TIMESTAMP);

-- Stagione
INSERT INTO stagione (ID, ANNO_INIZIO, ANNO_FINE) VALUES
(1, 2021, 2022),
(2, 2022, 2023);

-- Utente
INSERT INTO utente (NOME, COGNOME, TELEFONO, EMAIL, DATA_CREAZIONE) VALUES
('Mario', 'Rossi', '340123456', 'test1@mail.it', CURRENT_TIMESTAMP),
('Luigi', 'Bianchi', '340123456', 'test1@mail.it', CURRENT_TIMESTAMP);

-- Tipo Operazione
INSERT INTO tipo_operazione (ID, SIGLA, DESCRIZIONE) VALUES
(1, 'A', 'Acquisto'),
(2, 'S', 'Svincolo');

-- Operazione
INSERT INTO operazione (ID_SQUADRA, ID_GIOCATORE, ID_TIPO_OPERAZIONE, ID_STAGIONE, DATA_CREAZIONE) VALUES
(1, 1, 1, 1, CURRENT_TIMESTAMP),
(2, 2, 2, 2, CURRENT_TIMESTAMP);