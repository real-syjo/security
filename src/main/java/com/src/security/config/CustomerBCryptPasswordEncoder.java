package com.src.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomerBCryptPasswordEncoder extends BCryptPasswordEncoder {

}
