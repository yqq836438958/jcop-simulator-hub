FROM openjdk:11-windowsservercore

# copy required files into the image

# card simulator
COPY JCOP_Simulator-JCOP4.7-R1.00.4-RC17 /JCOP_Simulator-JCOP4.7-R1.00.4-RC17
# wrapper application
COPY target/jcshellWrapper-1.0.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
# start script
COPY start.ps1 /start.ps1

# call the start script
CMD .\start.ps1
