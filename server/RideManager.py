from .Driver import Driver
from .User import User
from .Rider import Rider
from .Query import Query

from urllib.parse import urlencode
from urllib.request import Request, urlopen


class RideManager:
    _instance = None
    _riders = set()
    _drivers = set()

    @staticmethod
    def get_instance():
        if RideManager._instance is None:
            RideManager()
        return RideManager._instance

    def __init__(self):
        if RideManager._instance is not None:
            raise Exception("There is already an instance!")
        else:
            RideManager._instance = self

    # lat lon, address, datetime
    def add_request(self, lat, lon, address, dateTime, isDriver):

        usr = User()

    def find_rides(self):
        query = self.create_query()
        response = self.send_request(query)

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
