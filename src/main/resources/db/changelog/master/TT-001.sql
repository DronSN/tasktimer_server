--Task statuses: started, paused, stopped
CREATE TABLE ttmr.task_status (
                                     task_status_id    integer  PRIMARY KEY ,
                                     name                  varchar(30) NOT NULL
);
INSERT INTO ttmr.task_status VALUES(1,'started'),(2,'paused'),(3,'stopped');

--Task colores: green, yellow, indigo, pink, purple
CREATE TABLE ttmr.task_color (
                                  task_color_id    integer  PRIMARY KEY ,
                                  name                  varchar(30) NOT NULL
);
INSERT INTO ttmr.task_color VALUES(1,'green'),(2,'yellow'),(3,'indigo'),(4,'pink'),(5,'purple');

--tags
CREATE SEQUENCE ttmr.tag_seq;
CREATE TABLE ttmr.tag (
                           tag_id            integer  PRIMARY KEY DEFAULT nextval('ttmr.tag_seq'),
                           name               varchar(256) NOT NULL,
                           datetime_creation  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER SEQUENCE ttmr.tag_seq OWNED BY ttmr.tag.tag_id;

CREATE TABLE ttmr.tag_task (
                           tag_id            integer NOT NULL ,
                           task_id           integer NOT NULL
);
ALTER TABLE ttmr.tag_task
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES ttmr.tag (tag_id);

--Tasks
CREATE SEQUENCE ttmr.task_seq;
CREATE TABLE ttmr.task (
                                    task_id            integer  PRIMARY KEY DEFAULT nextval('ttmr.task_seq'),
                                    name               varchar(256) NOT NULL,
                                    description        text,
                                    task_color_id      integer,
                                    task_status_id     integer NOT NULL,
                                    start              timestamp,
                                    stop               timestamp,
                                    datetime_creation  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER SEQUENCE ttmr.task_seq OWNED BY ttmr.task.task_id;

ALTER TABLE ttmr.task
    ADD CONSTRAINT fk_task_color FOREIGN KEY (task_color_id) REFERENCES ttmr.task_color (task_color_id);

ALTER TABLE ttmr.task
    ADD CONSTRAINT fk_task_status FOREIGN KEY (task_status_id) REFERENCES ttmr.task_status (task_status_id);

ALTER TABLE ttmr.tag_task
    ADD CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES ttmr.task (task_id);
