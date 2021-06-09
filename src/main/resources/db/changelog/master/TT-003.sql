--view contains task duration info
CREATE VIEW ttmr.task_duration AS
    SELECT
        task.task_id id,
        task.name,
        task.description,
        task.tags,
        task.task_color_id color,
        task.task_status_id status,
        task.start,
        task.stop,
        COUNT(intervall.task_id) chunks,
        EXTRACT(EPOCH from sum(intervall.finish - intervall.start)) duration
    FROM
        (SELECT
             task.task_id,
             task.name,
             task.description,
             ARRAY_AGG(t.name) tags,
             task.task_color_id,
             task.task_status_id,
             task.start,
             task.stop
         FROM ttmr.task task
                  LEFT JOIN ttmr.tag_task tt on task.task_id = tt.task_id
                  LEFT JOIN ttmr.tag t on t.tag_id = tt.tag_id
         GROUP BY task.task_id) as task
    LEFT JOIN ttmr.task_interval as intervall on intervall.task_id = task.task_id
    GROUP BY task.name, task.task_id, task.tags, task.description, task.task_color_id,
             task.task_status_id, task.start, task.stop;
