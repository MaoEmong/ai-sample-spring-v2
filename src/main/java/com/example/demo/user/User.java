package com.example.demo.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user_tb")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;
    private String email;

    // 주소 관련 필드 추가
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // RULE: 컬렉션은 생성자에 넣지 않는다.
    @Builder
    public User(Integer id, String username, String password, String email, String postcode, String address, String detailAddress, String extraAddress, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
        this.createdAt = createdAt;
    }

}
