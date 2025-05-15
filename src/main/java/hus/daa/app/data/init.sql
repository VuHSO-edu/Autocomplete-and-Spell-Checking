-- Create the table
CREATE TABLE IF NOT EXISTS words (
        id INT PRIMARY KEY,
        word VARCHAR
);

COPY words(id, word)
    FROM '/docker-entrypoint-initdb.d/vietnamese_words_with_index_73k.csv'
    WITH (FORMAT CSV, HEADER true);
