import paramiko
import time

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
info = open("info.txt",'r')
ssh.connect(info.readline().strip(),username=info.readline().strip(),password=info.readline().strip())
ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command("python textController.py")
time.sleep(5)
moves = open("moves.txt")
for line in moves:
	ssh_stdin.write(line)
ssh_stdin.write("quit\n")
