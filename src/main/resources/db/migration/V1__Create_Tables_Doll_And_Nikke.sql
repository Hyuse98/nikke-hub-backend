-- Script para a criação da tabela 'nikke'
CREATE TABLE nikke (
    nikke_id SERIAL PRIMARY KEY,
    character_name VARCHAR(255) UNIQUE NOT NULL,
    core_level INTEGER NOT NULL,
    attraction INTEGER NOT NULL,
    skill_1_level INTEGER NOT NULL,
    skill_2_level INTEGER NOT NULL,
    skill_burst INTEGER NOT NULL,
    nikke_rarity VARCHAR(255) NOT NULL,
    owned VARCHAR(255) NOT NULL,
    burst_type VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    nikke_class VARCHAR(255) NOT NULL, -- "class" é uma palavra reservada, então é colocada entre aspas
    weapon VARCHAR(255) NOT NULL,
    cube VARCHAR(255) NULL,
    doll_nikke_id INTEGER NULL
);

-- Script para a criação da tabela 'doll'
CREATE TABLE doll (
    doll_id SERIAL PRIMARY KEY,
    rarity VARCHAR(255) NOT NULL,
    level INTEGER NOT NULL
);