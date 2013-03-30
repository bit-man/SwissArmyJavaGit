#!/bin/bash

__PGM=`basename $0`
__baseFolder="$HOME/sw/git"

__gitVersion=""

function showMsg() {
    echo "$__PGM: $1"
}

function showError() {
    showMsg "ERROR: $1"
}

function showHelp() {
    cat <<EOH

    usage:
        $__PGM --version gitVersion  [--base folderPath]

    version : GIT version to install
    base : base folder for GIT source code
           (default : ${__baseFolder})

EOH
}

function checkFolders() {
    if [[ ! -d ${__baseFolder} ]]; then
        showError "Can't find base folder ${__baseFolder}"
        exit -2
    fi

    if [[ ! -d ${__gitFolder} ]]; then
        showError "Can't find GIT folder ${__gitFolder}"
        exit -3
    fi
}

function checkArgs() {
    if [[  "${__gitVersion}" == "" ]]; then
        showError "No GIT version"
        showHelp
        exit -4
    fi
}

while [[ ! -z $1 ]]; do
    if [[ "$1" == "--version" ]]; then
        shift
        __gitVersion=$1
    elif [[ "$1" == "--base" ]]; then
        shift
        __baseFolder=$1
    else
       showErr "Unknown option $1"
       usage
       exit -1
    fi
    shift
done


checkArgs

__gitFolder="${__baseFolder}/git-${__gitVersion}"
__installfolder="$HOME/git/git-${__gitVersion}"
checkFolders

mkdir -p  ${__installfolder}
cd ${__gitFolder}
make prefix=${__installfolder} install
