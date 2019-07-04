CREATE TABLE user_following (
    user_id int(11)     NOT NULL,
    following_user_id int(11) NOT NULL,
	PRIMARY KEY (user_id, following_user_id),
	CONSTRAINT `FK_EMP` FOREIGN KEY (user_id) REFERENCES user(user_id),
	CONSTRAINT `FK_COL` FOREIGN KEY (following_user_id) REFERENCES user(user_id)
);