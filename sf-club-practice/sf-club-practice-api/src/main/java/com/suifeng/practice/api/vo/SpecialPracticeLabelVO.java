package com.suifeng.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecialPracticeLabelVO implements Serializable {

    private Long id;

    /**
     * 分类id-标签id
     */
    private String assembleId;

    private String labelName;

}
