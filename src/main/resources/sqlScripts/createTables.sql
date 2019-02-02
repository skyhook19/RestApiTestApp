CREATE TABLE department(
	id serial PRIMARY KEY,
	name text UNIQUE NOT NULL,
	date_of_creation date NOT NULL CHECK(date_of_creation < Now()),
	parent_id integer REFERENCES department (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

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