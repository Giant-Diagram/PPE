$(document).scroll(function() {
    checkOffset();
});

function checkOffset() {
    if($('#rightForm').offset().top + $('#rightForm').height()
        >= $('#footer').offset().top - 40) {

        $('#rightForm').css('position', 'absolute');
        $('#rightForm').css('bottom','0');
        $('#rightForm').css('margin-top',$('#footer').offset().top + 'px');
        $('#rightForm').css('width',$('#rightForm-Parent').width() + 'px');
    }
    if($(document).scrollTop() + window.innerHeight < $('#footer').offset().top -40) {
        $('#rightForm').css('position', 'fixed');
        $('#rightForm').css('bottom','');
        $('#rightForm').css('margin-top','');
        $('#rightForm').css('width',$('#rightForm-Parent').width() + 'px');
    }
}
