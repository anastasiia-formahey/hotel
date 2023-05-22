set global event_scheduler = ON;
create event checkRequest
    on schedule every 1 day
    do update request inner join application on request.application_id = application.id
    join occupancy_of_room on request.room_id=occupancy_of_room.room_id and request.check_in_date = occupancy_of_room.check_in_date
       set request.status_id=11, application.status_id=11, occupancy_of_room.status_id=11
       where request.status_id=8
         and creating_date <= DATE_ADD(CURDATE(), INTERVAL 2 DAY);