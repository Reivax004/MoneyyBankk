# Bienvenue sur MoneyyBankk!

Projet réalisé dans le cadre du module INF2.
MoneyyBankk est une application bancaire simplifiée permettant :

- la gestion des utilisateurs

- la création et la consultation de transactions

- le calcul de statistiques financières

- la communication asynchrone Producer / Consumer via JMS (ActiveMQ Artemis)

Un Consumer joue le rôle de conseiller bancaire pour traiter certains événements (création d’utilisateur, demande de prêt, etc.).

## Prérequis

Afin de fonctionner ce projet à besoin de :

- JDK 21
- Maven
- Docker


## Comment accéder au projet ?

Pour lancer l'application en local plusieurs étapes sont à effectuer.

### Build du projet 

```bash
mvn install
```

### Lancement des services (PostgreSQL + ActiveMQ Artemis)

```bash
docker compose up -d
```

### Lancement du Producer (API principale)

Lancer la classe Main du projet principal.

Message attendu

```bash
"Api server is starting on http://localhost:8080/api/"
```

### Lancement du Consumer (API principale)

Lancer la classe Main du projet Consumer.

Message attendu

```bash
"Api server is starting on http://localhost:8081/api/"
```

## Base de données

Nom de db : starterdb 
User : admin mdp : admin
Driver : PSQL (AWS Wrapper)

Au cas où Postman renvoit des erreurs 500, il faut créer la bdd starterdb ainsi que l'user admin/admin en cmd.


## Route HTTP principales

Base URL :

```bash
http://localhost:8080/api/
```

### Authentication

body : 
{
"email": "",
"password": ""
}

POST auth/login + body

Cela nous renvoit notre bearer token

### Utilisateurs

body :
  {
    "lastname": "",
    "firstname": "",
    "birthdate": "",
    "email": "",
    "password": ""
  }

Création : POST users/register + body

Modification : PUT users/id + body + bearer token

Suppresion : DELETE users/id + bearer token

Liste de tout les users : GET users/all + bearer token

Liste d'un user : GET users/id + bearer token

### Transactions

body : 
  {
    "id": "",
    "price": "",
    "date": "",
    "currency": "",
    "type": "",
    "user_id":""
  }

Recupérer toutes les transactions de son compte : GET /transactions/all + bearer token

Recupérer une transaction de son compte : GET /transactions/id + bearer token

Créer une transaction : POST /transactions/new + body + bearer token

Modifier une transaction PUT /transactions/id + body + bearer token

Supprimer une transaciton DELETE /transactions/id + bearer token


### Statistiques 

Récupérer les statistiques de son compte : GET /statistics/account

### Demande de prêt

body :
  {
    "amount": ""
  }

Faire une demande de prêt sur son compte au consumer : POST loans/request + body + bearer token

## Evolutions futures

Système de role

## Auteurs

- [ALIBERT Xavier](https://github.com/Reivax004) 
- [HUANG Steven](https://github.com/Steven200405) 
- [JIN Christine](https://github.com/JinChristine)
- [ORDONNEAU Guillaume](https://github.com/Qxillum)
