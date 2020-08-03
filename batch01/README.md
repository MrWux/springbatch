# springBtach01
# 创建springBatch入门程序
## 输出Hello World!

## https://docs.spring.io/spring-batch/docs/4.2.x/reference/html/index.html

## springbatch框架
### 在springBatch框架中一个job可以定义很多的步骤step,在每个step里面可以定义要处理的步骤

## 什么是JOB
### job和step是springbatch框架执行批处理任务最为核心的两个概念
### 其中job是一个封装整个批处理过程的一个概念。
### 一个job是运行的基本单位，它内部由step组成。Job本质上可以看成step的一个容器。
### 一个job可以按照指定的逻辑顺序组合step。

## 什么是step
### 每个step对象都封装了批处理作业的第一个独立的阶段。事情上，每个Job本质上都是由一个或多个步骤组成。
### 每一个step包含定义和控制实际批处理所需的所有信息。任何特定的内容都由编写job的开发人员自行决定。
### 一个step可以非常简单也可以非常复杂
