/* globals Chart:false, feather:false */

$('#statisticTab').addClass('active');

ajaxApi.getAll("/psjournal/students").done(function (data) {
    let dataX = [];
    let dataY = [];
    let dataAvgLine = [];

    data.forEach(obj => {
        dataX.push(obj.lastName + " " + obj.firstName + " " + obj.patronymic);
        dataY.push(obj.averageRating);
    });

    let count = 0;
    let sum = 0;
    dataY.forEach(x => {
        count++;
        sum += x;
    });
    while (dataAvgLine.length < count) {
        dataAvgLine.push(sum / count);
    }

    createChart(dataX, dataY, dataAvgLine);
});

function createChart(dataX, dataY, dataAvgLine) {
    'use strict'
    feather.replace();
    // Graphs
    let chartEl = document.getElementById('myChart');

    let myChart = new Chart(chartEl, {
        type: 'bar',
        data: {
            labels: dataX,
            datasets: [{
                type: 'line',
                fill: false,
                backgroundColor: '#f80808',
                borderColor: '#f80808',
                borderDash: [5, 5],
                data: dataAvgLine,
            },
                {
                    type: 'bar',
                    data: dataY,
                    backgroundColor: '#007bff',
                }]
        },
        options: {
            responsive: true,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            legend: {
                display: false
            }
        }
    });
}