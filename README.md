# HttpHelper
一个假装封装好的网络请求类+json解析库
## 使用方式
建立一个helper对象</br>
```Java
HttpHelper helper = new HttpHelper.set().url("请求的地址").bulid();
```
默认请求方式为GET</br>
若更改POST在之中添加`mode("POST")`</br>
并在其中添加`param()`加入要上传的参数</br>
默认连接超时为10s，读取超时为5s</br>
更改分别通过`readTO`和`connectTO`实现，设置时间单位为s</br>

获取response对象中的Callback拿取结果</br>
```Java
new Response.from(helper).to(构造的对象.class).get(new Callback<T>() {
            @Override
            public void succeed(String response) {
                //对应操作
            }

            @Override
            public void succeed(T object) {
                //对应操作
            }

            @Override
            public void error(Exception e, int status) {
                //对应操作
            });
```
在Callback内拿起解析json对应的实体JavaBean类型并传参</br>
为方便处理给了两个succeed接口，一个是对应的json字符串，一个是解析后的javaBean实体类，具体操作针对开发时候的实际需要做出相应的更改。
