package com.suifeng.practice.server.service;

import com.suifeng.practice.api.common.PageResult;
import com.suifeng.practice.api.req.GetPracticeSubjectsReq;
import com.suifeng.practice.api.req.GetUnCompletePracticeReq;
import com.suifeng.practice.api.vo.*;
import com.suifeng.practice.server.entity.dto.PracticeSetDTO;
import com.suifeng.practice.server.entity.dto.PracticeSubjectDTO;

import java.util.List;

public interface PracticeSetService {

    /**
     * 获取专项练习内容
     * @return
     */
    List<SpecialPractiveVO> getSpecialPracticeContent();

    /**
     * 开始练习
     */
    PracticeSetVO addPractice(PracticeSubjectDTO dto);

    /**
     * 获取练习题
     */
    PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req);

    /**
     * 获取题目
     */
    PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto);

    /**
     * 获取模拟套题内容
     */
    PageResult<PracticeSetVO> getPreSetContent(PracticeSetDTO dto);

    /**
     * 获取未完成练习内容
     */
    PageResult<UnCompletePracticeSetVO> getUnCompletePractice(GetUnCompletePracticeReq req);
}
