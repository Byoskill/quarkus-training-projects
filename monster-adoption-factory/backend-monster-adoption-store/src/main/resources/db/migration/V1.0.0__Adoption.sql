--
-- Table structure for table `monster`
--

CREATE TABLE monster
(
    id          bigserial PRIMARY KEY,
    name        varchar(255) NOT NULL,
    description varchar(255)          DEFAULT NULL,
    age         int                   DEFAULT NULL,
    species     varchar(255)          DEFAULT NULL,
    image_url   varchar(255)          DEFAULT NULL,
    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Dumping data for table `monster`
--

INSERT INTO monster (name, description, age, species, image_url, created_at, updated_at)
VALUES ('Dracula', 'A vampire who lives in a castle in Transylvania.', 500, 'Vampire',
        'https://www.example.com/dracula.jpg', '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Frankenstein', 'A monster created from body parts.', 200, 'Frankenstein',
        'https://www.example.com/frankenstein.jpg', '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Wolfman', 'A werewolf who transforms during the full moon.', 100, 'Wolfman',
        'https://www.example.com/wolfman.jpg', '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Mummy', 'An ancient Egyptian mummy.', 3000, 'Mummy', 'https://www.example.com/mummy.jpg',
        '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Zombie', 'A reanimated corpse.', 50, 'Zombie', 'https://www.example.com/zombie.jpg', '2023-04-12 10:00:00',
        '2023-04-12 10:00:00'),
       ('Ghost', 'A spirit of a deceased person.', 1000, 'Ghost', 'https://www.example.com/ghost.jpg',
        '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Goblin', 'A small, mischievous creature.', 50, 'Goblin', 'https://www.example.com/goblin.jpg',
        '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Troll', 'A large, ugly creature that lives under bridges.', 1000, 'Troll', 'https://www.example.com/troll.jpg',
        '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Ogre', 'A large, cannibalistic creature.', 200, 'Ogre', 'https://www.example.com/ogre.jpg',
        '2023-04-12 10:00:00', '2023-04-12 10:00:00'),
       ('Cyclops', 'A one-eyed giant.', 300, 'Cyclops', 'https://www.example.com/cyclops.jpg', '2023-04-12 10:00:00',
        '2023-04-12 10:00:00');