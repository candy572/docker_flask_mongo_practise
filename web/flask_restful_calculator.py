import logging,datetime
from logging.handlers import RotatingFileHandler
from flask import Flask,jsonify,request
from flask_restful import Api,Resource
from pymongo import MongoClient
from random import randint


app = Flask(__name__)
api = Api(app)

client = MongoClient("mongodb://db:27017")
app.logger.info("above is the database names")
mydb = client["endusers"]
mycoll = mydb["endusercoll"]

@app.before_request
def before_request():
    app.logger.info("request came at ==> {}".format(str(datetime.datetime.now().isoformat())))

@app.after_request
def after_request(response):
   timestamp = datetime.datetime.now().isoformat()
   app.logger.info("url:{} payload:{}".format(request.url,request.data))
   app.logger.info("output is => {}".format(str(response.get_json())))
   app.logger.info("req ended at ==> {} {} {} {} {} {}".format(timestamp,request.remote_addr,request.method,request.scheme,request.full_path,response.status))
   return response

class Visit(Resource):
    def get(self):
        output = ""
        current_user_count = mycoll.find({})[0]['users']
        next_count = current_user_count + 1
        mycoll.update({}, {"$set":{'users' : next_count}})
        for x in mycoll.find({}):
            output = output + str(x)
        return jsonify(output)

class Register(Resource):
    outputjson = ""
    def post(self):
       inputjson  = request.get_json()
       userName   = inputjson['username']
       passWord   = inputjson['password'] + str(randint(500,5000))
       mycoll.insert({"username" : userName , "password" : passWord})
       self.outputjson = {"status" : 200}
       return jsonify(self.outputjson)
    def get(self):
       for x in mycoll.find({}):
          self.outputjson = self.outputjson + str(x)
       return jsonify(self.outputjson) 

class Add(Resource):
    def post(self):
        inputjson = request.get_json()
        outputjson = {"output" : int(inputjson['x']) + int(inputjson['y'])}
        return jsonify(outputjson)
    def get(self):
        return "i am in get of add"
    def put(self):
        return "i am in put of add"
    def delete(self):
        return "i am in delete of add"

class Substract(Resource):
    def post(self):
        inputjson = request.get_json()
        outputjson = { "output" : int(inputjson["x"]) - int(inputjson["y"]) }
        return jsonify(outputjson)
    def get(self):
        outputjson = { "status"  : 200 , "message" : "get request in the substract"}
        return jsonify(outputjson)

api.add_resource(Add,"/add")
api.add_resource(Substract,"/substract")
api.add_resource(Visit,"/visit")
api.add_resource(Register,"/register")

if __name__ == '__main__':
    formatter = logging.Formatter("[%(asctime)s] {%(pathname)s:%(lineno)d} %(levelname)s - %(message)s")
    handler = RotatingFileHandler('./flask_restful.log', maxBytes=100000000, backupCount=1)
    handler.setLevel(logging.DEBUG)
    handler.setFormatter(formatter)
    app.logger.addHandler(handler)
    app.logger.setLevel(logging.DEBUG)
    app.logger.warning('A warning occurred (%d apples)', 42)
    app.logger.error('An error occurred')
    app.logger.info('Info')
    app.logger.debug('this is debug message modified')
    app.logger.info("db names {}".format(str(client.list_database_names())))
    app.run(host='0.0.0.0',debug=True)

