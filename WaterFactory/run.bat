set b=%cd%
set dirpath=%~d0
call mvn clean
call mvn package

D:
cd D:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps
del  WaterFactory.war
rmdir /s /q WaterFactory
del  WaterFactory.war
rmdir /s /q WaterFactory
del  WaterFactory.war
rmdir /s /q WaterFactory

echo %dirpath%
%dirpath%

echo %b%
cd %b%
copy /y "target\WaterFactory.war" "D:\Program Files\Apache Software Foundation\Tomcat 8.0\webapps"
pause

