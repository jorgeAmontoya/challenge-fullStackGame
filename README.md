# challenge-fullStackGame

🧗‍♂️ Reto: challenge-fullStackGame


🚀 Dirigido a 👨‍💻 Sofka University.

👨🏻•💻 TEAM 👨🏻•💻

🎓 Jorge Anderson Montoya Orjuela.

🌋 Modelo de dominio  https://app.diagrams.net/#G1wm3QFJsyxpxeQsryGw-du8DoYD8w9u0-



Para lanzar el Back debe de tener en cuenta lo siguiente:
en docker descargar las imagenes de Rabbitmq y MongoDb y posteriormente correrlas.

docker run --name mongodb -d -p 27017:27017 mongo

docker run -d --restart always --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management


docker start mongodb

docker start rabbitmq


