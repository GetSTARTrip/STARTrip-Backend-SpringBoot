package com.startrip.codebase.service;

import com.startrip.codebase.board.NoticeService;
import com.startrip.codebase.board.category.Category;
import com.startrip.codebase.board.category.CategoryRepository;
import com.startrip.codebase.board.notice.Notice;
import com.startrip.codebase.board.notice.NoticeRepository;
import com.startrip.codebase.member.domain.user.User;
import com.startrip.codebase.member.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class NoticeServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(NoticeServiceTest.class);

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setup() {
    }

    @BeforeAll
    public void dataSetUp() throws Exception {
        dataCleanUp();
        logger.info("테스트 용 데이터를 DB로 삽입합니다");
        try (Connection conn = dataSource.getConnection()) {  // (2)
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/data.sql"));  // (1)
        }
    }

    @AfterAll
    public void dataCleanUp() throws SQLException {
        logger.info("테이블 초기화합니다");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scheme.sql"));
        }
    }

    private void createNotice(User user, Category category) {
        Notice notice = Notice.builder()
                .user(user)
                .category(category)
                .viewCount(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();

        noticeRepository.save(notice);
    }

    private void createNotice() {
        Notice notice = Notice.builder()
                .viewCount(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();

        noticeRepository.save(notice);
    }


    @DisplayName("게시글 조회 시, 조회수가 올라간다")
    @Test
    void notice_view_counting() {
        createNotice();
        Notice notice = noticeRepository.findAll().get(0);
        Notice find = noticeService.getNotice(notice.getNoticeId()); // 게시글 조회 로직

        assertThat(find.getViewCount()).isEqualTo(1);
    }

    @DisplayName("게시글 10번 조회 테스트")
    @Test
    void notifce_view_100() {
        noticeRepository.deleteAll();
        createNotice();

        List<Notice> notices = noticeRepository.findAll();
        Notice firstNotice = notices.get(0);
        for (int i = 0; i < 10; i++) {
            noticeService.getNotice(firstNotice.getNoticeId()); // 게시글 조회 로직
        }

        Notice find = noticeRepository.findById(firstNotice.getNoticeId()).get();
        assertThat(find.getViewCount()).isEqualTo(10);
    }

    @DisplayName("게시글 등록 시, 유저, 카테고리도 매핑된다")
    @Test
    void notice_post_with_user_and_category() {
        noticeRepository.deleteAll();
        User user = User.builder()
                .name("테스트이름")
                .email("test@test.com")
                .build();

        Category category = Category.builder()
                .categoryName("공지사항")
                .depth(0)
                .build();

        userRepository.save(user);
        categoryRepository.save(category);

        createNotice(user, category);

        List<Notice> notices = noticeRepository.findAll();
        Notice firstNotice = notices.get(0);

        Notice find = noticeService.getNotice(firstNotice.getNoticeId());

        assertThat(find.getUser().getEmail()).isEqualTo("test@test.com");
        assertThat(find.getCategory().getCategoryName()).isEqualTo("공지사항");
    }
}