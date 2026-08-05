package com.theloungemembers.web.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

//    private final AdminRepository adminRepository; // 어드민 DB 조회용 Repository

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 1. DB에서 어드민 계정 조회
//        Admin admin = adminRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("관리자 계정을 찾을 수 없습니다: " + username));
//
//        // 2. Spring Security 전용 UserDetails 객체로 변환하여 리턴
//        return User.builder()
//                .username(admin.getUsername())
//                .password(admin.getPassword()) // BCrypt 등으로 암호화된 비밀번호
//                .roles(admin.getRole().name()) // 예: "ADMIN"
//                .build();
//    }
}