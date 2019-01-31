# RestApiTestApp

This is a simple Spring Boot REST API project, using plain Hibernate as a JPA implementation and PostgreSQL as a database.

## SQL scripts for creating table structure

###Department table:
```
CREATE TABLE department(
	id serial PRIMARY KEY,
	name text UNIQUE NOT NULL,
	date_of_creation date NOT NULL CHECK(date_of_creation < Now()),
	parent_id integer REFERENCES department (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
```

###Employee table:
```
CREATE TYPE gen AS ENUM ('f', 'm');

CREATE TABLE employee(
	id serial PRIMARY KEY,
	first_name text NOT NULL,
	middle_name text,
	last_name text NOT NULL,
	gender gen NOT NULL,
	birth_date date NOT NULL CHECK(birth_date < Now()),
	joined_date date NOT NULL CHECK((joined_date > birth_date) AND (joined_date < Now())),
	discharge_date date CHECK(discharge_date > joined_date),
	phone varchar(30) NOT NULL UNIQUE CHECK(phone ~ '^[-0-9+ ()]+$'),
	email citext NOT NULL UNIQUE CHECK(email  ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'),
	salary numeric(15, 2) NOT NULL CHECK(salary > 0),
	position text NOT NULL,
	department_id integer NOT NULL REFERENCES department(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	is_head_of_department boolean NOT NULL DEFAULT false 
);
	
create UNIQUE INDEX on employee(department_id, is_head_of_department) 
where is_head_of_department = true;
```

##SQL scripts to add some data 

###Department table:
```
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
```

###Employee table:
```
INSERT INTO employee (first_name, middle_name, last_name, gender, birth_date, joined_date, discharge_date, phone, email, salary, position, department_id, is_head_of_department)
VALUES 
('Steve', NULL, 'Jobs', 'm', '1955-02-24', '1992-06-02', '2013-10-12', '(443)-123-543-753', 'stevejobs@apple.com', 1000000, 'The creator', 1, true),
('Steve', NULL, 'Wozniak', 'm', '1950-08-11', '1992-06-02', NULL, '(443)-123-543-002', 'stevewozniak@apple.com', 999999, 'CTO', 1, false),

('Tiertza', NULL, 'Monkley', 'f', '1975-05-03', '2000-02-03', NULL, '617-425-4952', 'tmonkley0@apple.com', 100000, 'Chief Marketing Officer', 2, true),
('Lucien', NULL, 'Singh', 'm', '1985-03-04', '2010-02-19', NULL, '596-519-9750', 'lsingh1@apple.com', 94578, 'Marketing Director', 2, false),
('Damien', NULL, 'McCurry', 'm', '1985-01-15', '2011-12-03', NULL, '111-207-7627', 'dmccurry2@apple.com', 90000, 'Youtube Marketing Specialist #1', 5, true), 
('Betti', NULL, 'Giacopelo', 'f', '1965-06-04', '2001-02-28', NULL, '260-612-9301', 'bgiacopelo3@apple.com', 45712, 'Youtube Marketing Specialist #2', 5, false),
('Desirae', NULL, 'McCrory', 'f', '1987-03-11', '1999-04-20', NULL, '132-198-2673', 'dmccrory4@apple.com', 90000, 'Radio Marketing Specialist #1', 6, true),
('Willdon', NULL, 'Hardstaff', 'm', '1988-08-20', '2014-05-02', NULL, '856-527-6623', 'whardstaff5@apple.com', 41257, 'Radio Marketing Specialist #2', 6, false),
('Vittoria', NULL, 'Brockett', 'f', '1988-04-02', '2000-04-05', NULL, '908-914-8393', 'vbrockett6@apple.com', 90000, 'TV Marketing Specialist #1', 7, true),
('Ernest', NULL, 'Cusworth', 'm', '1967-12-27', '1998-02-20', NULL, '481-328-2569', 'ecusworth7@apple.com', 56045, 'TV Marketing Specialist #2', 7, false),

('Crystal', NULL, 'Serle', 'f', '1973-07-11', '2005-09-19', NULL, '660-331-9423', 'cserle8@apple.com', 100000, 'Product Manager', 3, true),
('Windham', NULL, 'Dineges', 'm', '1986-11-13', '1998-11-26', NULL, '502-188-3513', 'wdineges9@apple.com', 92260, 'Technical Manager', 3, false),
('Emelita', NULL, 'Muris', 'f', '1973-05-19', '2011-05-16', NULL, '149-685-2727', 'emurisa@apple.com', 91000, 'MacOS Developer #1', 8, true),
('Janek', NULL, 'Barron', 'm', '1971-06-10', '2015-04-03', NULL, '838-552-2552', 'jbarronb@apple.com', 48751, 'MacOS Developer #2', 8, false),
('Pace', NULL, 'Jobke', 'm', '1991-02-18', '2011-03-05', NULL, '759-473-0095', 'pjobkeg@apple.com.com', 49154, 'MacOS Developer #3', 8, false),
('Belva', NULL, 'Haglington', 'f', '1988-01-20', '2009-01-13', NULL, '760-710-1966', 'bhaglingtonc@apple.com', 91000, 'IOS Developer #1', 9, true),
('Drucy', NULL, 'Duckitt', 'f', '1977-12-04', '2009-10-08', NULL, '146-860-2454', 'dduckittd@apple.com', 43458, 'IOS Developer #2', 9, false),
('Moss', NULL, 'Huglin', 'm', '1972-01-11', '2011-07-26', NULL, '758-473-0095', 'mhuglinh@apple.com', 45821, 'IOS Developer #3', 9, false),
('Bebe', NULL, 'Aisbett', 'f', '1962-04-02', '2013-08-26', NULL, '420-602-7049', 'baisbette@apple.com', 91000, 'Apple Music Developer #1', 10, true),
('Marlena', NULL, 'Goldsack', 'f', '1963-05-23', '1998-03-21', NULL, '408-444-8361', 'mgoldsackf@apple.com', 47278, 'Apple Music Developer #2', 10, false),

('Lothario', NULL, 'Udall', 'm', '1968-08-15', '1999-11-25', NULL, '162-518-6230', 'ludalli@apple.com', 100000, 'Chief Sales Officer', 4, true),
('Lauree', NULL, 'Garlant', 'f', '1981-05-16', '2003-06-27', NULL, '394-889-6758', 'lgarlantj@apple.com', 95424, 'Sales Director', 4, false),
('Staford', NULL, 'Hallt', 'm', '1971-06-11', '2006-10-06', NULL, '341-705-4236', 'shalltk@apple.com', 90000, 'Hardware Sales Manager', 11, true),
('Farra', NULL, 'Gercke', 'f', '1994-08-05', '2011-03-05', NULL, '724-284-2932', 'fgerckel@apple.com', 54812, 'Hardware Sales Specialist #1', 11, false),
('Felicia', NULL, 'Srutton', 'f', '1972-11-19', '1998-12-31', NULL, '389-584-5881', 'fsruttonm@apple.com', 90000, 'Software Sales Manager', 12, true),
('Mia', NULL, 'Blood', 'f', '1983-05-29', '2006-09-19', NULL, '616-653-3922', 'mbloodn@apple.com', 51457, 'Software Sales Specialist #1', 12, false);
```