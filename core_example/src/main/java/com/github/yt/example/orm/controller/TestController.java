package com.github.yt.example.orm.controller;

import com.github.yt.core.handler.QueryHandler;
import com.github.yt.example.orm.domain.MemberT;
import com.github.yt.example.orm.service.TestService;
import com.github.yt.web.base.BaseController;
import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.handler.HttpResultHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Resource
    private TestService testService;

    @ApiOperation(value="测试add")
    @RequestMapping(method = RequestMethod.POST)
    public HttpResultEntity<?> add() throws Exception {
        testService.save(new MemberT().setUserName("测试name1"));
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value="测试update")
    @RequestMapping(method = RequestMethod.PUT)
    public HttpResultEntity<?> update() throws Exception {
        MemberT member=new MemberT().setUserName("测试name2");
        testService.save(member);
        member.setUserName("修改名称");
        testService.update(member);
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value="测试delete")
    @RequestMapping(method = RequestMethod.DELETE)
    public HttpResultEntity<?> delete() throws Exception {
        MemberT member=new MemberT().setUserName("测试name3");
        testService.save(member);
        testService.delete(MemberT.class,member.getMemberId());
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value="测试通过id查询")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public HttpResultEntity<?> find() throws Exception {
        MemberT member=new MemberT().setUserName("测试name4");
        testService.save(member);
        return HttpResultHandler.getSuccessResult(testService.find(MemberT.class,member.getMemberId()));
    }

    @ApiOperation(value="测试通过findAll")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public HttpResultEntity<?> findAll() throws Exception {
        QueryHandler queryHandle=new QueryHandler();
        queryHandle.addWhereSql("t.name like CONCAT('%', #{data.name1} ,'%') and t.founderName like CONCAT('%', #{data.founderName1} ,'%')")
                .addExpandData("name1", "测试").addExpandData("founderName1", "system");
        return HttpResultHandler.getSuccessResult(testService.findAll(new MemberT(),queryHandle));
    }

    @ApiOperation(value="测试带分页")
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public HttpResultEntity<?> getData() throws Exception {
        QueryHandler queryHandle=new QueryHandler();
        queryHandle.addWhereSql("t.name like CONCAT('%', #{data.name1} ,'%') and t.founderName like CONCAT('%', #{data.founderName1} ,'%')")
                .addExpandData("name1", "测试").addExpandData("founderName1", "system");
        return HttpResultHandler.getSuccessResult(testService.getData(new MemberT(),queryHandle.configPage()));
    }

}
