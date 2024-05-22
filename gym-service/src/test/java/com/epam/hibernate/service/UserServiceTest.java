package com.epam.hibernate.service;

import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.RoleEnum;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void authenticateOk() throws AuthenticationException {
        when(userRepository.authenticate(any(String.class), any(String.class))).thenReturn(true);

        LoginDTO loginDTO = new LoginDTO("username", "password");
        ResponseEntity<?> responseEntity = userService.authenticate(loginDTO);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals("User authenticated successfully", responseEntity.getBody());

        verify(userRepository, times(1)).authenticate("username", "password");
    }


    private User createMockUser() {
        return new User("username", "password", true, RoleEnum.ADMIN);
    }
}
