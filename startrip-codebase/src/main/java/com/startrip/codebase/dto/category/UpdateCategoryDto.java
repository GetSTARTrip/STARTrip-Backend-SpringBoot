package com.startrip.codebase.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryDto {

    private UUID id;
    //private Long categoryParent; //TODO: 카테고리의 Parent도 바꿀 수 있게 할 것인가
    private String categoryName;

}