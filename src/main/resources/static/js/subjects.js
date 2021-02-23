const ctx = {
    ajaxUrl: "../subjects",
    updateTable: () => {
        ajaxApi.getAll(ctx.ajaxUrl)
            .done(updateTableByData);
    },
    fillForm: (dataSet) => {
        $('#objId').val(dataSet.id);
        $('#name').val(dataSet.name);
    },
    checkboxData: [],
    datatableOpts: {
        "columns": [
            {
                "title": "Id",
                "data": "id"
            },
            {
                "title": "Name",
                "data": "name"
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

$('#subjectsTab').addClass('active');
