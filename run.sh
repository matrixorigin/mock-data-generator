#!/usr/bin/env bash

if [[ $# -eq 0 ]];then
    echo "No parameters provided,the progame will run with  default parameters. "
    INPUT="def"
    OUTPUT="data"
fi
FILECOUNT=0
ARGS=`getopt -o c:o:h -l help,test:,file_count:,parse:,benchmark:,config:,output:,tables:,table_size:,auto_inc: -- "$@"`
echo "ARGS=${ARGS}"
eval set -- "${ARGS}"
while :
do
  case $1 in
    -c | --config) CONFIG=$2; shift ;;
    -o | --output) OUTPUT=$2; shift ;;
    -h | --help) HELP="true"; break ;;
    --tables) TABLES=$2; shift ;;
    --table_size) TABLE_SIZE=$2; shift ;;
    --auto_inc) AUTO_INCR=$2; shift ;;
    --benchmark) BENCHARMARK=$2; shift ;;
    --file_count) FILECOUNT=$2; shift ;;
    --parse) DDLFILE=$2; shift ;;
    --test) TEST=$2; shift ;;
    --) shift ; break ;;
    *) echo "Invalid option: $1" exit 1 ;;
  esac
  shift 
done 

if [ "${HELP}" == "true" ];then
  echo -e "Usage:　bash rush.sh [option] [param] ...\nExcute MO mock data task."
    echo -e "\n   -h  show scripts usage.\n   -c  provide the input table definition file or directory."
    echo -e "   -o provide output path for table data,optional."
    echo -e "   --benchmark the benchmark name, and if specified, will generate data for this benchmark."
    echo -e "   --parse parse the ddl file to generate table config definition"
    echo -e "   --tables  table count for benchmark[sysbench]."
    echo -e "   --table_size  table size for benchmark[sysbench]."
    echo -e "   --auto_inc  whether table is auto_increment for benchmark[sysbench].\n"
    echo -e "Examples:\n"
    echo "   bash rush.sh -i def -o data"
    echo -e "   bash boot.sh -i def/template.yaml -o mydata \n"
    echo "For more support,please email to sudong@matrixorigin.io"
    exit 1
fi

#if [ -z "${CONFIG}" ]; then
#    if [ -z "${BENCHARMARK}" ]; then
#      echo "Error: config file is required by -c or --config."
#      exit 1;
#    fi
#fi

if [ -z "${OUTPUT}" ]; then
    echo "The output path is not specified, and will use default paht[./data/]."
fi

if [ "${BENCHARMARK}" == "sysbench" ]; then
    if [ -z "${TABLES}" ]; then
      echo "Error: The table count is required in benchmark[sysbench] by --tables."
      exit 1;
    fi
    
    if [ -z "${TABLE_SIZE}" ]; then
      echo "Error: The table size is required in benchmark[sysbench] by --table_size."
      exit 1;
    fi
    
    if [ -z "${AUTO_INCR}" ]; then
      echo "The sysbench table is not with auto_increment primary key."
    fi
    
    expr ${TABLES} "+" 10 &> /dev/null
    if [ $? -ne 0 ]; then
      echo 'The table count ['${TABLES}'] is not a number'
      exit 1
    fi
    
    expr ${TABLE_SIZE} "+" 10 &> /dev/null
    if [ $? -ne 0 ]; then
      echo 'The table size ['${TABLE_SIZE}'] is not a number'
      exit 1
    fi
fi

WORKSPACE=$(cd `dirname $0`; pwd)
CONF_YAML=$WORKSPACE/conf.yml
LIB_WORKSPACE=$WORKSPACE/lib

function boot {
local libJars libJar
for libJar in `find ${LIB_WORKSPACE} -name "*.jar"`
do
  libJars=${libJars}:${libJar}
done

if [ ! -z "${TEST}" ]; then
  java -Xms1024M -Xmx10240M -cp ${libJars} \
            -Dconf.yml=${CONF_YAML} \
            io.mo.gendata.Faker --config ${CONFIG} --output ./data/ --test ${TEST}
  exit 0
fi
echo "file_count=${FILECOUNT}"
if [ -z "${BENCHARMARK}" ]; then
  if [ -z "${DDLFILE}" ];then
    java -Xms1024M -Xmx10240M -cp ${libJars} \
          -Dconf.yml=${CONF_YAML} \
          io.mo.gendata.Faker --config ${CONFIG} --output ${OUTPUT} --file_count ${FILECOUNT}
  else 
    java -Xms1024M -Xmx10240M -cp ${libJars} \
              -Dconf.yml=${CONF_YAML} \
              io.mo.gendata.Faker --parse ${DDLFILE} --output ${OUTPUT} --file_count ${FILECOUNT}
  fi
fi 

if [ "${BENCHARMARK}" == "sysbench" ]; then
  java -Xms1024M -Xmx10240M -cp ${libJars} \
            -Dconf.yml=${CONF_YAML} \
            io.mo.gendata.benchmark.sysbench.Generator --tables ${TABLES} --table_size ${TABLE_SIZE} --output $OUTPUT --file_count ${FILECOUNT}
fi

}

boot
