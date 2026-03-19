package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.board.BoardRepository;
import com.example.demo.reply.ReplyRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void join(UserRequest.Join joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user);
    }

    public User login(UserRequest.Login loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    @Transactional
    public User 회원수정(int id, UserRequest.Update updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        user.setEmail(updateDTO.getEmail());

        return user;
    }

    @Transactional
    public void 회원탈퇴(int id) {
        replyRepository.deleteByUserId(id);
        boardRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

}

