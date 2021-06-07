--Task interval
CREATE SEQUENCE ttmr.task_interval_seq;

CREATE TABLE ttmr.task_interval (
                                  task_interval_id    integer PRIMARY KEY  DEFAULT nextval('ttmr.task_interval_seq'),
                                  task_id             integer,
                                  start               timestamp NOT NULL,
                                  finish              timestamp
);

ALTER SEQUENCE ttmr.task_interval_seq OWNED BY ttmr.task_interval.task_interval_id;

ALTER TABLE ttmr.task_interval
    ADD CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES ttmr.task(task_id) ON DELETE CASCADE;
