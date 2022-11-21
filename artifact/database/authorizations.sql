CREATE SCHEMA IF NOT EXISTS authorizations;

--base table
CREATE TABLE IF NOT EXISTS authorizations.ROLES(id BIGSERIAL PRIMARY KEY, name VARCHAR(25));
CREATE TABLE IF NOT EXISTS authorizations.PERMISSIONS(id BIGSERIAL PRIMARY KEY, name VARCHAR(25));
CREATE TABLE IF NOT EXISTS authorizations.RESOURCES(id BIGSERIAL PRIMARY KEY, name VARCHAR(25), path VARCHAR(50));
CREATE TABLE IF NOT EXISTS authorizations.ACTIONS(id BIGSERIAL PRIMARY KEY, value VARCHAR(8));

--joining table
CREATE TABLE IF NOT EXISTS authorizations.USER_ROLE(user_id uuid, role_id INTEGER
					   , CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES auths.user(id)
					   , CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES authorizations.roles(id));
CREATE TABLE IF NOT EXISTS authorizations.ROLE_PERMISSIONS(role_id INTEGER, permission_id INTEGER
					   , CONSTRAINT permission_fk FOREIGN KEY (permission_id) REFERENCES authorizations.permissions(id)
					   , CONSTRAINT role_fk FOREIGN KEY(role_id) REFERENCES authorizations.roles(id));
CREATE TABLE IF NOT EXISTS authorizations.PERMISSION_RESOURCES(permission_id INTEGER, resource_id INTEGER
					   , CONSTRAINT permission_fk FOREIGN KEY (permission_id) REFERENCES authorizations.permissions(id)
					   , CONSTRAINT resource_fk FOREIGN KEY (resource_id) REFERENCES authorizations.resources(id));
CREATE TABLE IF NOT EXISTS authorizations.PERMISSION_ACTIONS(permission_id INTEGER, action_id INTEGER
						, CONSTRAINT permission_fk FOREIGN KEY (permission_id) REFERENCES authorizations.permissions(id)
					   , CONSTRAINT action_fk FOREIGN KEY (action_id) REFERENCES authorizations.actions(id));
					   
--populate roles
INSERT INTO authorizations.ROLES(name) VALUES('ADMIN');
INSERT INTO authorizations.ROLES(name) VALUES('GUEST');

--populate permissions
INSERT INTO authorizations.PERMISSIONS(name) VALUES('PERMIT_ALL');
INSERT INTO authorizations.PERMISSIONS(name) VALUES('PERMIT_READ');
INSERT INTO authorizations.PERMISSIONS(name) VALUES('PERMIT_MOVIE');

--populate RESOURCES
INSERT INTO authorizations.RESOURCES(name, path) VALUES('login-service', '/api/v1/auth');
INSERT INTO authorizations.RESOURCES(name, path) VALUES('movie-service', '/api/v1/movie');
INSERT INTO authorizations.RESOURCES(name, path) VALUES('actor-service', '/api/v1/actor');
INSERT INTO authorizations.RESOURCES(name, path) VALUES('review-service', '/api/v1/review');
INSERT INTO authorizations.RESOURCES(name, path) VALUES('file-service', '/api/v1/file');

--populate ACTIONS
INSERT INTO authorizations.ACTIONS(value) VALUES('POST');
INSERT INTO authorizations.ACTIONS(value) VALUES('PUT');
INSERT INTO authorizations.ACTIONS(value) VALUES('DELETE');
INSERT INTO authorizations.ACTIONS(value) VALUES('GET');

--populate ROLE_PERMISSIONS
--ADMIN
INSERT INTO authorizations.ROLE_PERMISSIONS VALUES (1, 1); -- PERMI ALL
--GUEST
INSERT INTO authorizations.ROLE_PERMISSIONS VALUES (2, 2); -- PERMI READ
--poulate PERMISSION_RESOURCES
--permit all
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (1, 1); --login-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (1, 2); --movie-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (1, 3); --actor-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (1, 4); --review-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (1, 5); --file-service
--permit read
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (2, 1); --login-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (2, 2); --movie-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (2, 3); --actor-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (2, 4); --review-service
INSERT INTO authorizations.PERMISSION_RESOURCES VALUES (2, 5); --file-service

select * from authorizations.permission_resources where permission_id = 1;
--populate PERMISSION_ACTIONS
--permit all
INSERT INTO authorizations.PERMISSION_ACTIONS VALUES (1, 1); -- POST
INSERT INTO authorizations.PERMISSION_ACTIONS VALUES (1, 2); -- PUT
INSERT INTO authorizations.PERMISSION_ACTIONS VALUES (1, 3); -- DELETE
INSERT INTO authorizations.PERMISSION_ACTIONS VALUES (1, 4); -- GET
--permit READ
INSERT INTO authorizations.PERMISSION_ACTIONS VALUES (2, 4); -- GET