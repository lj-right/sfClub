package com.suifeng.practice.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.suifeng.practice.api.common.PageResult;
import com.suifeng.practice.api.common.Result;
import com.suifeng.practice.api.req.*;
import com.suifeng.practice.api.vo.*;
import com.suifeng.practice.server.entity.dto.PracticeSetDTO;
import com.suifeng.practice.server.entity.dto.PracticeSubjectDTO;
import com.suifeng.practice.server.service.PracticeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 练习套卷controller
 */
@RestController
@RequestMapping("/practice/set")
@Slf4j
public class PracticeSetController {

    @Resource
    private PracticeSetService practiceSetService;

    @RequestMapping("/getSpecialPracticeContent")
    public Result<List<SpecialPractiveVO>> getSpecialPracticeContent() {
        try {
            List<SpecialPractiveVO> result = practiceSetService.getSpecialPracticeContent();
            if (log.isInfoEnabled()) {
                log.info("PracticeSetController.getSpecialPracticeContent.result:{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (Exception e) {
            log.error("PracticeSetController.getSpecialPracticeContent.error:{}", e.getMessage(), e);
            return Result.fail("获取专项练习内容失败");
        }
    }

    /**
     * 开始练习
     */
    @PostMapping(value = "/addPractice")
    public Result<PracticeSetVO> addPractice(@RequestBody GetPracticeSubjectListReq req) {
        if (log.isInfoEnabled()) {
            log.info("获取练习题入参{}", JSON.toJSONString(req));
        }
        try {
            //参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(req.getAssembleIds()), "标签ids不能为空！");
            PracticeSubjectDTO dto = new PracticeSubjectDTO();
            dto.setAssembleIds(req.getAssembleIds());
            PracticeSetVO practiceSetVO = practiceSetService.addPractice(dto);
            if (log.isInfoEnabled()) {
                log.info("获取练习题目列表出参{}", JSON.toJSONString(practiceSetVO));
            }
            return Result.ok(practiceSetVO);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取练习题目列表异常！错误原因{}", e.getMessage(), e);
            return Result.fail("获取练习题目列表异常！");
        }
    }

    /**
     * 获取练习题
     */
    @PostMapping(value = "/getSubjects")
    public Result<PracticeSubjectListVO> getSubjects(@RequestBody GetPracticeSubjectsReq req) {
        if (log.isInfoEnabled()) {
            log.info("获取练习题入参{}", JSON.toJSONString(req));
        }
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSetId()), "练习id不能为空！");
            PracticeSubjectListVO list = practiceSetService.getSubjects(req);
            if (log.isInfoEnabled()) {
                log.info("获取练习题目列表出参{}", JSON.toJSONString(list));
            }
            return Result.ok(list);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取练习题目列表异常！错误原因{}", e.getMessage(), e);
            return Result.fail("获取练习题目列表异常！");
        }
    }

    /**
     * 获取题目详情
     */
    @PostMapping(value = "/getPracticeSubject")
    public Result<PracticeSubjectVO> getPracticeSubject(@RequestBody GetPracticeSubjectReq req) {
        if (log.isInfoEnabled()) {
            log.info("获取练习题详情入参{}", JSON.toJSONString(req));
        }
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectId()), "题目id不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectType()), "题目类型不能为空！");
            PracticeSubjectDTO dto = new PracticeSubjectDTO();
            dto.setSubjectId(req.getSubjectId());
            dto.setSubjectType(req.getSubjectType());
            PracticeSubjectVO vo = practiceSetService.getPracticeSubject(dto);
            if (log.isInfoEnabled()) {
                log.info("获取练习题目详情出参{}", JSON.toJSONString(vo));
            }
            return Result.ok(vo);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取练习详情异常！错误原因{}", e.getMessage(), e);
            return Result.fail("获取练习题目详情异常！");
        }
    }

    /**
     * 获取模拟套题内容
     */
    @PostMapping(value = "/getPreSetContent")
    public Result<PageResult<PracticeSetVO>> getPreSetContent(@RequestBody GetPreSetReq req) {
        if (log.isInfoEnabled()) {
            log.info("获取模拟套题内容入参{}", JSON.toJSONString(req));
        }
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            PracticeSetDTO dto = new PracticeSetDTO();
            dto.setOrderType(req.getOrderType());
            dto.setPageInfo(req.getPageInfo());
            dto.setSetName(req.getSetName());
            PageResult<PracticeSetVO> list = practiceSetService.getPreSetContent(dto);
            if (log.isInfoEnabled()) {
                log.info("获取模拟套题内容出参{}", JSON.toJSONString(list));
            }
            return Result.ok(list);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取模拟套题内容异常！错误原因{}", e.getMessage(), e);
            return Result.fail("获取模拟套题内容异常！");
        }
    }

    /**
     * 获取未完成的练题内容
     */
    @PostMapping(value = "/getUnCompletePractice")
    public Result<PageResult<UnCompletePracticeSetVO>> getUnCompletePractice(@RequestBody GetUnCompletePracticeReq req) {
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            PageResult<UnCompletePracticeSetVO> list = practiceSetService.getUnCompletePractice(req);
            if (log.isInfoEnabled()) {
                log.info("获取未完成练习内容出参{}", JSON.toJSONString(list));
            }
            return Result.ok(list);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取未完成练习内容异常！错误原因{}", e.getMessage(), e);
            return Result.fail("获取未完成练习内容异常！");
        }
    }

}
