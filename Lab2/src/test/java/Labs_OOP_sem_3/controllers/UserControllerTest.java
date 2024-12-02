package Labs_OOP_sem_3.controllers;
/*
import Labs_OOP_sem_3.dto.AuthenticationRequest;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import Labs_OOP_sem_3.service.CustomUserDetailsService;
import Labs_OOP_sem_3.service.JwtUtil;
import Labs_OOP_sem_3.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private JwtUtil jwtUtil;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity(1, "testuser", "password");
    }

    @Test
    void GetUserByNameSuccess() {
        when(userService.findUserByUsername("testuser")).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.getUserByName("testuser");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).findUserByUsername("testuser");
    }

    @Test
    public void testGetUserByName_NotFound() {
        when(userService.findUserByUsername("unknownuser")).thenReturn(null);

        ResponseEntity<UserEntity> response = userController.getUserByName("unknownuser");

        assertEquals(404, response.getStatusCodeValue());
        verify(userService).findUserByUsername("unknownuser");
    }

    @Test
    public void testGetUserById_Success() {
        when(userService.findUserById(1)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.getUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).findUserById(1);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userService.findUserById(2)).thenReturn(null);

        ResponseEntity<UserEntity> response = userController.getUserById(2);

        assertEquals(404, response.getStatusCodeValue());
        verify(userService).findUserById(2);
    }

    /*
    @Test
    public void testRegisterUser() {
        String encodedPassword = passwordEncoder.encode("password");
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        String response = userController.registerUser(user);

        assertEquals("User registered successfully", response);
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }



    @Test
    public void testLoginUser() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("testuser", "password");
        UserDetails userDetails = org.mockito.Mockito.mock(UserDetails.class);
        String token = "jwtToken";

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(customUserDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        String response = userController.loginUser(authenticationRequest);

        assertEquals(token, response);
        verify(authenticationManager).authenticate(any());
        verify(customUserDetailsService).loadUserByUsername("testuser");
        verify(jwtUtil).generateToken(userDetails);
    }

    @AfterEach
    void delete() {
        userRepository.deleteById(1);
    }
}
*/