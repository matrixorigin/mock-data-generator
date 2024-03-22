
# What's in Mock-Data-Generator?

Mock-Data-Generator is a java-based tool to generate data for mostly table data of database or other casess.

# How to use Mock-Data-Generator?

## 1. config table definition

  ```
  tables:
  - name: sample1         #Table name
    count: 10             #The count of table data
    columns:
      - name: id          #The column name
        type: auto        #The column type, the tool support some typies that will be described in following phase
      - name: name      
        builtin: name     #The builtin type, the tool provides lots of builtin type that will be described in following phase
        #Prefixes
        #There are 2 types of prefix:
        #1. enum: format ES001,DA002
        #2. random number: format (10000,20000)
        prefix: ES001,DA002 
        null_ratio: 10    #the percentage of null value
      - name: proviceId
        enum: 11,12,13,14 #The enum type, values is separated by [,].
        index: proviceId  #The index tag,means each mutiple tuple[a,b,c] in which b and c is bound to a is immutable.
      - name: proviceName
        enum: 安徽,江苏,上海,杭州,黑龙江,吉林,北京,新疆,上冻,fdasf,fdasfd
        ref: proviceId    #The ref tag,means this column is bound to index which name is "proviceId" 
  ```

## 2. execute `./run.sh`
```
bash boot.sh -i def/template.yaml -o mydata
```

# Supported Type

| Parameters   | Description                                                 |
|--------------|-------------------------------------------------------------|
| auto         | auto_increment, and the value of this type is only ```\N``` |
| int(x,y)     | integer type, range from ```x``` to ```y```                 |
| decimal(x,y) | decimal type, max length is ```x```, precision is  ```y```  |
| date         | date type                                                   |
| datetime     | datetime type                                               |
| char(x)      | char type, length is ```x```                                |
| varchar(x)   | varchar type,max length is ```x```                          |
| uuid         | uuid type                                                   |
| vector(x)    | vector type, value is between 0 and 1000                    |
| json         | json type                                                   |


# Builtin Type

| Parameters    | Description     |
|---------------|-----------------|
| unique        | a unique number |
| name          |                 |
| phonenumber   |                 |
| cellphone     |                 |
| idcardnum     |                 |
| SSN           |                 |
| email         |                 |
| address       |                 |
| city          |                 |
| carvin        |                 |
| bankaccount   |                 |
| province      |                 |
| provincecode  |                 |
| creditcard    |                 |
| officecardnum |                 |
| carplatenum   |                 |
| nationality   |                 |
| college       |                 |
| qualification |                 |
| countryname   |                 |
| school        |                 |
| usname        |                 |
| countrycode   |                 |
| swiftcode     |                 |
| degree        |                 |
| licensenum    |                 |
| qq            |                 |
| wechat        |                 |
| HKphonenum    |                 |
| ipaddrv4      |                 |
| ipaddrv6      |                 |
| macaddr       |                 |
| passport      |                 |
| passportHK    |                 |
| passportMA    |                 |
| passportHKMA  |                 |

