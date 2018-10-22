# mmall

## 资源下载

[阿里云镜像](http://mirrors.aliyun.com)

## 环境部署学习建议

1. [Linux权限管理之基本权限](https://www.imooc.com/learn/481)
2. [Linux软件安装管理](https://www.imooc.com/learn/447)
3. [Linux达人养成计划](https://www.imooc.com/learn/175)
4. [Linux服务管理](https://www.imooc.com/learn/537)
5. [学习iptables防火墙](https://www.imooc.com/learn/389)
6. [版本管理工具介绍——Git篇](https://www.imooc.com/learn/208)
7. [Tomcat基础 第一章](https://www.imooc.com/learn/166)
8. [Maven基础](https://www.imooc.com/learn/443)
9. [MySQL基础](https://www.imooc.com/learn/122)


## 包结构

MVC

* pojo - 数据库表的映射
* dao - 数据访问对象
* service - 业务逻辑层
* controller - 控制器层
* vo - 视图对象

工具

* util

常量、全局异常

* common

## 用户模块

功能：

- 登陆
- 用户验证
- 注册
- 忘记密码
- 提交问题答案
- 重置密码
- 获取用户信息
- 更新用户信息
- 退出登陆

学习目标：

- 横向越权、纵向越权安全漏洞
- MD5铭文加密及增加salt值
- Guava缓存的使用
- 高复用服务响应对象的设计思想及抽象封装
- Mybatis-Plugin的使用技巧
- session的使用
- 方法局部演进

横向越权、纵向越权安全漏洞：

- 横向越权：攻击者尝试访问与他拥有相同权限的用户的资源
- 纵向越权：低级别攻击者尝试访问高级别用户的资源

高复用服务响应对象的设计思想及抽象封装：

```json
{
    "code": 200,
    "msg": "ok",
    "data": {
        "name": "ikutarian"
    }
}
```

https://www.jianshu.com/p/97f1d52210ab
https://gitee.com/imooccode/happymmallwiki/wikis/Home