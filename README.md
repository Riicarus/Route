# Route
数据结构课程实验项目--路由组网

## 概述
- 路由器统一使用路由器工厂创建
- 在配置文件中设定路由器数量
- 连接信息保存在数据库中（MySQL）
- 使用随机数进行路由器连接延迟初始化
- 使用守护线程监听接收文件变化，调用消息收发器进行处理（其实不需要创建文件进行请求接收，只需要工厂在消息收发器统一分配缓存，将其注册到工厂进行管理即可）
- 使用线程锁，防止并发读写导致数据混乱
- 访问 http://localhost:9000/api/send/{destinationId}/{message} 进行测试
- 路由器接收到信息会输出在控制台

## 优化
- 优化接收信息方式（工厂统一分配缓存，统一注册管理）
- 添加接口控制路由器数量（工厂新增路由器管理方法）
- 优化连接信息数据库更新方式：每次启动清空数据库（目前需要手动清空）
- 动态模拟路由器连接延迟，动态更新路由表（增加守护线程）
