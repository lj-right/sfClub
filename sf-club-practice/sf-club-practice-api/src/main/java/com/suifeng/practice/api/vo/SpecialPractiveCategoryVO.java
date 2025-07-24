package com.suifeng.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecialPractiveCategoryVO implements Serializable {

    private String categoryName;

    private Long categoryId;

    private List<SpecialPracticeLabelVO> labelList;
}
