-- Thêm 2 cột refresh token trực tiếp vào bảng users
-- (Lưu ý: cách này hỗ trợ 1 phiên đăng nhập tại 1 thời điểm - đơn giản và phù hợp với app web)
ALTER TABLE users
    ADD COLUMN refresh_token VARCHAR(255),
    ADD COLUMN refresh_token_expiry TIMESTAMP WITH TIME ZONE;

CREATE INDEX idx_users_refresh_token ON users(refresh_token);
