DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS UsersRole CASCADE;
DROP TABLE IF EXISTS Currency CASCADE;
DROP TABLE IF EXISTS RateSource CASCADE;
DROP TABLE IF EXISTS Rate CASCADE;
DROP TABLE IF EXISTS Replenishment CASCADE;
DROP TABLE IF EXISTS Deposite CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS RateSource CASCADE;

CREATE TABLE IF NOT EXISTS Users (
  users_id SERIAL NOT NULL,
  users_name VARCHAR(32) NOT NULL,
  users_password TEXT NOT NULL,
  users_email VARCHAR(32) NOT NULL,
  PRIMARY KEY (users_id)
);

CREATE TABLE IF NOT EXISTS Role (
  role_id SERIAL NOT NULL,
  role_name VARCHAR(12) NOT NULL UNIQUE,
  PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS UsersRole (
  usersrole_user INT NOT NULL,
  usersrole_role INT NOT NULL,
  FOREIGN KEY (usersrole_user) REFERENCES Users(users_id),
  FOREIGN KEY (usersrole_role) REFERENCES Role(role_id)
);

CREATE TABLE IF NOT EXISTS RateSource (
  ratesource_id SERIAL NOT NULL,
  ratesource_name VARCHAR(20) NOT NULL,
  PRIMARY KEY (ratesource_id)
);

CREATE TABLE IF NOT EXISTS Currency (
  currency_id SERIAL NOT NULL,
  currency_name VARCHAR(6) NOT NULL UNIQUE,
  PRIMARY KEY (currency_id)
);

CREATE TABLE IF NOT EXISTS Rate(
  rate_id SERIAL NOT NULL,
  rate_currency INT NOT NULL,
  rate_rate DECIMAL NOT NULL,
  rate_date DATE NOT NULL,
  rate_ratesource INT NOT NULL,
  PRIMARY KEY (rate_id),
  FOREIGN KEY (rate_currency) REFERENCES Currency(currency_id),
  FOREIGN KEY (rate_ratesource) REFERENCES RateSource(ratesource_id)
);


CREATE TABLE IF NOT EXISTS Replenishment(
  replenishment_id SERIAL NOT NULL,
  replenishment_currency INT NOT NULL,
  replenishment_sum DECIMAL NOT NULL,
  replenishment_date date NOT NULL,
  PRIMARY KEY (replenishment_id),
  FOREIGN KEY (replenishment_currency) REFERENCES Currency(currency_id)
);

CREATE TABLE IF NOT EXISTS Deposite (
  deposite_id SERIAL NOT NULL,
  deposite_name VARCHAR(64) NOT NULL,
  deposite_date_start DATE NOT NULL,
  deposite_date_end DATE NOT NULL,
  deposite_currency INT NOT NULL,
  deposite_replenishment INT,
  deposite_sum DECIMAL NOT NULL,
  deposite_tax_on_percent DECIMAL NOT NULL,
  deposite_rate DECIMAL NOT NULL,
  deposite_user INT NOT NULL,
  PRIMARY KEY (deposite_id),
  FOREIGN KEY (deposite_replenishment) REFERENCES Replenishment(replenishment_id),
  FOREIGN KEY (deposite_currency) REFERENCES Currency(currency_id),
  FOREIGN KEY (deposite_user) REFERENCES Users(users_id)
);

ALTER TABLE Replenishment
  ADD COLUMN replenishment_deposite INT,
  ADD FOREIGN KEY (replenishment_deposite) REFERENCES Deposite(deposite_id)
;
ALTER TABLE Users
  ADD COLUMN users_deposite INT,
  ADD FOREIGN KEY (users_deposite) REFERENCES Deposite(deposite_id)
;
INSERT INTO Role (role_name) VALUES
  ('ROLE_ADMIN'),
  ('ROLE_USER')
;
INSERT INTO Users (users_name, users_password, users_email) VALUES ('sergeyg', '$2a$10$Vt5QkKovXyO63N5Xh1pC/OiEO38TeAO0.AIodw9h8x5H/6IIay1YW', 'sergeyg@fakemail.com');
INSERT INTO UsersRole (usersrole_user, usersrole_role) VALUES (1,2),(1,1);
INSERT INTO Currency (currency_name) VALUES ('UAH');

DROP INDEX IF EXISTS index_users_users_id;
CREATE INDEX index_users_users_id ON Users USING HASH (users_id);