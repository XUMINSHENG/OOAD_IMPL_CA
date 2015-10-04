rem Set environment
CD %~dp0
CALL ../setenv.bat
rem execute the sql script to create JDO tables
"%MYSQL_HOME%" --user=root --password=%MYSQL_ROOT_PASSWORD%< cleanup.sql
"%MYSQL_HOME%" --user=root --password=%MYSQL_ROOT_PASSWORD%< setup.sql
"%MYSQL_HOME%" --user=phoenix --password=password< createphoenix.sql
"%MYSQL_HOME%" --user=phoenix --password=password< increment1.sql
pause