var ajaxUrl = "ajax/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatableMeal").DataTable({
        "paging": false,
        "info": true,
        "aoColumns": [
            {
                "mData": "dateTime"
            },
            {
                "mData": "description"
            },
            {
                "mData": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

$("#filterForm").submit(function () {
    filter();
    return false;
});
function filter() {
    $.get(ajaxUrl+"filter", function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}
function updateTable() {
    filter();
}
function resetFilter() {
    $("#filterForm").find(":input").val("");
    return false;
}
