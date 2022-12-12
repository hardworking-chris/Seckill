# B站秒杀项目

## 介绍

简单的电商秒杀模块项目，项目完成了基础的下单与秒杀页面展示的实现，并在此基础上进行了优化来实现高并发。具体优化策略为:

1. Redis 共享Session，避免每次登录都访问数据库
2. 页面静态化，前后端分离
3. 接口优化
   1. Redis预减库存减少数据库的访问
   2. RabbitMQ异步下单
4. 秒杀接口地址隐藏



This is a simple e-commerce seckill module project. The project completed the basic order and seckill page display implementation, and on this basis, carried out optimization to achieve high concurrency. The specific optimization strategy is as follows:



1. Storing the session, user and goods information before Placing orders in Redis to avoid accessing the database frequently

2. Interface optimization

   1. Pre-reducing inventory by Redis to reduce database access

   2. Placing asynchronous orders achieved by RabbitMQ

## 软件架构/Dependencies

|                   技术/technologies                   | 版本/version  |
| :---------------------------------------------------: | :-----------: |
|                      Spring Boot                      | 2.3.4.RELEASE |
|                         MySQL                         |    8.0.31     |
| [MyBatis Plus](https://github.com/baomidou/generator) |     3.5.2     |
|                       Swagger2                        |     3.0.0     |
|                    amqp(RabbitMQ)                     |               |
|                   Spring Boot Redis                   |               |

## 使用说明

登录页面：http://localhost:8080/login/toLogin

Login:http://localhost:8080/login/toLogin
