CREATE TABLE monsters (
                          id bigserial PRIMARY KEY,
                          name varchar(255) NOT NULL,
                          description varchar(255) DEFAULT NULL,
                          image_url varchar(255) DEFAULT NULL,
                          monsterUUID varchar(255) DEFAULT NULL,
                          price  int DEFAULT NULL,
                          age int DEFAULT NULL,
                          location varchar(255) DEFAULT NULL,
                          monsterId  int DEFAULT NULL,
                          created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);