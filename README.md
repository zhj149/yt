# yt
yt将是一套完整的java后台解决方案，目前主推core_orm</br>
</br>
core_orm是基于mybatis封装的增删改查项目，非常轻量、简单、易用。</br>
功能特性如下：</br>
1.service继承ServiceSupport，mapper继承BaseMapper，model继承BaseEntity即可体验，支持单表的增删改查操作，不支持多表联查。</br>
2.支持自定义扩展字段，比如创建时间、创建人、修改时间、修改人、删除标识等</br>
3.支持spring-boot</br>
4.支持wheresql</br>
</br>
如何运行例子？</br>
1.down代码</br>
2.找到core_example项目中的resources目录，修改application.yml中的数据库连接信息</br>
3.运行core_example项目中Application.java</br>
4.浏览器中输入 http://localhost:8080/swagger-ui.html#/ ，展开test-controller，即可访问对应的rest接口</br>
