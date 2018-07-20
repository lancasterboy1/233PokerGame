SET JAVAPATH=C:\Program Files\Java
SET JAVAVERSION=1.8.0_171
CALL javac -cp "%JAVAPATH%\jre%JAVAVERSION%\lib\ext\jfxrt.jar" -d . Master.java
CALL java Master
pause