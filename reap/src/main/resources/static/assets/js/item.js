$(document).ready(function () {

    var totalCart = 0;
    $('.cartBtn').on('click', function () {

        var price = $(this).prev().text();
        var name = $(this).parent().prev().attr("src");

        var nameee = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".png"));

        var url = "/assets/images/shopItems/" + nameee + ".png";
        price = price.substring(0, price.indexOf(" "));


        var row = ' <div class="row item-row-r">' +
            '<div class="col-md-2">' +
            '<img class="item-img"' + "src=" + '"' + url + '"' +
            'height="30" width="30"/>' +
            '</div>' +
            '<div class="col-md-4">' +
            '<p class="item-name">' + nameee + '</p>' +
            '</div>' +
            '<div class="col-md-2">' +
            '' +
            '</div>' +
            '<div class="col-md-3 item-price">' +
            '' + price + '' +
            '</div>' +
            '<div><button class="clear-item">&times;</button>' +
            '' +
            '</div>'
            + '';

        $(".item-data-r").append(row);

        totalCart += parseInt(price);
        $("#points").text(totalCart);


    });

    $(".RButton").click(function () {

        if (!$(".item-data-r").children().length > 0) {
            $("#result").addClass("alert alert-danger").append("Put some item(s) in bag to redeem");
            setTimeout(
                function () {
                    location.reload();
                }, 3000
            );
        }
        else {
            var imgs = "";
            var item = "";
            var price = "";
            var child = $(".item-data-r").children();
            child.each(function (index) {
                var itemName = $(this).find($(".item-name")).text();
                var itemPrice = $(this).find($(".item-price")).text();
                var imgUrl = $(this).find($(".item-img")).text()

                imgs = imgs + imgUrl + " ";
                item = item + itemName + " ";
                price = price + itemPrice + " ";


            })
            $.ajax({
                url: "/private/redeem",
                method: "post",
                data: {items: item, imgUrl: imgs, totalPrice: totalCart},
                success: function (e) {

                    if (e == "done") {
                        $("#result").addClass("alert alert-success").append("Placing order...");
                        setTimeout(
                            function () {
                                location.reload();

                            }, 3000
                        );
                    }
                    else {
                        $("#result").addClass("alert alert-danger").append("Not enough points to redeem");
                        $("#result").fadeTo(2000, 500).slideUp(500, function () {
                            $("#result").empty();
                            $('#result').removeClass("alert alert-danger");
                        });

                    }

                }
            });

        }
    });

    $('body').on('click', '.clear-item', function () {
        console.log("clickedd");
        var priceee = parseInt($(this).parent().prev().text());
        console.log(priceee);
        totalCart = totalCart - priceee;
        $("#points").text(totalCart);
        $(this).parent().parent().remove();

    });


});