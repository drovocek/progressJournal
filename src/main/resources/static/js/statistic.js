/* globals Chart:false, feather:false */

$('#statisticTab').addClass('active');

ajaxApi.getAll("../students").done(function (data) {
    let dataX = [];
    let dataY = [];

    console.log(data);

    data.forEach(obj => {
        dataX.push(obj.lastName + " " + obj.firstName + " " + obj.patronymic);
        dataY.push(obj.averageRating);
    });

    console.log(dataX);
    console.log(dataY);

    createChart(dataX, dataY);
});

function createChart(dataX, dataY) {
    'use strict'

    feather.replace();

    // Graphs
    let chartEl = document.getElementById('myChart');
    // eslint-disable-next-line no-unused-vars
    let myChart = new Chart(chartEl, {
        type: 'line',
        data: {
            labels: dataX,
            datasets: [{
                data: dataY,
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: false
            }
        }
    });
}
