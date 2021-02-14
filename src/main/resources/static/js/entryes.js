var entryAjaxUrl = "psjournal/entryes/";
var subjectAjaxUrl = "psjournal/subjects/";
var studentAjaxUrl = "psjournal/students/";

var subjects = getAllResponseJSON(subjectAjaxUrl);
var students = getAllResponseJSON(studentAjaxUrl);

console.log(subjects);
console.log(students);

function getAllResponseJSON(targetUrl) {
    return $.ajax({
        type: "GET",
        url: targetUrl
    }).done(function (data) {
        return data;
    });
}

var ctx = {
    ajaxUrl: entryAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: entryAjaxUrl
        }).done(updateTableByData);
    }
};

$(function () {
    makeEditable({
        "columns": [
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
        ]
    });
});
