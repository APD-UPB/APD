#!/bin/bash

docker-compose up -d --build

if [[ $? != 0 ]]
then
	echo "Nu s-a putut crea/porni containerul Docker"
	docker-compose down
	cd ..
	exit
fi

docker exec -w /apd/checker -it apd_container /apd/checker/checker.sh
docker-compose down
cd ..