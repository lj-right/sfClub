# sf-club

#### 介绍
这是一款专为程序员设计的技术交流社区，采用主流微服务框架+C端技术栈做为技术架构。旨在帮助程序员消
除技术信息差，进行平台统一化，程序员可以在平台，完善自身知识，进行针对性面试题库练习，模拟面试，
来提升程序员面试能力。

#### 项目模块简介
├── sf-club-auth            	// 鉴权微服务 [3011]
├──    └── sf-club-auth-api            // 对外接口层
├── 	   └── api
├── 	   └── req
├── 	   └── resp
├──    └── sf-club-auth-common
├── 	   └── config
├── 	   └── enum
├── 	   └── util
├──    └── sf-club-auth-application     // 应用层
├── 	   └── sf-club-auth-application-controller
├── 	       └── controller
├── 	       └── convert  // DTO转BO
├── 	       └── dto
├── 	       └── config
├── 	       └── intercepter
├── 	   └── sf-club-auth-application-job
├── 	   └── sf-club-auth-application-mq
├──    └── sf-club-auth-domain       // 领域层
├── 	   └── service               // 领域能力
├── 	   └── bo       
├── 	   └── convert      // BO转PO
├── 	   └── util
├──    └── sf-club-auth-infra       // 基础设施层
├── 	   └── basic
├── 	       └── entity  
├── 	       └── mapper  
├── 	       └── service  
├── 	       └── util  
├── 	   └── rpc  
├── 	   └── mq
├──    └── sf-club-auth-starter    // 启动层，无关于任何业务，纯启动
        
├── sf-club-gateway        // 网关微服务 [5000]	

├── sf-club-subject        // 题目微服务 [3010]

├── sf-club-circle         // 圈子微服务 [3014]

├── sf-club-interview      // 面试微服务 [3015]

├── sf-club-practice       // 练题微服务 [3013]

├── sf-club-wechat         // 微信微服务 [3012]

├── sf-club-oss            // oss微服务  [4000]
