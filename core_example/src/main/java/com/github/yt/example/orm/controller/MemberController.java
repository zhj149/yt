package com.github.yt.example.orm.controller;

import com.github.yt.core.handler.QueryHandler;
import com.github.yt.example.orm.domain.MemberT;
import com.github.yt.example.orm.service.TestService;
import com.github.yt.web.base.BaseController;
import com.github.yt.web.result.HttpResultEntity;
import com.github.yt.web.result.handler.HttpResultHandler;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Resource
    private TestService testService;

    @ApiOperation(value = "add member", notes = "add member notes", response = MemberT.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpResultEntity<?> add(@ApiParam(value = "memberT", required = true) @RequestBody MemberT memberT) throws Exception {
        testService.save(memberT);
        return HttpResultHandler.getSuccessResult();
    }

    //测试用
    @ApiOperation(value = "batchAdd", notes = "batchAdd", response = MemberT.class)
    @RequestMapping(value = "/batchAdd", method = RequestMethod.GET)
    //@ApiParam(value = "memberT", required = true) @RequestBody MemberT memberT
    public HttpResultEntity<?> batchAdd() throws Exception {
        MemberT member1 = new MemberT();
        member1.setPhone("159");
        MemberT member2 = new MemberT();
        member2.setPhone("186");
        List<MemberT> list = new ArrayList<>();
        list.add(member1);
        list.add(member2);
        testService.saveBatch(list);
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value = "update member", notes = "update member notes", response = MemberT.class)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public HttpResultEntity<?> update(@ApiParam(value = "memberT", required = true) @RequestBody MemberT memberT) throws Exception {
        testService.updateForSelective(memberT);
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value = "delete")
    @RequestMapping(value = "/delete/{memberId}", method = RequestMethod.DELETE)
    public HttpResultEntity<?> delete(@PathVariable String memberId) throws Exception {
        testService.delete(MemberT.class, memberId);
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value = "logicDelete")
    @RequestMapping(value = "/logic/{memberId}", method = RequestMethod.DELETE)
    public HttpResultEntity<?> logicDelete(@PathVariable String memberId) throws Exception {
        testService.logicDelete(MemberT.class, memberId);
        return HttpResultHandler.getSuccessResult();
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/find/{memberId}", method = RequestMethod.GET)
    public HttpResultEntity<?> find(@PathVariable String memberId) throws Exception {
        return HttpResultHandler.getSuccessResult(testService.find(MemberT.class, memberId));
    }

    @ApiOperation(value = "通过findAll")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public HttpResultEntity<?> findAll(MemberT memberT) throws Exception {
        QueryHandler queryHandle = new QueryHandler();
        queryHandle.addOrderBy("createDateTime", "desc");
        return HttpResultHandler.getSuccessResult(testService.findAll(memberT, queryHandle));
    }

    @ApiOperation(value = "带分页")
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public HttpResultEntity<?> getData() throws Exception {
        QueryHandler queryHandle = new QueryHandler();
        queryHandle.addOrderBy("createDateTime", "desc");
        queryHandle.addWhereSql("t.name like CONCAT('%', #{data.name1} ,'%') and t.founderName like CONCAT('%', #{data.founderName1} ,'%')")
                .addExpandData("name1", "测试").addExpandData("founderName1", "system");
        return HttpResultHandler.getSuccessResult(testService.getData(new MemberT(), queryHandle.configPage()));
    }

}
