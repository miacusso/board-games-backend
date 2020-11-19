CREATE TABLE games
(
    id integer NOT NULL AUTO_INCREMENT,
    name CHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE players
(
    id integer NOT NULL AUTO_INCREMENT,
    name CHAR(255),
    game integer,
    PRIMARY KEY (id)
);

CREATE TABLE results
(
    id integer NOT NULL AUTO_INCREMENT,
    date date,
    winner integer,
    game integer,
    PRIMARY KEY (id)
);

INSERT INTO games (id, name) VALUES (1, 'TIC TAC TOE'), (2, 'FOUR IN LINE');

INSERT INTO players (name, game) VALUES ('X', 1), ('O', 1), ('RED', 2), ('BLUE', 2);