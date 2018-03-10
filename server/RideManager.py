import json
from urllib.parse import urlencode
from urllib.request import Request, urlopen

from .Driver import Driver
from .Query import Query
from .Ride import Ride
from .Rider import Rider
from .User import User


class RideManager:
    _instance = None

    @staticmethod
    def get_instance():
        if RideManager._instance is None:
            RideManager()
        return RideManager._instance

    def __init__(self):
        if RideManager._instance is not None:
            raise Exception("There is already an instance!")
        else:
            self._riders = {}
            self._drivers = {}
            RideManager._instance = self

    def add_user(self, start, dest, isDriver, seats, vehicle, endTime, id):
        if isDriver:
            self._drivers[id] = User(start, dest, isDriver, seats, vehicle, endTime, id)
            # should work
        else:
            self._riders[id] = User(start, dest, isDriver, seats, vehicle, endTime, id)

    def get_user(self, id):
        if id in self._riders:
            return self._riders.get(id)
        if id in self._drivers:
            return self._drivers.get(id)
        else:
            return None

    def find_rides(self):
        query = self.create_query()
        response = self.send_request(query)
        self.parse_response(response)

    def create_query(self):
        drivers = set()
        riders = set()
        for driver in self._drivers:
            drivers.add(Driver(driver.start, driver.dest, driver.time, driver.seats))
        for rider in self._riders:
            riders.add(Rider(rider.start, rider.time))
        return Query(drivers, riders)

    def send_request(self, query):
        url = 'https://api.routific.com/v1/vrp/post'
        headers = {"Content-Type": "application/json",
                   "Authorization": "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWE0MmQwMjI0MTNkZjE3MGQ2MmU5YTUiLCJpYXQiOjE1MjA3MDg4NjZ9.Oq9hYvFMDhJkU34tZ5Skf0gyIKaF8Wk2cg5YTYNywME"}
        request = Request(url, data=urlencode(query).encode(), headers=headers)
        return urlopen(request).read().decode()

    def parse_response(self, response):
        parsed_json = json.load(response)
        solution = parsed_json["solution"]
        for name, route in solution.iteritems():
            ride = Ride(self._drivers[name])
            for stop in route:
                ride.stops.append([stop["location_name"], stop["arrival_time"]])
                ride.end_time = stop["arrival_time"]
                if ("end" or "start") not in stop["location_id"]:
                    self._riders[stop["location_id"]].ride = ride
