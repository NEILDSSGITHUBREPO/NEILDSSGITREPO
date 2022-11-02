CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE actors.actor(id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4()
						 , first_name VARCHAR(50)
						 , last_name VARCHAR(50)
					     , gender VARCHAR(1)
						 , age smallint);
						 
REATE TABLE actors.actor_movie(acid UUID NOT NULL
						 , mvid UUID NOT NULL
						 , CONSTRAINT FK_actor FOREIGN KEY (acid) REFERENCES actors.actor(id)
						 , CONSTRAINT FK_movie FOREIGN KEY (mvid) REFERENCES movs.movies(id));
						 