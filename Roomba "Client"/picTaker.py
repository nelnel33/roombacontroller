from foscam.foscam	import camera_factory

num = 0
camera = camera_factory()
while True:
	snap = camera.snapshot()
	path = "snaps/snap%d.jpg" %  num
	with open(path, "w") as outfile:
		outfile.write(snap)
		num+=1

