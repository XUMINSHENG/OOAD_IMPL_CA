CD %~dp0
CALL setenv.bat
SET CUR_DIR=%CD%
ant -f %CUR_DIR% -Dnb.internal.action.name=run -Ddirectory.deployment.supported=true -DforceRedeploy=false -Dnb.wait.for.caches=true -Dbrowser.context=%CUR_DIR% run