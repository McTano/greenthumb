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
	rideManager.add_user(request.json.user)
	return request.json

@app.route("/users")
def get_user():
	return request.json
	# rideManager.get_user(request.json.user_id)

# @app.route("/rides", methods=['GET'])
# def findRides():
# 	# return "get your own damn ride"