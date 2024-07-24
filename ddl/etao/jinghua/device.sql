CREATE TABLE `t_device` (
  `create_time` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `device_type` VARCHAR(10) NOT NULL COMMENT '设备类型',
  `tag` JSON DEFAULT NULL COMMENT '设备标签组',
  `metric_name` VARCHAR(20) NOT NULL COMMENT '指标名称',
  `metric_value` FLOAT NOT NULL COMMENT '指标值'
) CLUSTER BY (`device_type`, `metric_name`, `create_time`);