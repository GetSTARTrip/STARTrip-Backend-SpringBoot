package com.startrip.core.dto.noticecomment;

import com.startrip.core.entity.notice_comment.NoticeComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseCommentDto {

    private Long commentId;
    private Integer commentUpId;

    private String writer;
    private Long noticeId;

    private String commentText;
    private LocalDateTime createdTime;
    private Boolean isUpdated;


    public static ResponseCommentDto of(NoticeComment entity) {
        ResponseCommentDto dto = new ResponseCommentDto();
        dto.setCommentText(entity.getCommentText());
        dto.setCommentId(entity.getCommentId());
        dto.setNoticeId(entity.getNotice().getNoticeId());
        dto.setWriter(entity.getUser().getEmail()); // TODO : User Properties Response ...
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setCommentUpId(entity.getCommentUpId());
        return dto;
    }

}
