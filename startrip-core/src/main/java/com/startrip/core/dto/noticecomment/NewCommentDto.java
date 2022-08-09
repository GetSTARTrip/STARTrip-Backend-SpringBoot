package com.startrip.core.dto.noticecomment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCommentDto {

    private Long noticeId;

    private String userEmail;

    private String commentText;

}
