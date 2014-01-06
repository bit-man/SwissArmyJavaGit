#!/bin/bash

__baseFolder="$HOME/git"
__javaGitHome=`dirname $0`/../
__reportBaseFolder="${__javaGitHome}/target/test-reports"


function compile() {
    mvn clean package -DskipTests
    [[ $? -ne 0 ]] && exit -1
}

function versionsToTest() {
    if [[ -z $1 ]]; then
        echo `ls ${__baseFolder}`
    else
        echo $1
    fi
}

if [[ ! -z $1 && ! -d ${__baseFolder}/${1} ]]; then
    echo -e "\nVersion $1 does not exists under folder ${__baseFolder}\n"
    exit -1
fi

compile

for __gitVersion in  `versionsToTest $*`; do

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
