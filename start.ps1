#Write-Host "Starting JCOP_Simulator-JCOP4.7-R1.00.4-RC17..."
# start the simulator
#Start-Process "JCOP_Simulator-JCOP4.7-R1.00.4-RC17/win32/jcop.exe" -WindowStyle Hidden
# wait until it is reachable
#do {Write-Host "waiting for server..."sleep 2 } until(Test-NetConnection localhost -Port 8050 | ? { $_.TcpTestSucceeded })
#Write-Host "Launched JCOP_Simulator-JCOP4.7-R1.00.4-RC17"
Write-Host "Starting JRCP Wrapper WebSocket Server..."
# start the jc shell wrapper
Start-Process cmd.exe  -ArgumentList '/c java -jar .\app\app.jar' -Wait -PassThru -NoNewWindow
