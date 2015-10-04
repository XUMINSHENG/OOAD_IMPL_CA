CD %~dp0
CALL setenv.bat
SET CUR_DIR=%CD%
CALL antCmd.bat
PAUSE
javadoc -d %CUR_DIR%\docs -sourcepath %CUR_DIR%\src\java -subpackages sg.edu.nus.iss.phoenix