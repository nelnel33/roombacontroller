import paramiko

info = open("info.txt")
transport = paramiko.Transport((info.readline().strip()))
username = info.readline().strip()
password = info.readline().strip()
transport.connect(username = username, password = password)
sftp = paramiko.SFTPClient.from_transport(transport)
num = 0
while True:
	remote = '/home/pi/snaps/snap.jpg' 	
	local = '/home/rishab/snaps/snap.jpg'
	sftp.get(remote,local)
sftp.close()
transport.close()
