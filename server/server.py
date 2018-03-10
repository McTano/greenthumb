from .RideManager import RideManager

from flask import Flask
from flask import request
from flask import json

app = Flask(__name__)

rideManager = RideManager.get_instance()

@app.route("/")
def hello():
	return "Hello, Squirrels!"

@app.route("/users/new", methods=['PUT'])
def add_user():
	user = request.json['user']
	start = user['start']
	dest = user['dest']
	isDriver = user['isDriver']
	seats = user['seats']
	vehicle = user['vehicle']
	endTime = user['endTime']
	id = user['id']
	rideManager.add_user(
		start,
		dest, 
		isDriver,
		seats,
		vehicle,
		endTime,
		id)
	# return "putted"
	return json.dumps(request.json)

@app.route("/users")
def get_user():
	# return "getted"
	return json.dumps(request.json)
	# rideManager.get_user(request.json.user_id)

# @app.route("/rides", methods=['GET'])
# def findRides():
# 	# return "get your own damn ride"