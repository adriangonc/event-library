<h1 align="center"> Event Library </h1>
<p align="center"> POC API Kotlin para estudo de tecnologias como. Eventos kafka, Reativo, REST, NoSql etc. </p>

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Stack
- Kotlin
- Spring Kotlin
- Kafka
- MongoDB

# Configuração kafka local
###### Start: Entrar no diretorio bin do kafka e executar o comando abaixo para iniciar o zookeper
./zookeeper-server-start.sh ../config/zookeeper.properties

Alterar propriedades do arquivo /config/server.properties adicionando as linhas abaixo ao listener do arquivo
listeners = PLAINTEXT://localhost:9092
auto.create.topics=false

###### Iniciar broker do kafka
./kafka-server-start.sh ../config/server.properties

###### Criar tópico
./kafka-topics.sh --create --topic library-events --replication-factor 1 --partitions 4 --bootstrap-server localhost:9092

###### Instanciar console producer
./kafka-console-producer.sh --broker-list localhost:9092 --topic library-events

###### Instanciar console producer com key (Kei garante o envio para a mesma partição)
./kafka-console-producer.sh --broker-list localhost:9092 --topic library-events --property "key.separator=-" --property "parse.key=true"

###### Instanciar console consumer
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic library-events --from-beginning

###### Instanciar console consumer com key
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic library-events --from-beginning -property "key.separator= - " --property "print.key=true"

###### Listar topicos
./kaf-topics.sh --bootstrap-server localhost:9092 --list