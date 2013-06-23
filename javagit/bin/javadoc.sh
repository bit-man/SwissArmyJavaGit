#!/bin/bash

__srcPath=src/main/java/
__output=/tmp/javadoc
__version=0.2.0

javadoc -public -sourcepath ${__srcPath} -d ${__output} -version -author \
	-windowtitle "Swiss Arma JavaGit v${__version}" -verbose \
	-subpackages edu.nyu.cs.javagit.api -subpackages edu.nyu.cs.javagit.client\
		edu.nyu.cs.javagit

