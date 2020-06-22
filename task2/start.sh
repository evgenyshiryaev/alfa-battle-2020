#!/usr/bin/env bash

docker-compose down

sleep 5s

docker-compose up -d

sleep 5s

docker exec -i broker kafka-topics --create --topic RAW_PAYMENTS --bootstrap-server broker:9092 --replication-factor 1 --partitions 1

sleep 5s

docker exec -i broker kafka-console-producer --topic RAW_PAYMENTS --broker-list broker:9092 --property parse.key=true --property key.separator=":" < data.txt