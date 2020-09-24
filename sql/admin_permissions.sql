INSERT INTO `admin_permissions`(`id`, `name`, `slug`, `http_method`, `http_path`, `created_at`, `updated_at`) VALUES (1, 'All permission', '*', '', '*', NULL, NULL);
INSERT INTO `admin_permissions`(`id`, `name`, `slug`, `http_method`, `http_path`, `created_at`, `updated_at`) VALUES (2, 'Dashboard', 'dashboard', 'GET', '/', NULL, NULL);
INSERT INTO `admin_permissions`(`id`, `name`, `slug`, `http_method`, `http_path`, `created_at`, `updated_at`) VALUES (3, 'Login', 'auth.login', '', '/auth/login\r\n/auth/logout', NULL, NULL);
INSERT INTO `admin_permissions`(`id`, `name`, `slug`, `http_method`, `http_path`, `created_at`, `updated_at`) VALUES (4, 'User setting', 'auth.setting', 'GET,PUT', '/auth/setting', NULL, NULL);
INSERT INTO `admin_permissions`(`id`, `name`, `slug`, `http_method`, `http_path`, `created_at`, `updated_at`) VALUES (5, 'Auth management', 'auth.management', '', '/auth/roles\r\n/auth/permissions\r\n/auth/menu\r\n/auth/logs', NULL, NULL);
