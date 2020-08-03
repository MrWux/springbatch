# 创建springBatch入门程序

###  [官方网址](https://docs.spring.io/spring-batch/docs/4.2.x/reference/html/index.html)

## springbatch框架
 在springBatch框架中一个job可以定义很多的步骤step,在每个step里面可以定义要处理的步骤

## 什么是JOB
job和step是springbatch框架执行批处理任务最为核心的两个概念  其中job是一个封装整个批处理过程的一个概念。一个job是运行的基本单位，它内部由step组成。Job本质上可以看成step的一个容器。一个job可以按照指定的逻辑顺序组合step。

## 什么是step
每个step对象都封装了批处理作业的第一个独立的阶段。事情上，每个Job本质上都是由一个或多个步骤组成。 每一个step包含定义和控制实际批处理所需的所有信息。任何特定的内容都由编写job的开发人员自行决定。 一个step可以非常简单也可以非常复杂





## 架构图

![](image\Batch架构图.jpg)

框架一共有4个主要角色：jobLauncher是任务启动器，通过它来启动任务，可以看做是程序的入口。job代表着一个具体的任务。Step代表着一个具体的步骤，一个Job可以包含多个Step(想象把大象放进冰箱这个任务需要多少个步骤)。JobRepository是存储数据的地方，可以看做是一个数据库的接口，在任务执行的时候需要通过它来记录任务状态等等信息。

## 核心API

![](image\核心api.jpg)

```tex
JobInstance:该领域概念和Job的关系与java中实例和类的关系一样，Job定义了一个工作流程，JobInstance就是该工作流程中的一个具体实例。一个job可以有多个JobInstance。多个JobInstance之间的区分就要靠另一个领域概念JobParameters了;

JobParameters:是一组可以贯穿整个Job的运行时配置参数，不同的配置将产生不同的JobInstance,如果使用相同的JobParameters运行同一个Job,那么这次运行会重用上次创建的JobInstance;

JobExecution:该领域概念表示Job的一次运行，JobInstance运行时可能会成功或失败。每一次的JobInstance都会产生一个JobExecution。同一个JobInstance可以多次运行，这样该JobInstance将对应多个Jobexecution,JobExcution记录了一个JobInstance;

StepExecution:类似于JobExecution,该领域对象表示Step的一次运行。Setp是Job一部分，因此一个StepExecution会关联到一个JobExecution。另外，该对象还会存储很多与该次Step运行相关的所有数据，因为该对象也有很多的属性，并且需要持久化以支持一些Spring Batch的特性;

ExecutionContext:从前面的JobExecution,StepExecution的属性介绍中已经提到了该领域概念，说穿了，该领域就是一个容器。该容器由Batch框架控制，框架会该容器持久化，开发人员可以使用该容器保存一些数据。以支持在整个BatchJob或者整个Step中共享这些数据;
```

