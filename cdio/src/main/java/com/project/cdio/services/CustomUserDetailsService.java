//package com.project.cdio.services;
//
//import com.project.cdio.config.CustomUserDetails;
//import com.project.cdio.entities.UserEntity;
//import com.project.cdio.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByPhoneNumber(phone)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
//        return new CustomUserDetails(
//                user.getUserId(),
//                user.getPhoneNumber(),
//                user.getPassword(),
//                user.getAuthorities()
//        );
//    }
//}
