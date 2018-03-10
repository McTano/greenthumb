from server.Driver import Driver
from server.User import User
from server.Rider import Rider


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

    def create_query(self):
        drivers = set()
        riders = set()
        for driver in self._drivers:
            drivers.add(Driver(driver.start, driver.dest, driver.time, driver.seats))
        for rider in self._riders:
            riders.add(Rider(rider.start, rider.time))
