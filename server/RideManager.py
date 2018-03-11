import json
from urllib.request import Request, urlopen

from .Driver import Driver
from .Location import Location
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
            self.riders = {200: User(Location("3780 Arbutus", 49.2474624, -123.1532338),
                                     Location("6133 University", 49.2664779, -123.2520534),
                                     False, None, None, 9),
                           300: User(Location("800 Robson", 49.2819229, -123.1211844),
                                      Location("6133 University", 49.2664779, -123.2520534),
                                      False, None, None, 9)}
            self.drivers = {400: User(Location("6800 Cambie", 49.227107, -123.1163085),
                                      Location("6133 University", 49.2664779, -123.2520534),
                                      True, 4, "911 DAFUZZ", 9),
                            }
            self.rides = []
            RideManager._instance = self
            self.find_rides()

    def add_user(self, start, dest, isDriver, seats, vehicle, endTime, id):
        if isDriver:
            self.drivers[id] = User(start, dest, isDriver, seats, vehicle, endTime)
        else:
            self.riders[id] = User(start, dest, isDriver, seats, vehicle, endTime)
        self.find_rides()

    def get_user(self, id):
        if id in self.riders:
            return self.riders.get(id)
        if id in self.drivers:
            return self.drivers.get(id)
        else:
            return None

    def find_rides(self):
        query = self.create_query()
        response = self.send_request(query)
        self.parse_response(response)

    def create_query(self):
        drivers = {}
        riders = {}
        for key, driver in self.drivers.items():
            drivers[key] = Driver(driver.start, driver.dest, driver.endTime, driver.seats)
        for key, rider in self.riders.items():
            riders[key] = Rider(rider.start, rider.endTime)
        return Query(riders, drivers)

    def json_repr(self, obj):
        """Represent instance of a class as JSON.
        Arguments:
        obj -- any object
        Return:
        String that reprent JSON-encoded object.
        """

        def serialize(obj):
            """Recursively walk object's hierarchy."""
            if isinstance(obj, (bool, int, float, str)):
                return obj
            elif isinstance(obj, dict):
                obj = obj.copy()
                for key in obj:
                    obj[key] = serialize(obj[key])
                return obj
            # elif isinstance(obj, set):
            #     return [serialize(item) for item in obj]
            elif isinstance(obj, list):
                return [serialize(item) for item in obj]
            elif isinstance(obj, tuple):
                return tuple(serialize([item for item in obj]))
            elif hasattr(obj, '__dict__'):
                return serialize(obj.__dict__)
            else:
                return repr(obj)  # Don't know how to handle, convert to string

        return json.dumps(serialize(obj))

    def send_request(self, query):
        url = 'https://api.routific.com/v1/vrp'
        headers = {"Content-Type": "application/json",
                   "Authorization": "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWE0MmQwMjI0MTNkZjE3MGQ2MmU5YTUiLCJpYXQiOjE1MjA3MDg4NjZ9.Oq9hYvFMDhJkU34tZ5Skf0gyIKaF8Wk2cg5YTYNywME"}
        request = Request(url, data=self.json_repr(query).encode(), headers=headers)
        return urlopen(request).read().decode()

    def parse_response(self, response):
        parsed_json = json.loads(response)
        solution = parsed_json["solution"]
        for name, route in solution.items():
            ride = Ride(int(name))
            for stop in route:
                ride.stops.append([stop["location_name"], stop["arrival_time"]])
                ride.end_time = stop["arrival_time"]
                if "start" not in stop["location_id"] and "end" not in stop["location_id"]:
                    self.riders.get(int(stop["location_id"])).ride = ride
            self.drivers.get(int(name)).ride = ride
            self.rides.append(self.json_repr(ride))
