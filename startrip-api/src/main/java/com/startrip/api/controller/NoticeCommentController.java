package com.startrip.api.controller;

import com.startrip.api.service.NoticeCommentService;
import com.startrip.core.dto.noticecomment.NewCommentDto;
import com.startrip.core.dto.noticecomment.ResponseCommentDto;
import com.startrip.core.dto.noticecomment.UpdateCommentDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoticeCommentController {
    private final NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeCommentController(NoticeCommentService noticeCommentService) {
        this.noticeCommentService = noticeCommentService;
    }

    @GetMapping("/notice/{id}/comment")
    public @ResponseBody ResponseEntity getComment(@PathVariable("id") Long noticeId) {
        List<ResponseCommentDto> comments;
        try {
            comments = noticeCommentService.getComment(noticeId);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(comments, HttpStatus.OK);
    }

    @PostMapping("/notice/{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity addComment(@PathVariable("id") Long noticeId, @RequestBody NewCommentDto dto) {
        try {
            dto.setNoticeId(noticeId);
            noticeCommentService.newComment(dto);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 생성되었습니다.", HttpStatus.CREATED);
    }

    // TODO : 수정 시, 해당 인증 정보를 받아서 처리하기 (SecurityHolder)
    @PutMapping("/notice/{id}/comment/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity updateComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateCommentDto dto) {
        try {
            noticeCommentService.updateComment(commentId, dto);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 수정되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}/comment/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            noticeCommentService.deleteComment(commentId);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 삭제되었습니다.", HttpStatus.OK);
    }
}
