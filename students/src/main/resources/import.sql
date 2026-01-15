-- Dummy student data for development
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (1, 'Max', 'Mustermann', 'max.mustermann@thws.de', '000001234', '2023-10-01');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (2, 'Anna', 'Schmidt', 'anna.schmidt@thws.de', '000002345', '2023-10-01');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (3, 'Tom', 'Müller', 'tom.mueller@thws.de', '000003456', '2024-03-15');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (4, 'Laura', 'Weber', 'laura.weber@thws.de', '000004567', '2024-03-15');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (5, 'Jonas', 'Hoffmann', 'jonas.hoffmann@thws.de', '000005678', '2023-10-01');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (6, 'Sophie', 'Becker', 'sophie.becker@thws.de', '000006789', '2024-10-01');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (7, 'Felix', 'Wagner', 'felix.wagner@thws.de', '000007890', '2024-10-01');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (8, 'Emma', 'Fischer', 'emma.fischer@thws.de', '000008901', '2025-03-15');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (9, 'Lukas', 'Schneider', 'lukas.schneider@thws.de', '000009012', '2025-03-15');
INSERT INTO Student (id, firstName, lastName, email, matriculationNumber, enrollmentDate) VALUES (10, 'Marie', 'Koch', 'marie.koch@thws.de', '000010123', '2025-10-01');

SELECT setval('Student_SEQ', 100, true);


INSERT INTO Course (id, name, capacity) VALUES ('1', 'Cloud Native Enterprise Java', 20);
INSERT INTO Course (id, name, capacity) VALUES ('2', 'Datenbank Grundlagen', 100);

SELECT setval('Course_SEQ', 100, true);

INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (1, 1, 1, null);
INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (2, 1, 4, null);
INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (3, 1, 5, null);
INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (4, 1, 9, null);

INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (5, 2, 1, null);
INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (6, 2, 4, null);
INSERT INTO CourseParticipant (id, course_id, student_id, grade) VALUES (7, 2, 8, null);

SELECT setval('CourseParticipant_SEQ', 100, true);