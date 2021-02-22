// const subjAjaxUrl = "psjournal/subjects/";
// const studAjaxUrl = "psjournal/students/";

const concatFields = {
    stud: ['lastName', 'firstName', 'patronymic'],
    subj: ['name'],
};

const ctx = {
    ajaxUrl: "psjournal/entryes/",
    updateTable: () => {
        ajaxApi.getAll(ctx.ajaxUrl)
            .done(updateTableByData);
    },
    checkboxData: [
        {
            url: 'psjournal/students/',
            concatFields: ['lastName', 'firstName', 'patronymic'],
            id: '#studentId'
        },
        {
            url: 'psjournal/subjects/',
            concatFields: ['name'],
            id: '#subjectId'
        },
    ]
};

$(function () {
    makeEditable({
        "columns": [
            {
                "title": "entryId",
                "data": "id"
            },
            {
                "title": "studentId",
                "data": "student.id"
            },
            {
                "title": "subjectId",
                "data": "subject.id"
            },
            {
                "title": "First Name",
                "data": "student.firstName"
            },
            {
                "title": "Last Name",
                "data": "student.lastName"
            },
            {
                "title": "Patronymic",
                "data": "student.patronymic"
            },
            {
                "title": "Subject",
                "data": "subject.name"
            },
            {
                "title": "Mark",
                "data": "mark"
            },
            {
                "title": "Date",
                "data": "markDate"
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        columnDefs: [
            {
                targets: [0, 1, 2],
                visible: false,
                searchable: false
            }
        ],
    });
});