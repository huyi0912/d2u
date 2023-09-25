-- 1、创建数据库 test 数据库
create database if not exists test charset utf8;
use test ; -- 选择 test 数据库

-- 2、删除emp表（如果存在）
drop table if exists emp;

-- 3、在 test 库中创建 emp 表
create table emp(
    id serial primary key,
    name varchar(50),
    job varchar(50),
    salary float
);
-- 4、往 emp 表中, 插入若干条记录
insert into emp(name,job,salary) values('张三', '程序员', 3300);
insert into emp(name,job,salary) values('李四', '程序员', 2800);
insert into emp(name,job,salary) values('王五', '程序员鼓励师', 2700);
insert into emp(name,job,salary) values('王二', '部门总监', 4200);
insert into emp(name,job,salary) values('麻子', '程序员', 3000);
insert into emp(name,job,salary) values('最帅三太子', '程序员', 3500);
insert into emp(name,job,salary) values('苍老师', '程序员', 3700);
insert into emp(name,job,salary) values('波多野结衣', 'CEO', 5000);