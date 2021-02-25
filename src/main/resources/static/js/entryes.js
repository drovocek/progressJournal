const ctx = {
    ajaxUrl: "/psjournal/entryes",
    updateTable: () => {
        ajaxApi.getAll(ctx.ajaxUrl)
            .done(updateTableByData);
    },
    fillForm: (dataSet) => {
        $('#objId').val(dataSet.id);
        $('#subjectId option[value="' + dataSet.subject.id + '"]').prop('selected', true);
        $('#studentId option[value="' + dataSet.student.id + '"]').prop('selected', true);
        $('#mark option[value="' + dataSet.mark + '"]').prop('selected', true);
        $('#markDate').val(dataSet.markDate);
    },
    checkboxData: [
        {
            url: '/psjournal/students',
            concatFields: ['lastName', 'firstName', 'patronymic'],
            id: '#studentId'
        },
        {
            url: '/psjournal/subjects',
            concatFields: ['name'],
            id: '#subjectId'
        },
    ],
    datatableOpts: {
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
    }
};

$(function () {
    makeEditable(ctx.datatableOpts);
});

$('#marksTab').addClass('active');
