CREATE TABLE public.games
(
    id integer NOT NULL DEFAULT nextval('games_id_seq'::regclass),
    name text COLLATE pg_catalog."default"
);

CREATE TABLE public.players
(
    id integer NOT NULL DEFAULT nextval('players_id_seq'::regclass),
    name text COLLATE pg_catalog."default",
    game integer
);

CREATE TABLE public.results
(
    id integer NOT NULL DEFAULT nextval('results_id_seq'::regclass),
    date date,
    winner integer,
    game integer
);

INSERT INTO games (id, name) VALUES (1, 'TIC TAC TOE'), (2, 'FOUR IN LINE');

INSERT INTO players (name, game) VALUES ('X', 1), ('O', 1), ('RED', 2), ('BLUE', 2);