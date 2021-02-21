const ajaxApi = {
    get: (ajaxUrl) => {
        let id = $('#objId').val();
        return $.ajax({
            url: ajaxUrl + id,
            type: "GET"
        });
    },
    getAll(ajaxUrl) {
        return $.ajax({
            type: "GET",
            url: ajaxUrl,
            async: false
        });
    },
    delete: (ajaxUrl) => {
        if (confirm("Delete")) {
            let id = $('#objId').val();
            $.ajax({
                url: ajaxUrl.concat(id),
                type: "DELETE"
            }).done(function () {
                ctx.updateTable();
                successNoty("Delete done!");
            });
        }
    },
    createOrUpdate: (ajaxUrl) => {
        {
            let id = $('#objId').val();
            let hasId = (typeof id !== undefined);
            console.log("ajaxUrl: " + ajaxUrl);
            $.ajax({
                type: hasId ? "PUT" : "POST",
                url: ajaxUrl.concat(hasId ? id : ""),
                dataType: "json",
                contentType: "application/json",
                data: buildRequestBody()
            }).done(function () {
                // $("#editRow").modal("hide");
                ctx.updateTable();
                successNoty(hasId ? "Update done!" : "Save done!");
            });
        }
    },
}

const formApi = {
    clearForm() {
        console.log("<< clearForm() >>");//LOG
        $(".selected").removeClass("selected");
        $("#detailsForm").find(":input").val("");
        // viewApi.drawFormDetails(false);
    },
    fillForm() {
        console.log("<< fillForm() >>");//LOG
        const dataSet = ctx.datatableApi.rows(".selected").data()[0];
        //
        // dataSet.id
        // dataSet.student.firstName
        // dataSet.student.lastName
        // dataSet.student.patronymic
        // dataSet.subject.name
        // dataSet.name

        $('#objId').val(dataSet.id);
        // $('#subjectId').val(dataSet.student.firstName);
        // $('#studentId').val(dataSet.student.lastName);
        // $('#mark').val(dataSet.phoneNumber);
        // $('#markDate').val(dataSet.email);

        // $('select option:eq(0)').prop('selected',true)

        // this.drawFormDetails(true);
    },
    // drawFormDetails(isForUpdate) {
    //     console.log("<< drawFormDetails() >>");//LOG
    //
    //     const formBtn = $("#formButton");
    //     const deleteBtn = $("#delete");
    //     const formTitle = $("#formTitle");
    //
    //     formBtn.removeClass((isForUpdate) ? "btn btn-success" : "btn btn-warning")
    //         .addClass((isForUpdate) ? "btn btn-warning" : "btn btn-success")
    //         .html((isForUpdate) ? "Update" : "Create");
    //
    //     formTitle.html((isForUpdate) ? "Update User" : "Create User");
    //
    //     (isForUpdate) ? deleteBtn.show() : deleteBtn.hide();
    // },
}

function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true
            }
        ));

    $('#formButton').on("click", () => ajaxApi.createOrUpdate(ctx.ajaxUrl));
    $('#delete').on("click", () => ajaxApi.delete(ctx.ajaxUrl));
    $('#clear').on("click", formApi.clearForm);
    $('#datatable tbody').on("click", "tr", viewApi.selectRow);
}

const viewApi = {
    selectRow() {
        console.log("<< selectRow() >>");//LOG

        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            $('#detailsGroup').hide();
        } else {
            ctx.datatableApi.$("tr.selected").removeClass("selected");
            $(this).addClass("selected");
            $('#detailsGroup').show();
            formApi.fillForm();
        }
    },
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + key,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    console.log("failNoty()");
    var errorInfo = $.parseJSON(jqXHR.responseText);
    console.log(errorInfo);
    console.log(jqXHR);
    console.log("type: " + errorInfo.type);
    console.log("detail: " + errorInfo.detail);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function buildRequestBody() {
    console.log("<< buildRequestBody() >> ");//LOG
    const formData = JSON.stringify($("#detailsForm").serializeJSON());
    console.log("requestBody: " + formData);//LOG
    return formData;
}

$(document).ajaxError(function (event, jqXHR, options, jsExc) {
    failNoty(jqXHR);
});
$.ajaxSetup({cache: false});

// function renderEditBtn(data, type, row) {
//     if (type === "display") {
//         return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
//     }
// }
//
// function renderDeleteBtn(data, type, row) {
//     if (type === "display") {
//         return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
//     }
// }

// function add() {
//     $("#modalTitle").html("Add");
//     form.find(":input").val("");
//     $("#editRow").modal();
// }

// function updateRow(id) {
//     form.find(":input").val("");
//     $("#modalTitle").html("Update");
//     $.get(ctx.ajaxUrl + id, function (data) {
//         $.each(data, function (key, value) {
//             form.find("input[name='" + key + "']").val(value);
//         });
//         $('#editRow').modal();
//     });
// }