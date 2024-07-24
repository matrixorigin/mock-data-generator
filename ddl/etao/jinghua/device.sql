CREATE TABLE `t_device` (
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `device_type` VARCHAR(10) NOT NULL COMMENT '设备类型',
  `tag` JSON DEFAULT NULL COMMENT '设备标签组',
  `metric_name` VARCHAR(20) NOT NULL COMMENT '指标名称',
  `metric_value` FLOAT NOT NULL COMMENT '指标值'
) CLUSTER BY (`create_time`);

load data infile '/Users/sudong/Program/MatrixOrigin/mock-data-generator/data/device0.tbl' into table device  FIELDS TERMINATED BY '|' LINES TERMINATED BY '\n' parallel 'true';