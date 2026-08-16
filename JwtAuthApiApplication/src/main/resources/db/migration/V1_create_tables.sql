CREATE TABLE app_user (
                         id BIGSERIAL PRIMARY KEY,
                         firstname VARCHAR(255) NOT NULL,
                         lastname VARCHAR(255) NOT NULL,
                         email VARCHAR(50) NOT NULL UNIQUE ,
                         password VARCHAR NOT NULL,
                         role VARCHAR NOT NULL,
                         createdAt TIMESTAMP NOT NULL DEFAULT NOW(),
                         updatedAt TIMESTAMP NOT NULL DEFAULT NOW()
);
