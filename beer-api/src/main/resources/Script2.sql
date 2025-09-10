DROP DATABASE IF EXISTS beer_db;

DROP DATABASE IF EXISTS postgres;

DROP SCHEMA IF EXISTS beer_db;

DROP SCHEMA IF EXISTS beer_db;

DROP TABLE beer_db.beer;

DROP TABLE beer_db.news;

DROP TABLE beer_db.recent;

CREATE DATABASE IF NOT EXISTS beer_db;

CREATE SCHEMA IF NOT EXISTS beer_db;

CREATE TABLE IF NOT EXISTS beer_db.beer (
    id BIGSERIAL PRIMARY KEY,
    version INT4,
    beer_name VARCHAR(255),
    beer_style INT2,
    display_type INT2,
    display_order INT2,
    upc VARCHAR(255),
    price FLOAT8,
    quantity_on_hand INT4,
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

INSERT INTO beer_db.beer (
    beer_name,
    beer_style,
    display_type,
    display_order,
    upc,
    price,
    quantity_on_hand,
    created_date,
    updated_date,
    version
) VALUES
(
    'Galaxy Cat',
    '7',
    '0',
    '1',
    '12356789',
    12.99,
    122,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Crank',
    '8',
    '1',
    '1',
    '12356780',
    11.99,
    392,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Sunshine City',
    '7',
    '2',
    '1',
    '12356781',
    13.99,
    144,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
	'Sierra Nevada',
	'8',
	'0',
	'2',
	'3498575',
	9.99,
	22,
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP,
	1
),
(
	'Founders All Day',
	'8',
	'2',
	'2',
	'1358538',
	13.99,
	76,
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP,
	1
),
(
	'Trillium Fort Point',
	'7',
	'0',
	'3',
	'358753',
	14.48,
	23,
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP,
	1
),
(
	'Dog Fish Head',
	'8',
	'2',
	'3',
	'358798',
	10.99,
	35,
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP,
	1
),
(
	'Ballast Point Sculpin',
	'8',
	'3',
	'1',
	'408504',
	12.99,
	136,
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP,
	1
);


CREATE TABLE IF NOT EXISTS beer_db.news (
    id BIGSERIAL PRIMARY KEY,
    version INTEGER,
    title VARCHAR(255),
    content TEXT,
    author VARCHAR(255),
    published_date VARCHAR(255),
    image_url VARCHAR(255),
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

INSERT INTO beer_db.news (
    title,
    content,
    author,
    published_date,
    image_url,
    created_date,
    updated_date,
    version
) VALUES
(
    'New Craft Beer Release',
    'Exciting new IPA launching this weekend! Our brewers have crafted a unique blend of citrus and pine hop flavors.',
    'John Brewer',
    '2024-02-20',
    'https://example.com/beer-image1.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Brewery Tour Updates',
    'We are now offering extended brewery tours on weekends. Come learn about our brewing process and taste our latest creations!',
    'Sarah Miller',
    '2024-02-21',
    'https://example.com/brewery-tour.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
)

CREATE TABLE IF NOT EXISTS beer_db.recent (
    id BIGSERIAL PRIMARY KEY,
    version INTEGER,
    title VARCHAR(255),
    content TEXT,
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

INSERT INTO beer_db.recent (
    title,
    content,
    created_date,
    updated_date,
    version
) VALUES
(
    'Latest Beer Release',
    'Check out our newest craft beer release - Galaxy Cat IPA',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Weekend Special',
    'Try our award-winning Crank Pale Ale at a special price',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Brewery News',
    'New brewery tour schedule now available',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
);