# docker_flask_mongo_practise
Sample project for the dockerised python flask application which uses the mongo db

#We need to have the docker to use this application. After installing the docker & docker-compose run the below commands

sudo docker-compose build && 
sudo docker-compose up

#Sample Commands for testing

GET ==> curl http://localhost:5000/register   && POST ==> 
curl -H "Content-Type:application/json" -d@login.json http://localhost:5000/register




