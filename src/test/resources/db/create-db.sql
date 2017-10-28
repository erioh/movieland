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
  movie_id integer NOT NULL ,
  name_Russian varchar(256) NOT NULL,
  name_Native varchar(256) NOT NULL,
  year_Of_Release integer NOT NULL,
  description varchar(5000) NOT NULL,
  rating double NOT NULL,
  price double NOT NULL,
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


drop TABLE IF EXISTS movie_poster;
CREATE TABLE movie_poster (
  movie_id integer DEFAULT NULL,
  picture_id integer DEFAULT NULL
);


drop TABLE IF EXISTS movie_reviews;
CREATE TABLE movie_reviews (
  movie_id integer DEFAULT NULL,
  review_id integer DEFAULT NULL
);


drop TABLE IF EXISTS poster;
CREATE TABLE poster (
  picture_id integer NOT NULL,
  picture_Path varchar(5000) NOT NULL,
  PRIMARY KEY (picture_id)
);


drop TABLE IF EXISTS reviews;
CREATE TABLE reviews (
  review_id integer NOT NULL ,
  text varchar(5000) NOT NULL,
  user_id integer NOT NULL,
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