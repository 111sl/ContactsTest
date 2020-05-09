## day04

### 1.BroadcastReceiver

​	1.1 发送广播<br/>		方法和启动活动以及服务基本相同，使用intent然后sendBroadcast即可。<br/>	1.2 广播中不可执行耗时操作，如需执行则另开一个服务。<br/>	1.3 注册方法：静态注册（xml中）和动态注册，无论哪一种最终都是填写onReceive方法进行相关操作。动态注册中registerReceiver和unregiterReceiver成对出现。<br/>	1.4广播类型：普通、异步、有序，后两个系统级常用，平时不常用。

### 2.ContentProvider

​	2.1 分为自定义contentprovider和系统自带<br/>	2.2 自定义则必须重载一些方法<br/>	2.3 无论是自定义还是系统默认contentprovider，外部调用只需要直接调用类似insert这类接口并传参即可