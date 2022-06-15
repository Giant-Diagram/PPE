function tooltipTagId(elID) {
    $('#' + elID + '').tooltip();
}

$('#kod').tooltip({
    content: function () {
        return $(this).attr('title');
    }
});

$('#fileToUpload').tooltip({
    content: function () {
        return $(this).attr('title');
    }
});


$('#startDate').tooltip();
$('#endDate').tooltip();