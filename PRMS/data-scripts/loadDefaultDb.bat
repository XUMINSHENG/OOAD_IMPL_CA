rem Set environment
CD %~dp0
CALL ../setenv.bat
rem execute the sql script to create JDO tables
run '%MYSQL_HOME%\bin\mysql' --user=root --password=%MYSQL_ROOT_PASSWORD%< cleanup.sql
run '%MYSQL_HOME%\bin\mysql' --user=root --password=%MYSQL_ROOT_PASSWORD%< setup.sql
run '%MYSQL_HOME%\bin\mysql' --user=phoenix --password=password< createphoenix.sql
run '%MYSQL_HOME%\bin\mysql' --user=phoenix --password=password< increment1.sql
pause