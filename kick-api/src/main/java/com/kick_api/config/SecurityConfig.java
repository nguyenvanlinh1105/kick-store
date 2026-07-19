package com.kick_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration // Đánh dấu đây là một lớp cấu hình của Spring Boot
@EnableWebSecurity // Kích hoạt tính năng bảo mật web của Spring Security
@EnableMethodSecurity // Kích hoạt phân quyền chi tiết mức phương thức ở Controller bằng @PreAuthorize
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    /**
     * Cấu hình Filter Chain để kiểm soát luồng bảo mật của toàn bộ ứng dụng
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Tích hợp cấu hình CORS (Cross-Origin Resource Sharing) từ hàm corsConfigurationSource() bên dưới
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Vô hiệu hóa tính năng bảo vệ CSRF vì hệ thống sử dụng REST API (Stateless)
            .csrf(csrf -> csrf.disable())
            
            // 3. Tắt giao diện đăng nhập mặc định (Form Login) của Spring Security
            .formLogin(form -> form.disable())
            
            // 4. Tắt hộp thoại đăng nhập Basic Auth mặc định của trình duyệt khi bị chặn (tránh hiện pop-up bắt đăng nhập)
            .httpBasic(basic -> basic.disable())
            
            // 5. Cấu hình quy tắc phân quyền truy cập theo đường dẫn (URL matchers)
            .authorizeHttpRequests(auth -> auth
                // Đường dẫn chứa API công khai (Public)
                .requestMatchers("/api/v1/pub/**").permitAll()
                
                // Đường dẫn chứa các hành động của khách hàng (Customer)
                .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                
                // Đường dẫn chứa các hành động của ban quản trị (Admin)
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                // Đường dẫn chứa các hành động của nhân viên và quản trị (Staff & Admin)
                .requestMatchers("/api/v1/staff/**").hasAnyRole("ADMIN", "STAFF")
                
                // Các API còn lại bắt buộc phải đăng nhập (Authenticated) mới được gọi
                .anyRequest().authenticated()
            );

        return http.build();
    }

    /**
     * Cấu hình chi tiết bộ lọc CORS để cho phép ứng dụng Frontend (Vue 3) gọi được API sang Backend
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Cho phép các domain của Frontend gọi tới API lấy động từ file application.properties
        configuration.setAllowedOrigins(allowedOrigins);
        
        // Cho phép sử dụng tất cả các phương thức HTTP cần thiết
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Cho phép nhận tất cả các header (ví dụ: Authorization gửi token, Content-Type gửi JSON...)
        configuration.setAllowedHeaders(List.of("*"));
        
        // Cho phép client gửi kèm thông tin định danh như Cookie hay Token xác thực qua API
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Áp dụng tất cả cấu hình CORS trên cho mọi API trong hệ thống (/**)
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
