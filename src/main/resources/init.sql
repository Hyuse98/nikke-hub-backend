CREATE TABLE nikke (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) UNIQUE NOT NULL,
    dollId INT REFERENCES doll(id)
);

CREATE TABLE nikke (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) UNIQUE NOT NULL,
    core INT NOT NULL,
    attraction INT NOT NULL,
    skill1Level INT NOT NULL,
    skill2Level INT NOT NULL,
    burstLevel INT NOT NULL,
    rarity VARCHAR(255) NOT NULL,
    ownedStatus VARCHAR(255) NOT NULL,
    burstType VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    weapon VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    cube VARCHAR(255) NOT NULL,
    dollId INT REFERENCES doll(id)
);

CREATE TABLE gear (
    id SERIAL PRIMARY KEY,
    nikkeId INT REFERENCES nikke(id) ON DELETE CASCADE,
    type VARCHAR(255) NOT NULL,
    tier VARCHAR(255) NOT NULL,
    level INT NOT NULL,
    status1 VARCHAR(255),
    status2 VARCHAR(255),
    status3 VARCHAR(255),
    value1 DOUBLE PRECISION,
    value2 DOUBLE PRECISION,
    value3 DOUBLE PRECISION
);

CREATE TABLE doll (
    id SERIAL PRIMARY KEY,
    rarity VARCHAR(255) NOT NULL,
    level INT NOT NULL
);
