set global event_scheduler = ON;
create event checkBooking
    on schedule every 1 day
    do
    update booking set status_id=11
    where booking_expiration_date <= DATE(CURDATE())
        and status_id =2 or status_id=8
