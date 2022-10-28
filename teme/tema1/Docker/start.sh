#!/bin/bash

docker-compose up -d --build
id=`docker container ls | grep "dev-station" | cut -d " " -f1`
docker attach $id

# Atentie! Odata ce va atasati la un container, cand dati exit veti opri containerul respectiv.
# Daca vreti sa aveti mai multe terminale deschise in paralel in container, puteti folosi urmatoarea comanda:
# docker exec -it $id /bin/bash