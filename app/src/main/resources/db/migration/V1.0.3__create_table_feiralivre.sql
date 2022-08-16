CREATE TABLE feira_app.feira_livre
(
    id integer NOT NULL,
    nome character varying(36) NOT NULL,
    distrito_id integer NOT NULL,
    sub_prefeitura_id integer NOT NULL,
    latitude integer,
    longitude integer,
    setor_censo bigint,
    area_ponderacao bigint,
    regiao5 character varying(12) NOT NULL,
    regiao8 character varying(12),
    registro character varying(6) NOT NULL,
    logradouro character varying(36),
    numero integer,
    bairro character varying(36) NOT NULL,
    referencia character varying(36),
    criado_em time without time zone NOT NULL,
    atualizado_em time without time zone NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT feira_livre_nome_un UNIQUE (nome),
    CONSTRAINT feira_livre_registro_un UNIQUE (registro),
    CONSTRAINT distrito_fk FOREIGN KEY (distrito_id)
        REFERENCES feira_app.distrito (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT sub_prefeitura_fk FOREIGN KEY (sub_prefeitura_id)
        REFERENCES feira_app.sub_prefeitura (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE IF EXISTS feira_app.feira_livre
    OWNER to postgres;

CREATE INDEX idx_bairro_feira ON feira_app.feira_livre(bairro);
CREATE INDEX idx_regiao5 ON feira_app.feira_livre(regiao5);

GRANT SELECT, INSERT, UPDATE, DELETE ON feira_app.feira_livre TO feirasp_user;