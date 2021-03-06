package com.estate.service.impl;

import com.estate.dto.MyUserDetail;
import com.estate.dto.RoleDTO;
import com.estate.dto.UserDTO;
import com.estate.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findOneByUserNameAndStatus(username, 1);
        if (userDTO == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getCode()));
        }
        MyUserDetail myUserDetail =  new MyUserDetail(username , userDTO.getPassword(), true,true,true,true, authorities);
        BeanUtils.copyProperties(userDTO,myUserDetail);
        return myUserDetail;
    }
}
