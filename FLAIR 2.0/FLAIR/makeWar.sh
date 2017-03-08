#!/bin/sh

if [ "$#" -lt 1 ]; then
    echo "Usage: ./makeWar FILENAME (e.g. FeedBookDev.war)"
    exit 1
fi

OUTFILE=$1

./generateRevJs.sh


cd war
echo "Removing old WAR files..."
rm *.war
echo "Building ${OUTFILE} in `pwd`..."
zip -q -r ${OUTFILE} * -x media\*
cd ..

echo "Done."

