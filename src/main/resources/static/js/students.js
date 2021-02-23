const ctx = {
    ajaxUrl: "../students",
    updateTable: () => {
        ajaxApi.getAll(ctx.ajaxUrl)
            .done(updateTableByData);
    },
    fillForm: (dataSet) => {
        $('#objId').val(dataSet.id);
        $('#firstName').val(dataSet.firstName);
        $('#lastName').val(dataSet.lastName);
        $('#patronymic').val(dataSet.patronymic);
    },
    checkboxData: [],
    datatableOpts: {
        "columns": [
            {
                "title": "Id",
                "data": "id"
            },
            {
                "title": "First Name",
                "data": "firstName"
            },
            {
                "title": "Last Name",
                "data": "lastName"
            },
            {
                "title": "Patronymic",
                "data": "patronymic"
            },
            {
                "title": "AvgRating",
                "data": "averageRating"
            },
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        columnDefs: [
            {
                targets: [0],
                visible: false,
                searchable: false
            }
        ],
    }
};

$(function () {
    makeEditable(ctx.datatableOpts);
});

$('#studentsTab').addClass('active');
