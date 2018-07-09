const changeClassContent = function (e) {
    
    if($('#navbar').hasClass("show")) {
        $('#content').removeClass("col-sm-10").removeClass("col-9").addClass("col-sm-12");
        $(e.currentTarget).parent('div').addClass("navbar-title");
        $('#navbar').removeClass('show');
    } else {
        $('#content').removeClass("col-sm-12").addClass("col-9").addClass("col-sm-10");
        $(e.currentTarget).parent('div').removeClass("navbar-title");
        $('#navbar').addClass('show');
    }
}


$(document).ready(function(){
    $('.navbar-toggler').on('click', function(e) {
        changeClassContent(e);
    })
});