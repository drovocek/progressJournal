const subjAjaxUrl = "psjournal/subjects/";
const studAjaxUrl = "psjournal/students/";

const concatFields = {
    stud: ['lastName', 'firstName', 'patronymic'],
    subj: ['name'],
};

const ctx = {
    ajaxUrl: "psjournal/entryes/",
    updateTable: () => {
        ajaxApi.getAll(ctx.ajaxUrl)
            .done(updateTableByData);
    }
};

$(function () {
    makeEditable({
        "columns": [
            {
                "title": "id",
                "data": "id"
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
                targets: [0],
                visible: false,
                searchable: false
            }
        ],
    });
});

function getDataMap(ajaxUrl, concatFields) {
    let JSON = ajaxApi.getAll(ajaxUrl).responseJSON;
    return jsonToMap(JSON, concatFields);
}

function jsonToMap(JSON, concatFields) {
    let map = new Map();
    for (obj of JSON) {
        let value = "";
        concatFields.forEach(field => value = value.concat(obj[field] + " "));
        value.trim();
        map.set(obj.id, value);
    }
    return map;
}

function addOptToSelect(dataMap, selectId) {
    let select = document.getElementById(selectId);
    // while (select.childNodes.length > 3) {
    //     select.removeChild(select.lastChild);
    // }
    select.childNodes.forEach(el => el.re);
    dataMap.forEach(function (value, key) {
        let opt = document.createElement('option');
        opt.value = key;
        opt.innerHTML = value;
        select.appendChild(opt);
    });
}

addOptToSelect(getDataMap(studAjaxUrl, concatFields.stud), 'studentId');
addOptToSelect(getDataMap(subjAjaxUrl, concatFields.subj), 'subjectId');

