package com.startrip.core.entity.user;

import com.startrip.core.constant.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true) // 중복 이메일이 안되도록 설정
    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

    @Enumerated(EnumType.STRING)
    private Role authorities;

    public User update(String name, String picture) {
        this.name = name;
        this.picture_url = picture;

        return this;
    }

}
