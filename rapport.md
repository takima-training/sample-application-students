#TP1
Commande pour build un docker `docker build -t 'tag'/'nom de projet' 'location'`
Commande pour lancer un docker  :` docker run 'nom de l'image'`
                        option  : -p X:X mappage de port
                                  -d mode détaché
                                  -network 'nom du network'


##Why should we run the container with a flag -e to give theenvironment variables ?

The flag -e permet de passer des variables d'environnements qui remplaceront celles existantes dans un fichier, permet ainsi de ne pas divulguer de mots de passe/login durant l'execution ou de laisser ses identifiants dans un fichier lisible par d'autre

##Why do we need a volume to be attached to our postgres container

On attache un volume pour permettre la persistance des données après un redémarrage / crash du container de la base de donnée
`docker run -v 'location du volume'`

##Why do we need a multistage build ?
``` 
FROM openjdk:11
#Build main java
COPY ./src/ /usr/src/ 
WORKDIR /usr/src/
RUN javac Main.java
 
FROM openjdk:11-jre
#Copy ressource from previous stage
COPY --from=0 /usr/src/Main.class /usr/src/Main.class
#run javacode with JRE
WORKDIR /usr/src/
CMD ["java","Main"]
```

On a besoin d'un build multistage pour se débarasser des dépendences nécéssaires au build après la fin de celui-ci

##Why do we need a reverse proxy ?

Avoir un reverse proxy permet d'aller récuperer les données de la part de plusieurs backend notamment pour l'équilibrage des charges

##Why is docker-compose so important ?

Docker-compose permet de configurer et monter plusieurs images docker et de tous les monter plutot et configurer que de les configurer et lancer une par une.
C'est un outil qui se situe au dessus de la couche docker 

```
version: '3.3'
services:
  backend:
    container_name: backend
    build: ./sample-application-backend
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/SchoolOrganisation
    networks:
      - app-network
    depends_on:
      - database

  database:
    container_name: database
    image: postgres:12.0-alpine
    networks:
      - app-network
    environment:
      - POSTGRES_PASSWORD=takimapass
      - POSTGRES_USER=takima
      - POSTGRES_DB=SchoolOrganisation

  frontend:
    container_name: frontend
    build: ./sample-application-frontend
    ports:
      - "80:80"
    networks:
      - app-network

volumes:
  my_db_volume:
    driver: local

networks:
  app-network:
```

#TP2



travic CI/jenkins etc permet une orchestration de projet (exemple build, execution , déploiement sur un simple push ou un merge)
Chaque technologie à ses avantages et inconvénients et nécéssite de les connaitre/apprendre pour comparer

mvn clean verify permet de , supprimer le cache d'un précédent build (qui pourrait causer des problème en cas de nouveau build avec des parties manquantes car déjà construites) et de construire l'application java  (validation , compilation etc )avant de lancer des test d'intégrations.

npm run test 
permet dans de manièrer similaire de lancer des test si ils existent ( ici pour vuejs par exemple)

Unit tests ? Component test ?
Les tests unitaires et de composants permettent de verifier le bon fonctionnement de chaque fonction de manière isolé et de modules /fonctions.

What are testcontainers?
Les testcontainers sont des containers très legers permettant la réalisation des test 

What does the default java and node.js travis images do?

les images par défault java et node.js de travis 
fournissent un environnement avec les dépendances nécéssaire à l'execution de ces languages
```
git:
  depth: 5

jobs:
  include:
    - stage: Build and Test Java
      language: java
      jdk: oraclejdk11
      before_script:
        - cd sample-application-backend

    - stage: Build and Test Nodejs
      language: node.js
      node_js: "12.20"
      before_script:
        - cd sample-application-frontend
cache:
  directories:
  - "$HOME/.m2/repository"
  - "$HOME/.npm"
```

##Why do we need a develop branch ?

On a besoin d'une branche develop pour test les builds et permettre le build/test et développement


##Secured variables, why ?

On doit avoir des variables sécurisés afin d'eviter que nos informations de login /mot de passe ou variables sensibles soit disponible dans des fichiers qui sont dans des dépots publics


##For what purpose(creating and pushing on dockerhub) ?

L'interet et le but est de créer et push depuis n'importe quel endroit + mise a jour des images docker hub. 
```
"$DOCKER_HUB_Password" | docker login -u "$DOCKER_HUB_Username" --password-stdin`
docker push  kaisowaka/samplebackend
```


##Working quality gate

La vérification sur sonarcloud permet de repérer les erreurs ou mauvaises pratiques dans le code (code smelling , répétition inutiles) et possible failles de sécurité


##Splitted pipeline into jobs
La séparation des pipeline permet de trigger differents parties en fonction de la branche qu'on run afin de ne pas relancer toutes les étapes (build, tests etc) à chaque modification/push

#TP3
##What’s up with the /api/actuator endpoint? What could it be used for?

actuator permet la récupération d'informations et de verifier les informations de l'application.
il permet donc le monitoring 



##Checkpoint: document your server main information
ansible all -i inventories/setup.yml -m ping


```
all:
 vars:
  ansible_user: centos
  ansible_ssh_private_key_file: "C:\Users\foure\OneDrive\Bureau\ansible\ansible\id_rsa"
 children:
  prod:
   hosts: raphael.fourel.takima.cloud 
```



##Document your docker_container tasks configuration.


```
- hosts: all
  gather_facts: false
  become: yes
    
  tasks:
  - name: Test connection
    ping:

  roles:
    - docker
    - network
    - database
    - app
    - proxy
```

Configuration des 
##Front up and running.


##Checkpoint: Full CI/CD pipeline in action.