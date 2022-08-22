# API de Gestao de Feira Livre da cidade de Sao Paulo

## Pre requisitos

- Docker: [saiba como instalar](https://docs.docker.com/engine/install/){:target="_blank"}
- Docker-Compose: [saiba como instalar](https://docs.docker.com/compose/install/){:target="_blank"}
- Bash: [utiliza Windows? Saiba como usar no seu sistema](https://www.thewindowsclub.com/how-to-run-sh-or-shell-script-file-in-windows-10){:target="_blank"}

## Iniciando a aplicacao

- Passo 1: Faca o download ou clone do repositorio em um diretorio local
- Passo 2: Via seu terminal de preferencia, execute o seguinte comando na pasta que voce importou no passo anterior.
  Este passo pode levar alguns minutos.

```shell
[user@host feira-sp]$ docker-compose up --build -d
```

- Passo 3: Verifique se todos os servicos estao disponiveis. Como exemplo abaixo:

```shell
[user@host feira-sp]$ docker ps
CONTAINER ID   IMAGE                  COMMAND                  CREATED          STATUS          PORTS                                       NAMES
1a3d181cfe69   feirasp:latest         "java org.springfram…"   5 seconds ago   Up 5 seconds   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   app
d1449a7c74cd   postgres:14.5-alpine   "docker-entrypoint.s…"   6 seconds ago   Up 5 seconds   0.0.0.0:5432->5432/tcp, :::5432->5432/tcp   db

```

## Acessando documentacao da API

Esta API segue a especificacao Open API 3. Acessando o link abaixo voce podera conferir todos os recursos disponiveis.

[URL da especificacao OpenAPI 3](http://localhost:8080/swagger-ui/index.html#/feira-api){:target="_blank"}

## Importanto a massa de dados

Para importar os dados das feiras mais recentes disponibilizados pela Prefeitura de SP pelo [link](http://www.prefeitura.sp.gov.br/cidade/secretarias/upload/chamadas/feiras_livres_1429113213.zip){:target="_blank"}.
Devera usar seu terminal Bash e executar o script "**import-data**" que se encontra dentro da pasta *[scripts](scripts)*

```shell
[user@host feira-sp]$ cd scripts && ./import-data

```

## Saida de logs

Os logs da aplicacao estao configurados para serem gravados de forma estruturada em arquivos. Os mesmos irao ser encontrados no diretorio *[logs](logs)* com o seguinte nome "**feirasp-app-json.log**"

## Relatorio de cobertura de testes

Para acessar o relatorio de cobertura de testes, basta executar o seguinte comando:

```shell
[user@host feira-sp]$ cd app && ./mvnw clean verify

```

O resultado da execucao estara disponivel dentro da pasta 

```shell
[user@host feira-sp]$ ls ./app/target/jacoco-report
br.com.samuka182.feirasp             br.com.samuka182.feirasp.domain      br.com.samuka182.feirasp.service  jacoco-resources
br.com.samuka182.feirasp.api         br.com.samuka182.feirasp.entities    index.html                        jacoco-sessions.html
br.com.samuka182.feirasp.controller  br.com.samuka182.feirasp.exceptions  jacoco.csv                        jacoco.xml

```

Com isso basta abrir o arquivo **app/target/jacoco-report/index.html** no seu browser de preferencia para ter acesso ao relatorio.