#!/bin/sh
echo Build command [mvn clean install -Dskip.isis.standalone=true $*]
mvn clean install -Dskip.isis.standalone=true $*
