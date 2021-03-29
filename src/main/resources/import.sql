CREATE TABLE IF NOT EXISTS st_users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  patronymic VARCHAR(250) DEFAULT NULL,
  password VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  is_active BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO st_users (username, first_name, last_name, patronymic, password, email, is_active) VALUES
  ('admin', 'DevVault', 'Admin', 'Victorovich', '$2y$12$RnxVw2Q0Hmt04EFDWhuG2.CT8XS21na3.FPVakS5doQO6TPWX72Ey', 'admin@devvault.ru', TRUE);