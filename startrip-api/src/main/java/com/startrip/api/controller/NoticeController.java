package com.startrip.api.controller;

import com.startrip.api.service.NoticeCommentService;
import com.startrip.api.service.NoticeService;
import com.startrip.core.dto.notice.NewNoticeDto;
import com.startrip.core.entity.notice.Notice;
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
public class NoticeController {

    private NoticeService noticeService;

    private NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeController(NoticeService noticeService, NoticeCommentService noticeCommentService) {
        this.noticeService = noticeService;
        this.noticeCommentService = noticeCommentService;
    }

    @GetMapping("/notice")
    public @ResponseBody
    ResponseEntity
    allNotice() {
        List<Notice> notices = noticeService.allNotice();
        return new ResponseEntity(notices, HttpStatus.OK);
    }

    @GetMapping("/notice/{id}")
    public @ResponseBody
    ResponseEntity getNotice(@PathVariable("id") Long id) {
        Notice notice;
        try {
            notice = noticeService.getNotice(id);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(notice, HttpStatus.OK);
    }

    @PostMapping("/notice")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity addNotice(@RequestBody NewNoticeDto dto) {
        try {
            noticeService.createNotice(dto);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("생성되었습니다", HttpStatus.OK);
    }

    @PutMapping("/notice/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity updateNotice(@PathVariable("id") Long id, @RequestBody NewNoticeDto dto) {
        noticeService.updateNotice(id, dto);
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity deleteNotice(@PathVariable("id") Long id) {
        try {
            noticeService.deleteNotice(id);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }
}
