# yt
yt将是一套完整的java后台解决方案，目前主推core_orm</br>
</br>
为什么要做core_orm?</br>
想用hibernate，但控制不好hibernate的对象状态，没关系，使用core_orm，让你轻松体验mybaits。</br>
</br>
core_orm是基于mybatis封装的增删改查项目，非常轻量、简单、易用。</br>
功能特性如下：</br>
1.service继承ServiceSupport，mapper继承BaseMapper，model继承BaseEntity即可体验，支持单表的增删改查操作，不支持多表联查。</br>
2.支持分页</br>
3.支持自定义扩展字段，比如创建时间、创建人、修改时间、修改人、删除标识等</br>
4.支持spring-boot</br>
5.支持自定义wheresql</br>
</br>
如何运行例子？</br>
1.down代码</br>
2.使用idea开发工具
3.找到core_example项目中的resources目录，修改application.yml中的数据库连接信息</br>
4.运行core_example项目中Application.java</br>
5.浏览器中输入 http://localhost:8080/swagger-ui.html#/ ，展开test-controller，即可访问对应的rest接口</br>
</br>
qq群 489333310
