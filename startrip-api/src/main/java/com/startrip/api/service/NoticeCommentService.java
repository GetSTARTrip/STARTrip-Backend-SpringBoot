package com.startrip.api.service;


import com.startrip.core.dto.noticecomment.NewCommentDto;
import com.startrip.core.dto.noticecomment.ResponseCommentDto;
import com.startrip.core.dto.noticecomment.UpdateCommentDto;
import com.startrip.core.entity.notice.Notice;
import com.startrip.core.entity.notice.NoticeRepository;
import com.startrip.core.entity.notice_comment.NoticeComment;
import com.startrip.core.entity.notice_comment.NoticeCommentRepository;
import com.startrip.core.entity.user.User;
import com.startrip.core.entity.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeCommentService {

    private NoticeCommentRepository noticeCommentRepository;
    private UserRepository userRepository;
    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeCommentService(NoticeCommentRepository noticeCommentRepository, UserRepository userRepository, NoticeRepository noticeRepository) {
        this.noticeCommentRepository = noticeCommentRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public List<ResponseCommentDto> getComment(Long noticeId) {
//        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
//            throw new IllegalStateException("존재하지 않는 게시글입니다.");
//        });
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        });
        List<NoticeComment> comments = notice.getComments();
        List<ResponseCommentDto> dtos = new ArrayList<>();
        for (NoticeComment comment : comments) {
            dtos.add(ResponseCommentDto.of(comment));
        }
        return dtos;
    }

    @Transactional
    public void newComment(NewCommentDto dto) {
        User writer = userRepository.findByEmail(dto.getUserEmail()).orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        });
        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        });
        NoticeComment comment = NoticeComment.of(dto, writer, notice);

        noticeCommentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, UpdateCommentDto dto) {
        NoticeComment comment = noticeCommentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("해당 댓글이 없습니다.");
                });
        comment.updateCommentText(dto.getCommentText());

        noticeCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        noticeCommentRepository.deleteById(commentId);
    }
}
