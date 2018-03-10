from server import server
from server import User
from server import Ride



class RideManager:
    _instance = None

    @staticmethod
    def getInstance():
        if RideManager._instance == None:
            RideManager()
        return RideManager._instance

    def _init_(self):
        if RideManager._instance != None:
            raise Exception("There is already an instance!")
        else:
            RideManager._instance = self

    # lat lon, address, datetime
    def addRequest(self, lat, lon, address, dateTime, isDriver):

        usr = User()



    def findRides(self):
        server()






