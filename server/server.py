from flask import Flask
from flask import request
from flask import json

from server import RideManager

app = Flask(__name__)

rideManager = RideManager.getInstance()

@app.route("/")
def hello():
	return "Hello, Squirrels!"

@app.route("/users/new", methods=['PUT'])
def addUser():
	rideManager.addUser(request.user)

@app.route("/rides", methods=['GET'])
def findRides():
	return "get your own damn ride"