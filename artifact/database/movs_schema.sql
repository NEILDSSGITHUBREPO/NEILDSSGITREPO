CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--movies schema
CREATE SCHEMA IF NOT EXISTS movs;

CREATE TABLE IF NOT EXISTS movs.MOVIES(id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4()
				 	, title VARCHAR(100) NOT NULL
				    , budget NUMERIC(15,4) NOT NULL
					, income NUMERIC(15,4)
					, release_date DATE NOT NULL
					, image_link VARCHAR(255) NOT NULL
					, trailer_link VARCHAR(255)
					, maturity_rating SMALLINT NOT NULL
					, added_by UUID NOT NULL
				    , CONSTRAINT fk_user 
						FOREIGN KEY (added_by) REFERENCES auths.user(id));

CREATE TABLE IF NOT EXISTS movs.MOVIE_CATEGORY(mvid UUID NOT NULL
								, category VARCHAR(15) NOT NULL
								, CONSTRAINT fk_movie
								   FOREIGN KEY(mvid) REFERENCES movs.MOVIES(id));