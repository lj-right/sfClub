package com.jingdiansuifeng.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.jingdiansuifeng.subject.application.convert.SubjectLabelDTOConverter;
import com.jingdiansuifeng.subject.application.dto.SubjectLabelDTO;
import com.jingdiansuifeng.subject.common.entity.Result;
import com.jingdiansuifeng.subject.domain.entity.SubjectLabelBO;
import com.jingdiansuifeng.subject.domain.service.SubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 标签Controller
 */
@RestController
@RequestMapping("/subject/label")
@Slf4j
public class SubjectLabelController {

    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;

    /**
     * 新增标签
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.add.subjectLabelDTO:{}"
                        , JSON.toJSONString(subjectLabelDTO));
            }

            Preconditions.checkNotNull(subjectLabelDTO.getLabelName(), "标签名称不能为空");


            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDtoToLabelBo(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增标签失败");
        }
    }

    /**
     * 更新标签
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.update.subjectLabelDTO:{}"
                        , JSON.toJSONString(subjectLabelDTO));
            }

            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签id不能为空");

            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDtoToLabelBo(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新标签失败");
        }
    }

    /**
     * 删除标签
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.delete.subjectLabelDTO:{}"
                        , JSON.toJSONString(subjectLabelDTO));
            }

            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签id不能为空");

            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDtoToLabelBo(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.delete(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除标签失败");
        }
    }

    /**
     * 查询分类下标签
     */
    @PostMapping("/queryLabelByCategoryId")
    public Result<List<SubjectLabelDTO>> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryLabelByCategoryId.subjectLabelDTO:{}"
                        , JSON.toJSONString(subjectLabelDTO));
            }

            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(), "分类id不能为空");

            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDtoToLabelBo(subjectLabelDTO);
            List<SubjectLabelBO> boList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> result = SubjectLabelDTOConverter.INSTANCE
                    .convertBoListToLabelDTOList(boList);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.queryLabelByCategoryId.error:{}", e.getMessage(), e);
            return Result.fail("查询分类下标签失败");
        }
    }

}
