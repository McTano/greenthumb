#generated after routific update
class Ride:
    def __init__(self, id, driver, passengers, stops, endTime):
        self.id = id
        self.driver = driver
        #self.passengers = passengers
        self.stops = stops
        self.endTime = endTime

        self.totalTime #this will be calculated via routific

    def getDriver(self):
        return self.driver

    def getId(self):
        return self.id

    def getStops(self):
        return self.stops

    def getEndTime(self):
        return self.endTime

    def totalTime(self):
        return self.totalTime