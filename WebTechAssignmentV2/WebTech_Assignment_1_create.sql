CREATE DATABASE IF NOT EXISTS WebTechAssignment;
USE WebTechAssignment;

DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS User;

-- Game table --
CREATE TABLE User (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(30) NOT NULL,
    `username` varchar(30) NOT NULL,
    `password` varchar(30) NOT NULL,
    `image` varchar(30),
    PRIMARY KEY(`id`)
);

CREATE TABLE Game (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `releaseDate` varchar(50) NOT NULL,
    `developer` varchar(50),
    `publisher` varchar(50),
    `engine` varchar(50),
    `genre` varchar(50),
    `platform` varchar(75),
    PRIMARY KEY(`id`)
);

INSERT INTO User VALUES (1, 'User1', 'user1', 'password1', 'generic.jpg');
INSERT INTO User VALUES (2, 'User2', 'user2', 'password2', 'generic.jpg');
INSERT INTO User VALUES (3, 'User3', 'user3', 'password3', 'generic.jpg');

INSERT INTO Game VALUES (1, 'Red Dead Redemption 2', '29/10/18', 'Rockstar Studios', 'Rockstar Games', 'RAGE', 'Action-Adventure', 'PS4, Xbox One');
INSERT INTO Game VALUES (2, 'God Of War', '20/04/18', 'SIE Santa Monica Studios', 'Sony Interactive Entertainment', 'N/A', 'Action-Adventure', 'PS4');
INSERT INTO Game VALUES (3, 'Divinity: Original Sin 2', '31/08/18', 'Larian Studios', 'Bandai Namco Entertainment', 'N/A', 'Role-Playing', 'PS4, Xbox One, Windows');
INSERT INTO Game VALUES (4, 'Shadow Of The Colossus', '07/02/18', 'SCE Japan Studio Team Ico', 'Sony Interactive Entertainment', 'Bluepoint Engine', 'Action-Adventure', 'PS4');
INSERT INTO Game VALUES (5, 'Celeste', '25/01/18', 'Matt Makes Games', 'Matt Makes Games', 'XNA', 'Platform', 'PS4, Xbox One, Windows, Nintendo Switch, Linux, macOS');

	