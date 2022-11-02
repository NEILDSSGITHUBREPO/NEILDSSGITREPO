CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--authentication schema
CREATE SCHEMA IF NOT EXISTS auths;

CREATE TABLE IF NOT EXISTS auths.user_info(id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4()
											 , phone_number VARCHAR(25) UNIQUE NOT NULL
										     , admin_name VARCHAR(25) NOT NULL);
											 
CREATE TABLE IF NOT EXISTS auths.user(id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4()
									  , email VARCHAR(25) UNIQUE NOT NULL
									  , password VARCHAR(255) NOT NULL
									  , uif UUID NOT NULL
									  , CONSTRAINT fk_uinfo
									 		FOREIGN KEY (uif) REFERENCES auths.user_info(id));
											
--QUERY
SELECT * FROM auths.user u inner join auths.user_info ui ON u.uif = ui.id;
											
