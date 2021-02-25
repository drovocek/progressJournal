DELETE
FROM JOURNAL_ENTRY;
DELETE
FROM STUDENT;
DELETE
FROM SUBJECT;

ALTER TABLE STUDENT
    ALTER COLUMN ID RESTART WITH 10;
ALTER TABLE SUBJECT
    ALTER COLUMN ID RESTART WITH 10;
ALTER TABLE JOURNAL_ENTRY
    ALTER COLUMN ID RESTART WITH 10;

INSERT INTO STUDENT (ID, FIRST_NAME, LAST_NAME, PATRONYMIC)
VALUES (0, 'Harry', 'Potter', 'Jamesovich'),
       (1, 'Hermione', 'Granger', 'Hogusovna'),
       (2, 'Ronald', 'Weasley', 'Arturovich');

INSERT INTO SUBJECT (ID, NAME)
VALUES (0, 'Astronomy'),
       (1, 'Charms'),
       (2, 'Herbology'),
       (3, 'Arithmancy'),
       (4, 'Magical Theory');

INSERT INTO JOURNAL_ENTRY (ID, MARK, MARK_DATE, STUDENT_ID, SUBJECT_ID)
VALUES (0, 7, '1992-09-27', 0, 0),
       (1, 8, '1992-09-28', 0, 3),
       (2, 9, '1992-09-27', 0, 1),
       (3, 10, '1992-09-28', 1, 2),
       (4, 7, '1992-09-27', 1, 2),
       (5, 8, '1992-09-28', 1, 4);

