$(document).ready(function () {

   /* var currentPageSize = 3;
    loadPosts(currentPageSize);

    function loadPosts(currentPage) {
        $.ajax({
            url:"/private/getposts",
            method:"post",
            data:{pageNumber:1, pageSize:currentPage},
            success: function (postslist) {
                console.log(postslist);
                $(postslist).each(function (post) {
                    
                })
            }
        })
    }

    $('#postbox').load("/private/getposts", {"pageNumber": 1, "pageSize": currentPageSize});

    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            currentPageSize += 3;
            $('#postbox').load("/private/getposts", {"pageNumber": 1, "pageSize": currentPageSize});

        }
        console.log(currentPageSize);
    });*/


    var start = $('#startDate').val(moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss.SSS'));
    var end = $('#endDate').val(moment().format('YYYY-MM-DD HH:mm:ss.SSS'));

    $(".searchBox").keyup(function () {
        var value = $(this).val().toLowerCase();
        $(".list-group #filter").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });


    $(".giverole").change(function () {
        var role = $(this).val();
        var email = $(this).parent().parent().find(".user-email-id").html();
        $.ajax({
            url: "/private/giverole/" + role + "/" + email,
            method: "post",
            success: function (message) {
                $("#result").addClass("alert alert-info").append("Allocating role...");
                $("#result").fadeTo(2000, 500).slideUp(500, function () {
                    $("#result").empty();
                    $('#result').removeClass("alert alert-info");
                    location.reload();
                });
            }
        })
    });


    $(".takerole").change(function () {
        var role = $(this).val();
        var email = $(this).parent().parent().find(".user-email-id").html();
        $.ajax({
            url: "/private/takerole/" + role + "/" + email,
            method: "post",
            success: function (message) {
                $("#result").addClass("alert alert-info").append("De-allocating role...");
                $("#result").fadeTo(2000, 500).slideUp(500, function () {
                    $("#result").empty();
                    $('#result').removeClass("alert alert-info");
                    location.reload();
                });
            }
        })
    });

    $(".gold").change(function () {
        var gold = $(this).val();
        if ($.isNumeric(gold)) {
            var email = $(this).parent().parent().find(".user-email-id").html();
            console.log(gold + " " + email);
            $.ajax({
                url: "/private/changegold/" + gold + "/" + email,
                method: "post",
                success: function (message) {
                    $("#result").addClass("alert alert-info").append("Changing gold badge...");
                    $("#result").fadeTo(2000, 500).slideUp(500, function () {
                        $("#result").empty();
                        $('#result').removeClass("alert alert-info");
                    });
                }
            })
        }
        else {
            alert("Enter number!!");
        }
    });

    $(".silver").change(function () {
        var silver = $(this).val();
        if ($.isNumeric(silver)) {
            var email = $(this).parent().parent().find(".user-email-id").html();
            console.log(silver + " " + email);
            $.ajax({
                url: "/private/changesilver/" + silver + "/" + email,
                method: "post",
                success: function (message) {
                    $("#result").addClass("alert alert-info").append("Changing silver badge...");
                    $("#result").fadeTo(2000, 500).slideUp(500, function () {
                        $("#result").empty();
                        $('#result').removeClass("alert alert-info");
                    });
                }
            })
        }
        else {
            alert("Enter number!!");
        }
    });

    $(".bronze").change(function () {
        var bronze = $(this).val();
        if ($.isNumeric(bronze)) {
            var email = $(this).parent().parent().find(".user-email-id").html();
            console.log(bronze + " " + email);
            $.ajax({
                url: "/private/changesilver/" + bronze + "/" + email,
                method: "post",
                success: function (message) {
                    $("#result").addClass("alert alert-info").append("Changing bronze badge...");
                    $("#result").fadeTo(2000, 500).slideUp(500, function () {
                        $("#result").empty();
                        $('#result').removeClass("alert alert-info");
                    });
                }
            })
        }
        else {
            alert("Enter number!!");
        }
    });


    $(".user-active").change(function () {
        var active = $(this).is(":checked");
        var email = $(this).parent().parent().find(".user-email-id").html();
        console.log(active);
        console.log(email);
        $.ajax({
            url: "/private/active/" + active + "/" + email,
            method: "post",
            success: function (message) {
                $("#result").addClass("alert alert-info").append("Changing active status...");
                $("#result").fadeTo(2000, 500).slideUp(500, function () {
                    $("#result").empty();
                    $('#result').removeClass("alert alert-info");
                });
            }
        })
    });


    $(".totalpoints").change(function () {
        var totalpoints = $(this).val();
        var email = $(this).parent().parent().find(".user-email-id").html();
        console.log(totalpoints);
        console.log(email);
        $.ajax({
            url: "/private/changetotalpoints/" + totalpoints + "/" + email,
            method: "post",
            success: function (message) {
                $("#result").addClass("alert alert-info").append("Changing total points...");
                $("#result").fadeTo(2000, 500).slideUp(500, function () {
                    $("#result").empty();
                    $('#result').removeClass("alert alert-info");
                });
            }
        })
    });


    $('span[name="dates"]').daterangepicker({}, function (start, end) {
            var startDate = start.format('YYYY-MM-DD HH:mm:ss.SSS');
            var endDate = end.format('YYYY-MM-DD HH:mm:ss.SSS');
            console.log(startDate);
            console.log(endDate);
            $('#startDate').val(startDate);
            $('#endDate').val(endDate);
            var start = $("#startDate").val();
            var end = $("#endDate").val();
            console.log(start);
            console.log(end);
            $.ajax({
                type: "Get",
                url: "/private/searchRecognitionByDate/" + start + "/" + end,
                success: function (data) {

                    $('#page').empty();
                    $('#page').html(data);
                }
            });

        }
    );


    $("#wall-of-fame").on('click', ".delete-post", function (e) {
        var id = $(this).find('.get-row-key').val();
        var reason = $(this).find('#reason').val();

        $.ajax({
            url: "/private/revoke/" + id + "/" + reason,
            method: "post",
            success: function (e) {
                if (e === "success") {
                    $('#deletepost').addClass("alert alert-success");
                    $('#deletepost small').text("Revoking Recognition...");
                    setTimeout(function () {
                        location.reload();
                    }, 3000);
                }
                if (e === "failure") {
                    $("#result").addClass("alert alert-danger").append("Receiver does not have enough badge to revoke.");
                    $("#result").fadeTo(2000, 500).slideUp(500, function () {
                        $("#result").empty();
                        $('#result').removeClass("alert alert-danger");
                    });
                }
            }
        });
    });


    var table = $('#manageemployees').DataTable({
        paging: true,
        fixedColumns: true
    });
    var availableTags = [];

    $.ajax({
        url: "/private/getemployeesemail",
        method: "get",

        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                availableTags.push(data[i].email);
            }
        }
    });
    $("#newerid").autocomplete({
        source: availableTags
    });

})