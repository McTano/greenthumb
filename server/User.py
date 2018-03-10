class User:
    def __init__(self, start, dest, isDriver, seats, vehicle, endTime):
        self.start = start
        self.dest = dest
        self.isDriver = isDriver
        self.seats = seats
        self.vehicle = vehicle
        self.ride = None
        self.endTime = endTime

    def getId(self):
        return self.id

    def getDest(self):
        return self.dest

    def getStart(self):
        return self.start

    def isDriver(self):
        return self.isDriver

    def getVehicle(self):
        return self.vehicle

    def getEndTime(self):
        return self.endTime

    def setRide(self, ride):
        self.ride = ride

    def getRide(self):
        return self.ride