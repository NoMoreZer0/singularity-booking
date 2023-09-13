# Singularity Management
REST API for booking rooms for different time slots.

Rooms information are added at the start via migrations. H2 database used for demo purposes.

Email notifications are disabled for demo purposes

Below table for all rest API options:

| TYPE   | PATH                                     | DESCRIPTION                                                           |
|--------|------------------------------------------|-----------------------------------------------------------------------| 
| GET    | /healthcare                              | check that application is running                                     |
| POST   | /register                                | register user                                                         |
| POST   | /login                                   | login user                                                            |
| GET    | /user/${userId}                          | get user profile                                                      |
| GET    | /room                                    | get all rooms                                                         |
| GET    | /room/${roomNumber}                      | get information of specific room                                      |
| PATCH  | /reservation/${roomNumber}               | add reservation to the room with roomNumber                           |
| GET    | /reservation/${roomNumber}               | get all reservations by room number                                   |
| GET    | /reservation/my                          | get all user reservations                                             |
| DELETE | /reservation/${roomNumber}/${timeslotID} | delete user timeslot from the room                                    |
| POST | /reservation/date                        | get all reservations by date in the given time line                   |
| POST | /register/date/${roomNumber}             | get all reservations by date in the given time line in the given room |

More specific information about routes can be found through swagger.

