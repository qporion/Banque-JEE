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

const changeFormLogIn = function (e) {
    $(e.currentTarget).parent().parent().find('input[name=compte]').replaceWith(
            "<input type=\"text\" class=\"form-control\" name=\"login\" placeholder=\"prénom.nom\" />"
            );
}

const changeTableCompteClient = function(e) {
    if ($(e.currentTarget).is(':checked')) {
        $('tbody > tr').each(function(){
            if ( ! $(this).hasClass("bg-danger") ) {
                $(this).hide()
            }
        })
    } else {
        $('tbody > tr').each(function(){
           $(this).show()
        })
    }
}

const validForm = function(e) {
    $(e.currentTarget).closest('form').submit()
}

$(document).ready(function(){
    $('.navbar-toggler').on('click', changeClassContent);
    $('#switch-conseiller').on('click', changeFormLogIn);
    $('#decouvert').on('change', changeTableCompteClient);
    $('.valid-form').on('click', validForm);
});