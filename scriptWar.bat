cd %SIRH%
cd ../../
cd workspaceSTS
cd sirh-ws
call mvn clean
call mvn install
call mvn install -DcreateChecksum=true
