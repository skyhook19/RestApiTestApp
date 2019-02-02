INSERT INTO department(name, date_of_creation)
VALUES ('Apple Head Department', '1992-06-01');

INSERT INTO department(name, date_of_creation, parent_id)
VALUES
('Marketing Department', '1993-07-05', 1),
('Development Department', '1993-06-14', 1),
('Sales Department', '1993-07-27', 1);

INSERT INTO department(name, date_of_creation, parent_id)
VALUES
('Youtube Marketing Department', '2009-11-24', 2),
('Radio Marketing Department', '1994-06-11', 2),
('TV Marketing Department', '1993-08-01', 2);

INSERT INTO department(name, date_of_creation, parent_id)
VALUES
('MacOS Development Department', '1996-03-29', 3),
('IOS Development Department', '2007-09-02', 3),
('Apple Music Development Department', '2013-09-14', 3);

INSERT INTO department(name, date_of_creation, parent_id)
VALUES
('Hardware Sales Department', '1994-01-06', 4),
('Software Sales Department', '1994-02-27', 4);
