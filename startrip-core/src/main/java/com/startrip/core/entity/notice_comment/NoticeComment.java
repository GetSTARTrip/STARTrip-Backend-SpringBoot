package com.startrip.core.entity.notice_comment;

import com.startrip.core.entity.notice.Notice;
import com.startrip.core.entity.user.User;
import com.startrip.core.dto.noticecomment.NewCommentDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class NoticeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    private Integer commentUpId; // TODO : 로직 고민

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @NotNull
    private String commentText;

    @NotNull
    private LocalDateTime createdTime;

    private Boolean isUpdated;

    public static NoticeComment of(NewCommentDto dto, User user, Notice notice) {

        NoticeComment comment = NoticeComment.builder()
                .user(user)
                .notice(notice)
                .commentText(dto.getCommentText())
                .createdTime(LocalDateTime.now())
                .build();

        return comment;
    }

    public void updateCommentText(String commentText) {
        this.commentText = commentText;
    }
}
