from roomba.create2 import Create2
import time

r = Create2()
r.start()
r.safe()
time.sleep(2)
user_input = raw_input("Enter a command:")
while(user_input != "quit"):
	
	if(user_input == "forward"):
		r.straight(50)
		time.sleep(0.1)
		r.drive(0,0)
	elif(user_input == "left"):
		r.counterclockwise(50)
		time.sleep(0.1)
		r.drive(0,0)
	elif(user_input == "back"):
		r.straight(-50)
		time.sleep(0.1)
		r.drive(0,0)
	elif(user_input == "right"):
		r.clockwise(50)
		time.sleep(0.1)
		r.drive(0,0)
	else:
		print("Please enter, \"left\", \"right\", \"forward\", or \"backward\"")

	user_input = raw_input("Enter a command:")
r.drive(0,0)
r.stop()
