#!/usr/bin/env bash

if [[ $# -eq 0 ]];then
    echo "No parameters provided,the progame will run with  default parameters. "
    INPUT="def"
    OUTPUT="data"
fi
while getopts ":i:o:h" opt
do
    case $opt in
        i)
        INPUT=${OPTARG}
        echo -e "\nThe input table definition file : ${INPUT}"
        ;;
        o)
        OUTPUT=${OPTARG}
        echo -e "\nThe output path for table data : ${OUTPUT}"
        ;;
        h)
        echo -e "Usage:　bash rush.sh [option] [param] ...\nExcute MO mock data task."
        echo -e "\n   -h  show scripts usage.\n   -i  provide the input table definition file or directory,optional."
        echo -e "   -o provide output path for table data,optional.\n"
        echo -e "Examples:\n"
        echo "   bash rush.sh -i def -o data"
	      echo -e "   bash boot.sh -i def/template.yaml -o mydata \n"
        echo "For more support,please email to dong.su@matrixorigin.io"
        exit 1
        ;;
        ?)
        echo "Unkown parameter,please use -h to get help."
        exit 1;;
    esac
done

WORKSPACE=$(cd `dirname $0`; pwd)
CONF_YAML=$WORKSPACE/conf.yml
LIB_WORKSPACE=$WORKSPACE/lib

function boot {
local libJars libJar
for libJar in `find ${LIB_WORKSPACE} -name "*.jar"`
do
  libJars=${libJars}:${libJar}
done
java -Xms1024M -Xmx10240M -cp ${libJars} \
        -Dconf.yml=${CONF_YAML} \
        io.mo.gendata.Faker $INPUT $OUTPUT
}

boot
