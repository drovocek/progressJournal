const ajaxApi = {
    get: (ajaxUrl) => {
        console.log("<< get() >>");//LOG
        let id = $('#objId').val();
        return $.ajax({
            url: ajaxUrl + id,
            type: "GET"
        });
    },
    getAll(ajaxUrl) {
        console.log("<< getAll() >>");//LOG
        return $.ajax({
            type: "GET",
            url: ajaxUrl,
        });
    },
    delete: (ajaxUrl) => {
        console.log("<< delete() >>");//LOG
        if (confirm("Delete")) {
            let id = $('#objId').val();
            $.ajax({
                url: ajaxUrl.concat("/", id),
                type: "DELETE"
            }).done(function () {
                $('.collapse').collapse("toggle");
                ctx.updateTable();
                successNoty("Delete done!");
            });
        }
    },
    createOrUpdate: (ajaxUrl) => {
        {
            console.log("<< createOrUpdate() >>");//LOG
            let id = $('#objId').val();
            let hasId = (id !== "");
            $.ajax({
                type: hasId ? "PUT" : "POST",
                url: ajaxUrl.concat("/", hasId ? id : ""),
                dataType: "json",
                contentType: "application/json",
                data: formApi.buildRequestBody()
            }).done(function () {
                $('.collapse').collapse("toggle");
                ctx.updateTable()
                successNoty(hasId ? "Update done!" : "Save done!");
            });
        }
    },
}

function makeEditable(datatableOpts) {
    ctx.datatable = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": true,
                "info": true,
                "responsive": true
            }
        ));

    console.log(ctx.checkboxData);
    ctx.checkboxData.forEach(checkbox => {
        console.log("checkbox");
        addOptToSelect(checkbox);
    });

    $('#add').on("click", formApi.addNew);
    $('#clear').on("click", formApi.clearForm);
    $('#save').on("click", () => ajaxApi.createOrUpdate(ctx.ajaxUrl));
    $('#delete').on("click", () => ajaxApi.delete(ctx.ajaxUrl));
    $('#datatable tbody').on("click", "tr", tableApi.selectRow);

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
}

const formApi = {
    addNew() {
        console.log("<< addNew() >>");//LOG
        if (!$('#add').hasClass('collapsed')) {
            formApi.clearForm();
            formApi.drawFormDetails();
        }
        formApi.printAddBtn();
        ctx.datatable.$('tr.selected').removeClass('selected');
    },
    printAddBtn() {
        if (!$('#add').hasClass('collapsed')) {
            $('#add').removeClass('btn-primary');
            $('#add').addClass('btn-warning');
            $('#add').html('<i class="fa fa-minus"></i>');
        } else {
            $('#add').removeClass('btn-warning');
            $('#add').addClass('btn-primary');
            $('#add').html('<i class="fa fa-plus"></i>');
        }
    },
    clearForm() {
        console.log("<< clearForm() >>");//LOG
        $(".selected").removeClass("selected");
        $('#objId').val("");
        $("#detailsForm").find(":input").val("");
    },
    fillForm() {
        console.log("<< fillForm() >>");//LOG
        const dataSet = ctx.datatable.rows(".selected").data()[0];
        ctx.fillForm(dataSet);
    },
    drawFormDetails() {
        console.log("<< drawFormDetails() >>");//LOG
        let hasId = ($('#objId').val() !== "");

        $("#formTitle").html((hasId) ? "Update" : "Create");
        $("#save")
            .removeClass((hasId) ? "btn btn-success" : "btn btn-warning")
            .addClass((hasId) ? "btn btn-warning" : "btn btn-success")
            .html((hasId) ? "Update" : "Add");

        (hasId) ? $("#delete").show() : $("#delete").hide();
    },
    buildRequestBody() {
        console.log("<< buildRequestBody() >> ");//LOG
        const formData = JSON.stringify($("#detailsForm").serializeJSON());
        console.log("requestBody: " + formData);//LOG
        return formData;
    },
}

const tableApi = {
    selectRow() {
        console.log("<< selectRow() >>");//LOG
        if ($(this).hasClass("selected")) {
            if ($('#collapseForm').hasClass('show')) {
                $('#add').addClass('collapsed');
                $('#collapseForm').collapse("toggle");
            }
            $(this).removeClass("selected");
        } else {
            ctx.datatable.$("tr.selected").removeClass("selected");
            $(this).addClass("selected");
            formApi.fillForm();
            formApi.drawFormDetails();
            if (!$('#collapseForm').hasClass('show')) {
                $('#add').removeClass('collapsed');
                $('#collapseForm').collapse("toggle");
            }
        }

        formApi.printAddBtn();
    },

}

function updateTableByData(data) {
    console.log("<< updateTableByData() >>");//LOG
    ctx.datatable.clear().rows.add(data).draw();
    formApi.printAddBtn();
}

function jsonToMap(JSON, concatFields) {
    console.log("<< jsonToMap() >>");//LOG
    let map = new Map();
    for (obj of JSON) {
        let value = "";
        concatFields.forEach(field => value = value.concat(obj[field] + " "));
        value.trim();
        map.set(obj.id, value);
    }
    return map;
}

function addOptToSelect(checkbox) {
    console.log("<< addOptToSelect() >>");//LOG

    ajaxApi.getAll(checkbox.url).done(function (data) {
        let dataMap = jsonToMap(data, checkbox.concatFields);
        dataMap.forEach(function (value, key) {
            let opt = document.createElement('option');
            opt.value = key;
            opt.innerHTML = value;
            $(checkbox.id).append(opt);
        });
    });
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
    var errorInfo = $.parseJSON(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}
