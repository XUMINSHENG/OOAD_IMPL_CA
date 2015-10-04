CD %~dp0
CALL setenv.bat
SET CUR_DIR=%CD%
ant -f %CUR_DIR% -Dnb.internal.action.name=build -DforceRedeploy=false -Dbrowser.context=%CUR_DIR% dist