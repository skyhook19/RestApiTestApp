CREATE TABLE salary_fund(
	id serial PRIMARY KEY,
	salary_fund numeric(15, 2) NOT NULL CHECK(salary_fund >= 0),
	date_of_record timestamp NOT NULL,
	department_id integer REFERENCES department (id) ON DELETE CASCADE ON UPDATE CASCADE
);