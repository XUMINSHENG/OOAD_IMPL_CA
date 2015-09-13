to setup the Mysql connection pool
1. create a new glassfish server instance

2. place the jdbc connector jar to glassfish/domains/[domainName]/lib/ext

3. start glassfish instance from netBean

4. View Domain Admin Console -> Resource -> JDBC -> set up connection pool then Resource

5. Netbeans -> project broswer -> lib -> add lib -> UnitTestingLib
   -> right click -> edit -> add all jar in /lib/testing/*