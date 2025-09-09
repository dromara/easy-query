CREATE TABLE IF NOT EXISTS t_def_table (
    id VARCHAR (100) PRIMARY KEY,
    user_name VARCHAR (100),
    nickname VARCHAR (128),
    enable BOOLEAN,
    score DECIMAL (20, 2),
    mobile VARCHAR (32),
    avatar VARCHAR (256),
    number Integer,
    status Integer,
    created DATETIME,
    options VARCHAR (2048)
    );

CREATE TABLE IF NOT EXISTS t_def_table_left1 (
    id VARCHAR (100) PRIMARY KEY,
    def_id VARCHAR (100),
    user_name VARCHAR (100),
    nickname VARCHAR (128),
    enable BOOLEAN,
    score DECIMAL (20, 2),
    mobile VARCHAR (32),
    avatar VARCHAR (256),
    number Integer,
    status Integer,
    created DATETIME,
    options VARCHAR (2048)
    );