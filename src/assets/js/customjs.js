
/*$(function() {

    $("#fname_error_message").hide();
    $("#lname_error_message").hide();
    $("#gender_error_message").hide();
    $("#dob_error_message").hide();
    $("#aadhar_error_message").hide();
    $("#income_error_message").hide();
    $("#education_error_message").hide();
    $("#occupation_error_message").hide();

    
    
    var error_fname = false;
    var error_lname = false;
    var error_gender = false;
    var error_dob = false;
    var error_aadhar = false;
    var error_income = false;
    var error_education = false;
    var error_occupation = false;



    $("#form_frame").on("focusout", function() {
        check_fname();
    });
    $("#form_lrame").focusout( function() {
        check_lname();
    });
    $("#form_lrame").focusout( function() {
        check_gender();
    });
    $("#form_lrame").focusout( function() {
        check_dob();
    });
    $("#form_lrame").focusout( function() {
        check_aadhar();
    });
    $("#form_lrame").focusout( function() {
        check_income();
    });
    $("#form_lrame").focusout( function() {
        check_education();
    });
    $("#form_lrame").focusout( function() {
        check_occupatiob();
    });



    function check_fname() {
        var pattern = /^[a-zA-z]*$/;
        var check_fname = $("form_fname").val();
        if (pattern.test(fname) && fname !== '') {
            $('#fname_error_message').hide();
            $("#form_fname").css("border-button", "2px solid #34F458");
        } else {
            $("fname_error_message").html("Should contain only Characters");
            $("#fname_error_message").show();
            $("#form_fname").css("borfer-bottom", "2px solid #F90A0");
            error_fname = true;
        }
    }

    function check_lname() {
        var pattern = /^[a-zA-z]*$/;
        var check_fname = $("form_lname").val();
        if (pattern.test(lname) && lname !== '') {
            $('#lname_error_message').hide();
            $("#form_lname").css("border-button", "2px solid #34F458");
        } else {
            $("lname_error_message").html("Should contain only Characters");
            $("#lname_error_message").show();
            $("#form_lname").css("borfer-bottom", "2px solid #F90A0");
            error_fname = true;
        }
    }

});*/