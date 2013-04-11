#!/bin/bash

__PGM=`basename $0`
__gitRepoHome="$HOME/sw/git/git-github"
__installFolderBase="$HOME/git/"
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
        $__PGM --version gitVersion  [--repo repositoryPath]
             [--version-list]

    version : GIT version to install (e.g. v1.7.0)
    repo : base folder for GIT source code
           (default : ${__gitRepoHome})
    version-list : list available GIT versions available into repositoryPath 

EOH
}

function checkFolders() {
    if [[ ! -d ${__gitRepoHome} ]]; then
        showError "Can't find GIT repository folder ${__baseFolder}"
        exit -2
    fi
}

function checkArgs() {
	if [[ ${__list} -eq 1 ]]; then
		cd  ${__gitRepoHome}
		git tag -l
		exit 0
	fi
	
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
    elif [[ "$1" == "--repo" ]]; then
        shift
        __gitRepoHome=$1
    elif [[ "$1" == "--version-list" ]]; then
    	shift
        __list=1
    else
       showError "Unknown option $1"
       showHelp
       exit -1
    fi
    shift
done


checkArgs
checkFolders

__installfolder="${__installFolderBase}/git-${__gitVersion}"
mkdir -p  ${__installfolder} && \
	cd ${__gitRepoHome} && \
	make prefix=${__installfolder} install
