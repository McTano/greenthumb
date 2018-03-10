class Rider:

    def __init__(self, location, time):
        self.location = location
        self.start = "{}:00".format(time - 1)
        self.end = "{}:00".format(time)
        self.duration = 2
        self.load = 1
