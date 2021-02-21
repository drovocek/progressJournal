
// const viewApi = {
//     initTableView() {
//         console.log("<< initTableView() >>");//LOG
//
//         dataTable = $('#realtime').DataTable({
//             paging: true,
//             info: false,
//             responsive: true,
//             columns: [
//                 {
//                     title: "First Name",
//                     data: "firstName"
//                 },
//                 {
//                     title: "Last Name",
//                     data: "lastName"
//                 },
//                 {
//                     title: "Phone Number",
//                     data: "phoneNumber"
//                 },
//                 {
//                     title: "Email",
//                     data: "email"
//                 },
//                 {
//                     title: "Id",
//                     data: "id"
//                 }
//             ],
//             order: [
//                 [
//                     4,
//                     "asc"
//                 ]
//             ],
//             columnDefs: [
//                 {
//                     targets: [4],
//                     visible: false,
//                     searchable: false
//                 }
//             ]
//         });
//
//         $('#formButton').on("click", socketApi.createOrUpdate.bind(this, dataTable));
//         $('#delete').on("click", socketApi.delete.bind(this, dataTable));
//         $('#clear').on("click", viewApi.clearForm);
//
//         const self = viewApi;
//         $('#realtime tbody').on("click", "tr", function () {
//             self.selectRow.bind(this, dataTable)();
//         });
//
//         const sellLayout = '<input class="form-control py-2 border-right-0 border" type="search" placeholder="Search">';
//         const filterRow = '<tr><th >x1</th><th>x2</th><th>x3</th><th>x4</th></tr>';
//         $("#realtime thead").append(filterRow);
//         $("#realtime thead tr:eq(1) th").each(function (i) {
//
//             $(this).html(sellLayout);
//
//             $("input", this).on("keyup change", function () {
//                 if (dataTable.column(i).search() !== this.value) {
//                     dataTable
//                         .column(i)
//                         .search(this.value)
//                         .draw();
//                 }
//             });
//         });
//
//         viewApi.addPhoneMask();
//     },
//     printTable(usersArray) {
//         console.log("<< printTable() >>");//LOG
//         dataTable.rows.add(usersArray).draw();
//     },
//     addRow(dataTable, data) {
//         console.log("<< addRow() >>");//LOG
//         const addedRow = dataTable.row.add(data).draw(false);
//
//         const addedRowNode = addedRow.node();
//         console.log(addedRowNode);
//         $(addedRowNode).addClass("highlight");
//     },
//     removeRow(dataTable, id) {
//         console.log("<< removeRow() >>");//LOG
//
//         var rowIndexes = [];
//         dataTable.rows(function (idx, data, node) {
//             if (data.id === parseInt(id)) {
//                 rowIndexes.push(idx);
//             }
//             return false;
//         });
//
//         dataTable.row(rowIndexes[0]).remove().draw(false);
//     },
//     selectRow(dataTable) {
//         console.log("<< selectRow() >>");//LOG
//
//         if ($(this).hasClass("selected")) {
//             $(this).removeClass("selected");
//         } else {
//             dataTable.$("tr.selected").removeClass("selected");
//             $(this).addClass("selected");
//             viewApi.fillForm(dataTable);
//         }
//     },
//     clearForm() {
//         console.log("<< clearForm() >>");//LOG
//         phoneMask.setRawValue("+7 ");
//         $(".selected").removeClass("selected");
//         $("#detailsForm").find(":input").val("");
//         viewApi.drawFormDetails(false);
//     },
//     fillForm(dataTable) {
//         console.log("<< fillForm() >>");//LOG
//         const dataSet = dataTable.rows(".selected").data()[0];
//
//         $('#id').val(dataSet.id);
//         $('#firstName').val(dataSet.firstName);
//         $('#lastName').val(dataSet.lastName);
//         $('#phoneNumber').val(dataSet.phoneNumber);
//         $('#email').val(dataSet.email);
//         phoneMask.setRawValue(dataSet.phoneNumber);
//
//         this.drawFormDetails(true);
//     },
//     drawFormDetails(isForUpdate) {
//         console.log("<< drawFormDetails() >>");//LOG
//
//         const formBtn = $("#formButton");
//         const deleteBtn = $("#delete");
//         const formTitle = $("#formTitle");
//
//         formBtn.removeClass((isForUpdate) ? "btn btn-success" : "btn btn-warning")
//             .addClass((isForUpdate) ? "btn btn-warning" : "btn btn-success")
//             .html((isForUpdate) ? "Update" : "Create");
//
//         formTitle.html((isForUpdate) ? "Update User" : "Create User");
//
//         (isForUpdate) ? deleteBtn.show() : deleteBtn.hide();
//     },
//     addPhoneMask() {
//         phoneMask =
//             new Cleave('#phoneNumber', {
//                 blocks: [2, 3, 3, 4],
//                 delimiters: [" (", ") ", "-"],
//                 delimiterLazyShow: true,
//                 prefix: "+7",
//                 numericOnly: true
//             });
//     },
//     buildRequestBody() {
//         console.log("<< buildRequestBody() >> ");//LOG
//         const formData = JSON.stringify($("#detailsForm").serializeJSON());
//         console.log("requestBody: " + formData);//LOG
//         return formData;
//     },
//     successNoty(key) {
//         console.log("<< successNoty() >>");//LOG
//
//         new Noty({
//             text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + key,
//             type: "success",
//             // mint, sunset, relax, nest, metroui, semanticui, light, bootstrap-v3, bootstrap-v4
//             theme: "relax",
//             layout: "bottomRight",
//             timeout: 1000
//         }).show();
//     },
//     failNoty(apiError) {
//         console.log("<< failNoty() >>");//LOG
//         console.log(apiError);
//         console.log("type: " + apiError.type);
//         console.log("details: " + apiError.details);
//
//         failedNote = new Noty({
//             text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + apiError.typeMessage + "<br>" + apiError.details.join("<br>"),
//             type: "error",
//             // mint, sunset, relax, nest, metroui, semanticui, light, bootstrap-v3, bootstrap-v4
//             theme: "relax",
//             layout: "bottomRight",
//             timeout: 2000
//         }).show();
//     }
// }
//
// $(document).ready(() => socketApi.connect(viewApi.initTableView));
