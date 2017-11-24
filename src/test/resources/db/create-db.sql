drop TABLE IF EXISTS country;
CREATE TABLE country (
  country_id integer NOT NULL,
  name varchar(256) NOT NULL,
  PRIMARY KEY (country_id)
) ;


drop TABLE IF EXISTS genre;
CREATE TABLE genre (
  genre_id integer NOT NULL ,
  name varchar(256) NOT NULL,
  PRIMARY KEY (genre_id)
);
drop TABLE IF EXISTS movie;
CREATE TABLE movie (
  movie_id integer auto_increment NOT NULL ,
  name_Russian varchar(256) NOT NULL,
  name_Native varchar(256) NOT NULL,
  year_Of_Release integer NOT NULL,
  description varchar(5000) NOT NULL,
  rating double NOT NULL,
  price double NOT NULL,
  PICTURE_PATH varchar(5000),
  PRIMARY KEY (movie_id)
);
drop TABLE IF EXISTS movie_country;
CREATE TABLE movie_country (
  movie_id integer DEFAULT NULL,
  country_id integer DEFAULT NULL
);
drop TABLE IF EXISTS movie_genre;
CREATE TABLE movie_genre (
  movie_id integer DEFAULT NULL,
  genre_id integer DEFAULT NULL
);

drop TABLE IF EXISTS reviews;
CREATE TABLE reviews (
  review_id integer NOT NULL auto_increment,
  movie_id integer  DEFAULT NULL,
  text varchar(5000)  DEFAULT NULL,
  user_id integer  DEFAULT NULL,
  PRIMARY KEY (review_id)
);


drop TABLE IF EXISTS users;
CREATE TABLE users (
  user_id integer NOT NULL ,
  user_name varchar(256) NOT NULL,
  user_email varchar(256) NOT NULL,
  password varchar(256) NOT NULL,
  PRIMARY KEY (user_id)
);

drop TABLE IF EXISTS role;
CREATE TABLE role (
  role_id INT NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (role_id));

drop TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id));

