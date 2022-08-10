CREATE TABLE feira_app.distrito
(
    id uuid NOT NULL,
    identificador_externo character varying(32),
    nome character varying(36) NOT NULL,
    criado_em time without time zone NOT NULL,
    atualizado_em time without time zone NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT distrito_external_identifier_un UNIQUE (identificador_externo)
);

ALTER TABLE IF EXISTS feira_app.distrito
    OWNER to postgres;

GRANT SELECT, INSERT, UPDATE, DELETE ON feira_app.distrito TO feirasp_user;