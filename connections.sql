DROP TABLE IF EXISTS line_segs_num_of_pass;
DROP TABLE IF EXISTS line_segment;
DROP TABLE IF EXISTS lines_starting_times;
DROP TABLE IF EXISTS line;
DROP TABLE IF EXISTS stop;


CREATE TABLE stop(
    name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE line(
    line_name VARCHAR(50) PRIMARY KEY,
    first_stop VARCHAR(50) NOT NULL REFERENCES stop ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE,
    num_of_line_segs integer NOT NULL
);

CREATE TABLE line_segment(
    place integer,
    time_diff integer NOT NULL,
    capacity integer NOT NULL,
    line_name VARCHAR(50) NOT NULL REFERENCES line ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE,
    next_stop VARCHAR(50) NOT NULL REFERENCES stop ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE,
    PRIMARY KEY (place, line_name)
);

CREATE TABLE lines_starting_times(
    line_name VARCHAR(50) REFERENCES line ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE,
    starting_time integer,
    PRIMARY KEY (line_name, starting_time)
);

CREATE TABLE line_segs_num_of_pass(
    id serial PRIMARY KEY,
    place integer NOT NULL,
    num_of_pass integer NOT NULL,
    starting_time integer NOT NULL,
    line_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (place, line_name) REFERENCES line_segment (place, line_name) ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE,
    FOREIGN KEY (line_name, starting_time) REFERENCES lines_starting_times (line_name, starting_time) ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE
);

insert into stop values ('stop1');
insert into stop values ('stop2');
insert into stop values ('stop3');
insert into stop values ('stop4');
insert into stop values ('stop5');
insert into stop values ('stop6');
insert into stop values ('stop7');
insert into stop values ('stop8');
insert into stop values ('stop9');
insert into stop values ('stop10');


insert into line values ('line1', 'stop1', 5);
insert into line values ('line2', 'stop2', 4);
insert into line values ('line3', 'stop3', 4);


insert into lines_starting_times values ('line1', 1);

insert into lines_starting_times values ('line2', 1);
insert into lines_starting_times values ('line2', 2);

insert into lines_starting_times values ('line3', 1);
insert into lines_starting_times values ('line3', 4);


insert into line_segment values (0, 2, 5, 'line1', 'stop2');
insert into line_segment values (1, 3, 5, 'line1', 'stop3');
insert into line_segment values (2, 2, 5, 'line1', 'stop4');
insert into line_segment values (3, 1, 5, 'line1', 'stop5');
insert into line_segment values (4, 2, 5, 'line1', 'stop6');

insert into line_segment values (0, 2, 5, 'line2', 'stop4');
insert into line_segment values (1, 2, 5, 'line2', 'stop6');
insert into line_segment values (2, 8, 5, 'line2', 'stop8');
insert into line_segment values (3, 4, 5, 'line2', 'stop10');

insert into line_segment values (0, 2, 5, 'line3', 'stop2');
insert into line_segment values (1, 1, 5, 'line3', 'stop5');
insert into line_segment values (2, 2, 5, 'line3', 'stop8');
insert into line_segment values (3, 3, 5, 'line3', 'stop9');


insert into line_segs_num_of_pass values (default, 0, 0, 1, 'line1');
insert into line_segs_num_of_pass values (default, 1, 0, 1, 'line1');
insert into line_segs_num_of_pass values (default, 2, 0, 1, 'line1');
insert into line_segs_num_of_pass values (default, 3, 0, 1, 'line1');
insert into line_segs_num_of_pass values (default, 4, 0, 1, 'line1');

insert into line_segs_num_of_pass values (default, 0, 0, 1, 'line2');
insert into line_segs_num_of_pass values (default, 1, 0, 1, 'line2');
insert into line_segs_num_of_pass values (default, 2, 0, 1, 'line2');
insert into line_segs_num_of_pass values (default, 3, 0, 1, 'line2');

insert into line_segs_num_of_pass values (default, 0, 0, 2, 'line2');
insert into line_segs_num_of_pass values (default, 1, 0, 2, 'line2');
insert into line_segs_num_of_pass values (default, 2, 0, 2, 'line2');
insert into line_segs_num_of_pass values (default, 3, 0, 2, 'line2');

insert into line_segs_num_of_pass values (default, 0, 0, 1, 'line3');
insert into line_segs_num_of_pass values (default, 1, 0, 1, 'line3');
insert into line_segs_num_of_pass values (default, 2, 0, 1, 'line3');
insert into line_segs_num_of_pass values (default, 3, 0, 1, 'line3');

insert into line_segs_num_of_pass values (default, 0, 0, 4, 'line3');
insert into line_segs_num_of_pass values (default, 1, 0, 4, 'line3');
insert into line_segs_num_of_pass values (default, 2, 0, 4, 'line3');
insert into line_segs_num_of_pass values (default, 3, 0, 4, 'line3');