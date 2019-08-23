from flask import Flask
from flask import jsonify
from flask import request

#from flask import json
from requests import Request, Session
import requests
import json

app = Flask(__name__)
url_firebase = "https://fcm.googleapis.com/fcm/send"
url_backend_pulso = "http://amstdb.herokuapp.com/db/pulsos/"
url_backend_ambulancia = "http://amstdb.herokuapp.com/db/ambulancia/"

token_firebase = "AAAAzuewgHQ:APA91bHvpLOIy0vBTp6oEAJNRT_V12R4VoLSR-itmmODzM9xCWjcRlGankaI4oIg3XACy4wTqFfrN08QJUYyQ5V2PdB43QFUnVvIwBivuCERNfYdTeo6cuS2ngz4SWqRn-sbh7_Nuloj"
token_app = "cnJCxNoYodc:APA91bFvTekqLcL4lqgdDuTw-ggRHB2gLf0gFf4l-kIJz2CBnq--aeAH9iXtifnFiSf9MED1iCbn0F9lJQhCLtUF29bmZF15oFps6Tzy3sqEBbmTwsq89ciFkLGM1d_1rGOUEOe78mSk"

token_backend = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo2LCJ1c2VybmFtZSI6IkFsbGFuIiwiZXhwIjoxNTY1MjM1NzU0LCJlbWFpbCI6IiJ9.q3xnV0Fbg8gpSTqjx9iJhOZ7Frf40llfgHKuITqQWrw"
headers = {}
headers['Content-Type'] = "application/json"
headers['Cache-Control'] = "no-cache"
headers['Authorization'] = "key=" + token_firebase

headers_django = {}
headers_django["Authorization"] = "JWT " + token_backend

prioridades = {
    "Arritmia" : "Baja",
    "Hiperpirexia" : "Alta",
    "Presion arterial baja" : "Media",
    "Paro cardiaco" : "Alta",
    "Presion arterial alta" : "Media",
    "Sin señal" : "Baja",
    "Señal desconicida" : "Baja"
}

firebaseDestinity = {
    "to" : token_app
} 

class FirebaseRequest(json.JSONEncoder):
    def __init__(self, to, notification ):
        self.to = to
        self.notification = notification




@app.route('/sigFox', methods=['POST'])
def SigFox():
    data = request.get_json()

    id_pulso = data["pulso"]
    id_ambulancia = data["ambulancia"]
    print(url_backend_pulso + str(id_pulso))
    requestPulso = requests.get( url_backend_pulso + str(id_pulso), headers = headers_django)


    dataPulso = requestPulso.json()
    tipoPulso = dataPulso["nombre"]
    prioridad = ""
    if tipoPulso in prioridades:
        prioridad = prioridades[tipoPulso]
    else:
        prioridad = "Baja"

    if (prioridad == "Alta"):

        bodyFirebase = {
            "title" : "Alerta de {}!".format(tipoPulso),
            "body" : "La ambulancia {} acaba de detectar un(a) {} en este momento".format(id_ambulancia, tipoPulso)
        }
        jsonToSend = {
            "to" : token_app,
            "notification": bodyFirebase
        }

        s = requests.post( url_firebase, json = jsonToSend, headers = headers)

        print(s.status_code)
        return jsonify(StatusCode = str(s.status_code), Content = str(s.content))

    
    return jsonify(StatusCode = str(200), Content = str("Nada que reportar"))

@app.route('/prueba', methods=['POST'])
def prueba():
    data = request.get_json()
    bodyFirebase = {
        "title" : data["title"],
        "body" :data["body"]
    }
    jsonToSend = {
        "to" : token_app,
        "notification": bodyFirebase
    }

    s = requests.post( url_firebase, json = jsonToSend, headers = headers)
    return jsonify(StatusCode = str(s.status_code), Content = str(s.content))


app.run()

