to setup the Mysql connection pool

1. Netbeans -> project broswer -> lib -> add lib -> UnitTestLib
   -> right click -> edit -> add all jar in OOAD_Impl/PRMS/lib/testingLib/*
   
2. Copy the jdbc connector jar from OOAD_Impl/PRMS/lib to C:/ProgramFiles/glassfish-<version>/glassfish/lib

3. start glassfish instance from netBean

4. View Domain Admin Console -> Resource -> JDBC -> set up connection pool then Resource