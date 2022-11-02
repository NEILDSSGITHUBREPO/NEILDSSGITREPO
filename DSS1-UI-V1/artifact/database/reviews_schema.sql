CREATE SCHEMA IF NOT EXISTS reviews;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE reviews.review (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    description TEXT NOT NULL,
    date_posted character varying(25) NOT NULL,
    rating numeric(1,0) DEFAULT 1,
    added_by uuid NOT NULL,
    reviewed_movie uuid NOT NULL,
	CONSTRAINT fk_movie FOREIGN KEY(reviewed_movie) REFERENCES movs.MOVIES(id),
	CONSTRAINT fk_user FOREIGN KEY(added_by) REFERENCES auths.user(id)
);

select * from auths.user
select * from reviews.review;