class User:
    def __init__(self, start, dest, hasCar, seats, vehicle, endTime, id):
        self.start = start
        self.dest = dest
        self.hasCar = hasCar
        self.seats = seats
        self.vehicle = vehicle
        self.id = id
        self.route = None
        self.endTime

    def getId(self):
        return self.id

    def getDest(self):
        return self.dest

    def getStart(self):
        return self.start

    def hasCar(self):
        return self.hasCar

    def getVehicle(self):
        return self.vehicle

    def getEndTime(self):
        return self.endTime

    def setRoute(self, route):
        self.route = route