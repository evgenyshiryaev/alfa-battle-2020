docker exec -i broker kafka-topics --create --topic RAW_PAYMENTS --bootstrap-server broker:9092 --replication-factor 1 --partitions 1

docker exec -i c7080ea3ebf7 kafka-console-producer --topic RAW_PAYMENTS --broker-list broker:9092 --property parse.key=true --property key.separator=":" < data.txt

docker exec -i broker kafka-consumer-groups --bootstrap-server broker:9092 --all-groups --all-topics --reset-offsets --to-earliest --execute