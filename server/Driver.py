class Driver:

    def __init__(self, start_location, end_location, shift_end, capacity):
        self.start_location = start_location
        self.end_location = end_location
        self.shift_start = shift_end - 1
        self.shift_end = shift_end
        self.capacity = capacity
