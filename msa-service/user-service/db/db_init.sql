CREATE TABLE IF NOT EXISTS `user` (
                                      `user_id` bigint AUTO_INCREMENT NOT NULL UNIQUE,
                                      `email` varchar(255) NOT NULL,
                                      `name` varchar(255) NOT NULL,
                                      `password` varchar(255) NOT NULL,
                                      `status` varchar(20) NOT NULL,
                                      `activity_score` int NOT NULL,
                                      `role` varchar(10) NOT NULL,
                                      `created_at` datetime NOT NULL,
                                      `updated_at` datetime NOT NULL,
                                      PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `user_profile` (
                                              `user_profile_id` bigint NOT NULL UNIQUE,
                                              `user_id` bigint NOT NULL,
                                              `profile_image_url` varchar(255) NOT NULL,
                                              `self_intro` varchar(255) NOT NULL,
                                              `phone_no` varchar(12) NOT NULL,
                                              `birth_date` date NOT NULL,
                                              `created_at` datetime NOT NULL,
                                              `updated_at` datetime NOT NULL,
                                              PRIMARY KEY (`user_profile_id`)
);

CREATE TABLE IF NOT EXISTS `user_login_history` (
                                                    `user_login_history_id` bigint NOT NULL UNIQUE,
                                                    `user_id` bigint NOT NULL,
                                                    `ip_address` varchar(255) NOT NULL,
                                                    `login_at` datetime NOT NULL,
                                                    PRIMARY KEY (`user_login_history_id`)
);

CREATE TABLE IF NOT EXISTS `user_token` (
                                            `user_token_id` bigint NOT NULL UNIQUE,
                                            `user_id` bigint NOT NULL,
                                            `refresh_token` varchar(255) NOT NULL,
                                            `expired_at` datetime NOT NULL,
                                            PRIMARY KEY (`user_token_id`)
);


CREATE TABLE boards (
    board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE INDEX idx_board_user ON boards(user_id);
CREATE INDEX idx_comment_board ON comments(board_id);

CREATE TABLE point_accounts (
    user_id BIGINT PRIMARY KEY,
    balance INT NOT NULL DEFAULT 0,
    updated_at DATETIME NOT NULL
);

CREATE TABLE point_transactions (
    point_transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount INT NOT NULL,
    type VARCHAR(20) NOT NULL,
    reference_id BIGINT,
    created_at DATETIME NOT NULL
);