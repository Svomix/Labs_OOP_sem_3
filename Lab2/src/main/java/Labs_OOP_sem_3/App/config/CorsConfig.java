//package Labs_OOP_sem_3.App.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Разрешить CORS для всех эндпоинтов
//                .allowedOrigins("http://localhost:5173") // Разрешенный источник (ваш клиент)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные HTTP-методы
//                .allowedHeaders("*") // Разрешенные заголовки
//                .allowCredentials(true); // Разрешить отправку куки и авторизационных данных
//    }
//}