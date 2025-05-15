FROM postgres:14

ENV POSTGRES_USER=daa
ENV POSTGRES_PASSWORD=daa123
ENV POSTGRES_DB=vn_words

COPY init.sql /docker-entrypoint-initdb.d/
COPY vietnamese_words_with_index_73k.csv /docker-entrypoint-initdb.d/

EXPOSE 5432