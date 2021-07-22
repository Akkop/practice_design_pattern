# 日志系统：一条SQL更新语句是如何执行的？

连接数据库(连接器) -- 分析器 -- 优化器 -- 执行器 -- 存储引擎 -- 根据条件进行更新、

-- 当跟新的时候，将会清空对应表的缓存。8.0之后将不再支持缓存。

-- 然而跟新操作与查询操作不同的是跟新操作还包含了两个重要的日志模块，redo log(重做日志) ，binlog(归档日志)

##### 重做日志：redo log

-- InnoDB 引擎就会先把记录写到 redo log ，并跟新内存。适时的将数据更新到磁盘。

-- InnoDB 的 redo log 是固定大小的

-- redo log 是 InnoDB 引擎特有的日志

-- write pos 写数据，checkpoint 擦除位置数据，并往后继续推移循环

-- redo log 可以保证即使异常重启mysql，之前提交的记录也不会丢失，这个能力叫 crash-safe

##### 重做日志：binlog

-- binlog 是 Server 层的日志，即归档日志

##### 两种日志的差别

-- redo log 是 InnoDB 引擎特有的，binlog 是 MySQL 的 Server 层实现的，所有引擎都可以使用。

-- redo log 是物理日志，binlog 是逻辑日志，记录的是这个语句的原始逻辑

-- redo log 是循环写的，空间固定会用完，binlog 是可以追加写入的，不会覆盖以前的日志。

##### 更新流程

取id = 2 的行 - 判断是否再内存中，在内存使用内存数据，不在内存使用磁盘数据 - 对该条行c值进行+1 - 写入新行 - 新行跟新到内存 - 写入redo log，redo log 处于 prepare 状态 - 告知执行器完成跟新 - 执行器生成该操作的 binlog，并把 binlog 写入磁盘。- 执行器调用引擎的提交事务接口，将redo log 改成 commit 状态自此完成跟新。



redo log 用于保证 crash-safe 能力。innodb_flush_log_at_trx_commit 这个参数设置成 1 的时候，表示每次事务的 redo log 都直接持久化到磁盘。这个参数我建议你设置成 1，这样可以保证 MySQL 异常重启之后数据不丢失。



sync_binlog 这个参数设置成 1 的时候，表示每次事务的 binlog 都持久化到磁盘。这个参数我也建议你设置成 1，这样可以保证 MySQL 异常重启之后 binlog 不丢失。