package com.suifeng.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecialPractiveVO implements Serializable {

    private String primaryCategoryName;

    private Long primaryCategoryId;

    private List<SpecialPractiveCategoryVO> categoryList;

}
