package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.dto.UserDto;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletRequest request) {
        try {
            // Создаем объект аутентификации
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return "redirect:/home";
        } catch (Exception e) {
            // Если аутентификация не удалась, показываем ошибку
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";
        }
    }
        @PostMapping("/register")
        public ResponseEntity<String> registerUser (@RequestBody UserDto userDto){
            if (userRepository.findByUsername(userDto.getName()).isPresent()) {
                return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует");
            }

            UserEntity newUser = new UserEntity();
            newUser.setUserName(userDto.getName());
            userRepository.save(newUser);
            return ResponseEntity.ok("Пользователь зарегистрирован");
        }
    }
