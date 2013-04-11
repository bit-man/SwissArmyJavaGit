#!/bin/bash

__baseFolder="$HOME/git"
__javaGitHome=`dirname $0`/../
__reportBaseFolder="${__javaGitHome}/target/test-reports"

for __gitVersion in `ls ${__baseFolder}`; do
	
	echo ">>>>>>>>>>"
    echo ">>>>>>>>>> Running test for GIT version ${__gitVersion}"
    echo ">>>>>>>>>>"

    mvn surefire-report:report -Djavagit.test.gitpath="${__baseFolder}/${__gitVersion}/bin"

    __reportFolder=${__reportBaseFolder}/${__gitVersion}
    rm -Rf ${__reportFolder}
    mkdir -p  ${__reportFolder}
    cp -R  ${__javaGitHome}/target/surefire-reports/* ${__reportFolder}
done


echo ">>>>>>>>>>"
echo ">>>>>>>>>> TEST results can be found under folder ${__reportBaseFolder}"
echo ">>>>>>>>>>"
