package com.huixian.eduservice.controller;


import com.huixian.eduservice.entity.EduTeacher;
import com.huixian.eduservice.entity.vo.TeacherQuery;
import com.huixian.eduservice.service.EduTeacherService;
import com.huixian.commonutils.R;
import com.huixian.service.base.config.exceptionHandler.AchangException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author achang
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@Api( description = "讲师管理")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    //查询讲师表所有数据
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){

        List<EduTeacher> list=eduTeacherService.list(null);
        return R.ok().data("items",list) ;
    }

    @ApiOperation("根据ID删除讲师")
    //逻辑删除讲师的方法
    @DeleteMapping("/deleteTeacherById/{id}")
    public R deleteTeacherById(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value="分页讲师列表")
    @GetMapping("/pageList/{page}/{limit}")
    public R pageList(@ApiParam(name="page",value="当前页码",required = true)@PathVariable Long page,@ApiParam(name="limit",value="每页记录数",required=true)@PathVariable Long limit){
        Page<EduTeacher> pageParam=new Page<>(page,limit);
        eduTeacherService.page(pageParam,null);
        List<EduTeacher> records=pageParam.getRecords();
        long total=pageParam.getTotal();
        HashMap map=new HashMap();

        return R.ok().data("total",total).data("rows",records);

    }

    //多条件查询讲师带分页
    @ApiOperation(value = "多条件查询讲师带分页")
    @PostMapping("/pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){//通过封装TeacherQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        //调用方法实现多条件分页查询
        eduTeacherService.pageQuery(pageParam,teacherQuery);

        //获取查询到的数据
        List<EduTeacher> records = pageParam.getRecords();

        try{
            int i=10/0;
        } catch (Exception e) {
            throw new AchangException(20001,"执行了huixian自定义的异常处理");
        }
        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);

    }

    @ApiModelProperty(value = "新增讲师")
    @PostMapping("/save")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiModelProperty(value = "根据id查询")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("item",teacher);
    }

    //修改讲师
    @ApiModelProperty(value = "修改讲师")
    @PostMapping("/updateById")
    public R updateById(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

